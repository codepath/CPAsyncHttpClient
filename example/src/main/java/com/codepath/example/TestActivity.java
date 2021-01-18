package com.codepath.example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestHeaders;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.BinaryHttpResponseHandler;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
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

        client.setReadTimeout(10);
        client.setConnectTimeout(10);
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

        client.get("https://cdn2.thecatapi.com/images/6eg.jpg", new BinaryHttpResponseHandler()  {
            @Override
            public void onSuccess(int statusCode, Headers headers, Response response) {
                try {
                    // ~/Library/Android/sdk/platform-tools/adb pull /sdcard/Android/data/com.codepath.cpasynchttpclient/files/Pictures/TEST/test.jpg
                    File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "TEST");

                    // Create the storage directory if it does not exist
                    if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                        Log.d("DEBUG", "failed to create directory");
                    }

                    // Return the file target for the photo based on filename
                    InputStream data = response.body().byteStream();
                    File file = new File(mediaStorageDir.getPath() + File.separator + "test.jpg");
                    FileOutputStream outputStream = new FileOutputStream(file);
                    byte[] buffer = new byte[2048];
                    int len;
                    while ( (len = data.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                    }
                    Log.e("DEBUG", "done!");
                } catch (IOException e) {
                    Log.e("DEBUG", e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("DEBUG", response);
            }
        });

    }
}
