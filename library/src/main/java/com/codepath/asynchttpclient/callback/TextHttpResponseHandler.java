package com.codepath.asynchttpclient.callback;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import com.codepath.asynchttpclient.AbsCallback;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class TextHttpResponseHandler implements AbsCallback {

    public TextHttpResponseHandler() {
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {

    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull final Response response)
            throws IOException {

        final TextHttpResponseHandler handler = this;

        try (final ResponseBody responseBody = response.body()) {

            final int responseCode = response.code();
            final Headers responseHeaders = response.headers();

            Runnable runnable;
            final String responseString = responseBody.string();

            if (response.isSuccessful()) {
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
                        handler.onFailure(responseCode, responseHeaders, responseString, null);
                    }
                };
            }

            // run on main thread to keep things simple
            new Handler(Looper.getMainLooper()).post(runnable);
        } catch (IOException e) {
            handler.onFailure(500, null, "", e);
        }
    }

    public abstract void onSuccess(int statusCode, Headers headers, String response);

    public abstract void onFailure(int statusCode, @Nullable Headers headers, String errorResponse, @Nullable Throwable throwable);

}

