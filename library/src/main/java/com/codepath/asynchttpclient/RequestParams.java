package com.codepath.asynchttpclient;

import java.util.HashMap;

public class RequestParams extends HashMap<String, String> {

    public void put(String key, int value) {
        if (key != null) {
            super.put(key, String.valueOf(value));
        }
    }

    public void put(String key, long value) {
        if (key != null) {
            super.put(key, String.valueOf(value));
        }
    }

    public void put(String key, boolean value) {
        if (key != null) {
            super.put(key, String.valueOf(value));
        }
    }

}
