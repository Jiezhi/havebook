package io.github.jiezhi.havebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by jiezhi on 5/26/16.
 * Function:
 */
public class ScannerActivity extends BaseActivity implements ZBarScannerView.ResultHandler {
    private static final String TAG = "ScannerActivity";

    //    private ZXingScannerView scannerView;
    private ZBarScannerView scannerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        scannerView = new ZXingScannerView(this);
        scannerView = new ZBarScannerView(this);
//        List<BarcodeFormat> formats = new ArrayList<>();
//        formats.add(BarcodeFormat.ISBN13);
////        formats.add(BarcodeFormat.ISBN10);
//        scannerView.setFormats(formats);
        setContentView(scannerView);

    }


    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.ISBN13);
        formats.add(BarcodeFormat.ISBN10);
//        scannerView.setFormats(formats);
        formats = (List<BarcodeFormat>) scannerView.getFormats();
        for (BarcodeFormat format : formats
                ) {
            Log.e(TAG, "support: " + format.getName());
        }
        formats.remove(formats.indexOf(BarcodeFormat.ISBN10));
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

            // If you would like to resume scanning, call this method below:
            Intent bookDetailIntent = new Intent(ScannerActivity.this, SimpleBookActivity.class);
            bookDetailIntent.putExtra("isbn", scanStr);
            startActivity(bookDetailIntent);
        }
    }
}
