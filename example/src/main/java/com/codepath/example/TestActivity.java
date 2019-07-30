package com.codepath.example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.CPAsyncHttpClient;
import com.codepath.asynchttpclient.callback.CPAsyncJsonCallback;
import com.codepath.asynchttpclient.callback.CPAsyncTextCallback;

import okhttp3.Headers;
import okhttp3.ResponseBody;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void onTest(View view) {
        CPAsyncHttpClient cp = new CPAsyncHttpClient();
        cp.get("https://api.thecatapi.com/v1/images/search", new CPAsyncTextCallback() {
            @Override
            public void onSuccess(int statusCode, Headers headers, String response) {
                Log.d("DEBUG", response);
            }

            @Override
            public void onFailure(int statusCode, Headers headers, ResponseBody errorResponse) {

            }
        });

        cp.get("https://api.thecatapi.com/v1/images/search", new CPAsyncJsonCallback() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("DEBUG", json.toString());

            }

            @Override
            public void onFailure(int statusCode, Headers headers, Throwable errorResponse) {

            }
        });
    }
}
