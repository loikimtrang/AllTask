package com.example.listmarkerproject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listmarkerproject.sharePreferenes.DataLocalManager;

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
        Set<String> markerSet = DataLocalManager.getListMarker();
        List<Marker> markers = new ArrayList<>();
        for (String name : markerSet) {
            markers.add(new Marker(name));
        }
        markerList.setValue(markers);
    }

    private void saveMarkersToLocal(List<Marker> markers) {
        Set<String> markerSet = new HashSet<>();
        for (Marker marker : markers) {
            markerSet.add(marker.getName());
        }
        DataLocalManager.setListMarker(markerSet);
    }
}
