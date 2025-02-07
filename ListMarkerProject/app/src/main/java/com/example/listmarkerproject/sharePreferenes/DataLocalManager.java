package com.example.listmarkerproject.sharePreferenes;

import android.content.Context;

import com.example.listmarkerproject.model.Marker;
import com.example.listmarkerproject.model.MarkerDetail;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataLocalManager {
    private static final String PREF_LIST_MARKER = "PREF_LIST_MARKER";
    private static final String PREF_LIST_MARKER_DETAIL = "PREF_LIST_MARKER_DETAIL";
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

    public static void setListMarkerDetail(List<MarkerDetail> listMarkerDetail) {
        Gson gson = new Gson();
        String strJsonArray = gson.toJson(listMarkerDetail);
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_LIST_MARKER_DETAIL, strJsonArray);
    }

    public static List<MarkerDetail> getListMarkerDetail() throws JSONException {
        String strJsonArray = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_LIST_MARKER_DETAIL);
        List<MarkerDetail> markerDetailList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(strJsonArray);
        JSONObject jsonObject;
        MarkerDetail markerDetail;
        Gson gson = new Gson();

        for (int i = 0; i<jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            markerDetail = gson.fromJson(jsonObject.toString(), MarkerDetail.class);
            markerDetailList.add(markerDetail);
        }
        return markerDetailList;
    }
}

