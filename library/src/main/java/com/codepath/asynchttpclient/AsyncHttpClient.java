package com.codepath.asynchttpclient;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

// todo: pre/post events, progress events

public class AsyncHttpClient {

    private OkHttpClient okHttpClient;
    public static MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json");

    public AsyncHttpClient() {
        okHttpClient = new OkHttpClient.Builder().build();
    }

    AsyncHttpClient(OkHttpClient client) {
        okHttpClient = client;
    }

    protected Request.Builder createBuilderWithHeaders(
            String url, @Nullable HashMap<String, String> requestHeaders) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (requestHeaders != null) {
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return requestBuilder;
    }

    public void get(String url, HashMap<String, String> requestHeaders, AbcCallback callback) {
        Request.Builder requestBuilder = createBuilderWithHeaders(url, requestHeaders);
        Request request = requestBuilder.build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public void get(String url, AbcCallback callback) {
        get(url, null, callback);
    }

    public void post(String url, HashMap<String, String> requestHeaders, String body, AbcCallback callback) {
        Request.Builder requestBuilder = createBuilderWithHeaders(url, requestHeaders);

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, body);

        Request request = requestBuilder.post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }


    public void post(String url, HashMap<String, String> requestHeaders, File file, AbcCallback callback) {
        Request.Builder requestBuilder = createBuilderWithHeaders(url, requestHeaders);

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, file);

        Request request = requestBuilder.post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }


}
