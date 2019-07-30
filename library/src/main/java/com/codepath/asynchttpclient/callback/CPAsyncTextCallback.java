package com.codepath.asynchttpclient.callback;

import android.os.Handler;
import android.os.Looper;

import com.codepath.asynchttpclient.CPAbsCallback;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class CPAsyncTextCallback implements CPAbsCallback {

    public CPAsyncTextCallback() {
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {

    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull final Response response)
            throws IOException {

        try (final ResponseBody responseBody = response.body()) {

            final int responseCode = response.code();
            final Headers responseHeaders = response.headers();

            final CPAsyncTextCallback handler = this;

            Runnable runnable;

            if (response.isSuccessful()) {
                final String responseString = responseBody.string();

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.onSuccess(responseCode, responseHeaders, responseString);
                    }
                };
            } else {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.onFailure(responseCode, responseHeaders, responseBody);
                    }
                };
            }

            // run on main thread to keep things simple
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    public abstract void onSuccess(int statusCode, Headers headers, String response);

    public abstract void onFailure(int statusCode, Headers headers, ResponseBody errorResponse);
}

