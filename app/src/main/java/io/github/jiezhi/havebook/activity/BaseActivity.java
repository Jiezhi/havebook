package io.github.jiezhi.havebook.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import io.github.jiezhi.havebook.app.MySingleton;

/**
 * Created by jiezhi on 5/24/16.
 * Function:
 */
public abstract class BaseActivity extends AppCompatActivity{
    private static final String TAG = "BaseActivity";
    protected RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = MySingleton.getInstance(this).getRequestQueue();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
