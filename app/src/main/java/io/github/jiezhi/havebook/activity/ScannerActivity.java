package io.github.jiezhi.havebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.jiezhi.havebook.R;
import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by jiezhi on 5/26/16.
 * Function:
 */
public class ScannerActivity extends BaseActivity implements ZBarScannerView.ResultHandler {
    private static final String TAG = "ScannerActivity";

    private ZBarScannerView scannerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_layout);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        scannerView = (ZBarScannerView) findViewById(R.id.scanner_view);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        List<BarcodeFormat> formats = new ArrayList<>();
        formats = (List<BarcodeFormat>) scannerView.getFormats();
        // remove isbn10 format, cause when scan a regular book with isbn13
        // it only show 10 format code.
        int index = formats.indexOf(BarcodeFormat.ISBN10);
        if (index > 0)
            formats.remove(index);
        scannerView.setFormats(formats);
        scannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        String scanStr = rawResult.getContents();
        Log.d(TAG, rawResult.getContents()); // Prints scan results
        Log.d(TAG, rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)

        Toast.makeText(this, "Contents = " + rawResult.getContents() +
                ", length = " + scanStr.length() +
                ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();

        if (scanStr.length() != 13) {
            scannerView.resumeCameraPreview(this);
        } else {
            Intent bookDetailIntent = new Intent(ScannerActivity.this, SimpleBookActivity.class);
            bookDetailIntent.putExtra("isbn", scanStr);
            startActivity(bookDetailIntent);
        }
    }
}
