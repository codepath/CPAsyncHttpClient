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

import java.io.IOException;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void onTest(View view) {
        AsyncHttpClient client = new AsyncHttpClient();
        // Basic GET calls

        // Sending no headers with API key
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
        RequestHeaders requestHeaders = new RequestHeaders();
        requestHeaders.put("x-api-key", BuildConfig.api_key);

        client.get("https://api.thecatapi.com/v1/images/search", requestHeaders, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("DEBUG", json.jsonArray.toString());

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("DEBUG", response);
            }
        });

        BufferedSource bufferedSource = Okio.buffer(Okio.source(getResources().openRawResource(R.raw.cat)));
        try {
            ByteString source = bufferedSource.readByteString();
            RequestBody body = RequestBody.create(source.toByteArray(), MediaType.get("image/jpg"));
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "cat.jpg", body)
                    .build();

            client.post("https://api.thecatapi.com/v1/images/upload", requestHeaders, params, requestBody, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    Log.d("DEBUG", json.toString());

                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.d("DEBUG", response);
                }
            });
        }
        catch (IOException e) {

        }

        client.post("https://api.thecatapi.com/v1/images/search", "test", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("DEBUG", json.toString());

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("DEBUG", response);
            }
        });

    }
}
