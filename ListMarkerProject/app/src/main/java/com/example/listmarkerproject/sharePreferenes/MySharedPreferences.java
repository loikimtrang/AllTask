package com.example.listmarkerproject.sharePreferenes;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class MySharedPreferences {
    private static final String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private Context mContext;

    public MySharedPreferences(Context context) {
        this.mContext = context;
    }

    public void putStringValue(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
