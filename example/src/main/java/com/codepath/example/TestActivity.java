package com.codepath.example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;

import okhttp3.Headers;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void onTest(View view) {
        AsyncHttpClient client = new AsyncHttpClient();
        // Basic GET calls
        client.get("https://api.thecatapi.com/v1/images/search", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, String response) {
                Log.d("DEBUG", response);
            }

            @Override
            public void onFailure(int statusCode, @Nullable Headers headers, String errorResponse, @Nullable Throwable throwable) {
                Log.d("DEBUG", errorResponse);
            }
        });

        RequestParams params = new RequestParams();
        params.put("limit", "5");
        params.put("page", 0);

        client.get("https://api.thecatapi.com/v1/images/search", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("DEBUG", json.jsonArray.toString());

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });


        client.post("https://api.thecatapi.com/v1/images/search", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("DEBUG", json.toString());

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

        client.post("https://api.thecatapi.com/v1/images/search", "test", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("DEBUG", json.toString());

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

    }
}
