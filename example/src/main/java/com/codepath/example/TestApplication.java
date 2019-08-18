package com.codepath.example;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class TestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
