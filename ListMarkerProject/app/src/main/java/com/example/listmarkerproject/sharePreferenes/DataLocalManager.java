package com.example.listmarkerproject.sharePreferenes;

import android.content.Context;

import com.example.listmarkerproject.Marker;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DataLocalManager {
    private static final String PREF_LIST_MARKER = "PREF_LIST_MARKER";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }
    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }


    public static void setListMarker(List<Marker> listMarker) {
        Gson gson = new Gson();
        String strJsonArray = gson.toJson(listMarker);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_LIST_MARKER, strJsonArray);
    }


    public static List<Marker> getListMarker() throws JSONException {
        String strJsonArray = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_LIST_MARKER);
        List<Marker> markerList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(strJsonArray);
        JSONObject jsonObject;
        Marker marker;
        Gson gson = new Gson();

        for (int i = 0; i<jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            marker = gson.fromJson(jsonObject.toString(), Marker.class);
            markerList.add(marker);
        }

        return markerList;
    }
}

