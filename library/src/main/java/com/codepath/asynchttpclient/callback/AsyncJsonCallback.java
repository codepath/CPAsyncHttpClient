package com.codepath.asynchttpclient.callback;

import android.os.Handler;
import android.os.Looper;

import com.codepath.asynchttpclient.AbsCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class AsyncJsonCallback implements AbsCallback {

    public abstract void onSuccess(int statusCode, Headers headers, JSON json);

    public abstract void onFailure(int statusCode, Headers headers, String response, Throwable errorResponse);

    public AsyncJsonCallback() {
    }

    public class JSON {
        public JSONObject jsonObject;
        public JSONArray jsonArray;
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

            final AsyncJsonCallback handler = this;

            Runnable runnable;

            Handler loopHandler = new Handler(Looper.getMainLooper());
            final String responseString = responseBody.string();

            if (response.isSuccessful()) {
                try {
                    final Object jsonResponse = this.parseResponse(responseString);

                    // run on main thread to keep things simple
                    loopHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            JSON json = new JSON();

                            if (jsonResponse instanceof JSONObject) {
                                json.jsonObject = (JSONObject) jsonResponse;
                                handler.onSuccess(responseCode, responseHeaders, json);
                            } else if (jsonResponse instanceof JSONArray) {
                                json.jsonArray = (JSONArray) jsonResponse;
                                handler.onSuccess(responseCode, responseHeaders, json);
                            } else if (jsonResponse instanceof String) {
                                // In RFC5179 a simple string value is not a valid JSON
                                handler.onFailure(responseCode, responseHeaders, responseString, new JSONException(
                                        "Response cannot be parsed as JSON data" + jsonResponse));
                            }
                        }
                    });
                } catch (final JSONException e) {
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            handler.onFailure(responseCode, responseHeaders, responseString, null);
                        }
                    };

                }
            } else {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        handler.onFailure(responseCode, responseHeaders, responseString,
                                new IllegalStateException("Response failed"));
                    }
                };
                loopHandler.post(runnable);
            }

        }
    }

    protected Object parseResponse(String jsonString) throws JSONException {
        boolean useRFC5179CompatibilityMode = true;

        if (jsonString == null) {
            return null;
        }

        Object result = null;
        //trim the string to prevent start with blank, and test if the string is valid JSON,
        // because the parser don't do this :(. If JSON is not valid this will return null
        if (jsonString != null) {
            jsonString = jsonString.trim();
            if (useRFC5179CompatibilityMode) {
                if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
                    result = new JSONTokener(jsonString).nextValue();
                }
            } else {
                // Check if the string is an JSONObject style {} or JSONArray style []
                // If not we consider this as a string
                if ((jsonString.startsWith("{") && jsonString.endsWith("}"))
                        || jsonString.startsWith("[") && jsonString.endsWith("]")) {
                    result = new JSONTokener(jsonString).nextValue();
                }
                // Check if this is a String "my String value" and remove quote
                // Other value type (numerical, boolean) should be without quote
                else if (jsonString.startsWith("\"") && jsonString.endsWith("\"")) {
                    result = jsonString.substring(1, jsonString.length() - 1);
                }
            }
        }
        if (result == null) {
            result = jsonString;
        }
        return result;
    }
}

