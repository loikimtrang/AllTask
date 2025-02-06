package com.example.listmarkerproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listmarkerproject.sharePreferenes.DataLocalManager;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarkerViewModel extends ViewModel {

    private final MutableLiveData<List<Marker>> markerList = new MutableLiveData<>();

    public MarkerViewModel() {
        loadMarkersFromLocal();
    }

    public LiveData<List<Marker>> getMarkerList() {
        return markerList;
    }

    public void addMarker(Marker marker) {
        List<Marker> currentList = markerList.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(marker);
        markerList.setValue(currentList);
        saveMarkersToLocal(currentList);
    }

    private void loadMarkersFromLocal() {
        try {
            List<Marker> listMarker = DataLocalManager.getListMarker();
            markerList.setValue(listMarker);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void saveMarkersToLocal(List<Marker> markers) {
        DataLocalManager.setListMarker(markers);
    }

}
