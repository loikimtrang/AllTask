package com.example.listmarkerproject;

import android.app.Application;

import com.example.listmarkerproject.sharePreferenes.DataLocalManager;

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
