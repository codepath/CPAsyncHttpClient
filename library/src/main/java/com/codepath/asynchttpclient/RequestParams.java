package com.codepath.asynchttpclient;

import java.util.HashMap;

public class RequestParams extends HashMap<String, String> {

    public void put(String key, Object value) {
        if (key != null && value != null) {
            this.put(key, value);
        }
    }

    public void put(String key, int value) {
        if (key != null) {
            this.put(key, String.valueOf(value));
        }
    }

    public void put(String key, long value) {
        if (key != null) {
            this.put(key, String.valueOf(value));
        }
    }

}
