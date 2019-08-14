package com.codepath.asynchttpclient;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.Map;

import okhttp3.HttpUrl;
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

    public AsyncHttpClient(OkHttpClient client) {
        okHttpClient = client;
    }

    protected Request.Builder createBuilderWithHeaders(
            String url, @Nullable RequestHeaders requestHeaders) {
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (requestHeaders != null) {
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return requestBuilder;
    }

    protected String createUrlWithRequestParams(String url, RequestParams requestParams) {
        if (requestParams != null) {
            HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
            for (Map.Entry<String, String> param : requestParams.entrySet()) {
                httpBuider.addQueryParameter(param.getKey(), param.getValue());
            }
            url = httpBuider.build().toString();
        }
        return url;
    }

    public void get(String url, RequestParams requestParams, AbsCallback callback) {
        this.get(url, null, requestParams, callback);
    }

    public void get(String url, RequestHeaders requestHeaders, @Nullable RequestParams requestParams, AbsCallback callback) {
        url = createUrlWithRequestParams(url, requestParams);

        Request.Builder requestBuilder = createBuilderWithHeaders(url, requestHeaders);
        Request request = requestBuilder.build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    public void get(String url, AbsCallback callback) {
        get(url, null, callback);
    }

    public void post(String url, RequestParams requestParams, RequestHeaders requestHeaders, String body, AbsCallback callback) {
        url = createUrlWithRequestParams(url, requestParams);

        Request.Builder requestBuilder = createBuilderWithHeaders(url, requestHeaders);

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, body);

        Request request = requestBuilder.post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void post(String url, RequestParams requestParams, RequestHeaders requestHeaders, File file, AbsCallback callback) {
        Request.Builder requestBuilder = createBuilderWithHeaders(url, requestHeaders);

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, file);

        Request request = requestBuilder.post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void post(String url, String body, AbsCallback callback) {
        this.post(url, null, null, body, callback);
    }

    public void post(String url, File body, AbsCallback callback) {
        this.post(url, null, null, body, callback);
    }

    public void post(String url, RequestParams requestParams, String body, AbsCallback callback) {
        this.post(url, requestParams, null, body, callback);
    }

    public void post(String url, RequestParams requestParams, File body, AbsCallback callback) {
        this.post(url, requestParams, null, body, callback);
    }

    public void delete(String url, RequestHeaders requestHeaders, String body, AbsCallback callback) {
        Request.Builder requestBuilder = createBuilderWithHeaders(url, requestHeaders);

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, body);

        Request request = requestBuilder.delete(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void patch(String url, RequestHeaders requestHeaders, String body, AbsCallback callback) {
        Request.Builder requestBuilder = createBuilderWithHeaders(url, requestHeaders);

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, body);

        Request request = requestBuilder.patch(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }


}
