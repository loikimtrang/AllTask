package com.example.listmarkerproject.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.listmarkerproject.model.MarkerDetail;
import com.example.listmarkerproject.sharePreferenes.DataLocalManager;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MarkerDetailViewModel extends ViewModel {
    private final MutableLiveData<List<MarkerDetail>> markerDetailList = new MutableLiveData<>();

    public MarkerDetailViewModel() {
        loadMarkersDetailFromLocal();
    }

    public LiveData<List<MarkerDetail>> getMarkerDetailList() {
        return markerDetailList;
    }

    public void addMarkerDetail(MarkerDetail markerDetail) {
        List<MarkerDetail> currentList = markerDetailList.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(markerDetail);
        markerDetailList.setValue(currentList);
        saveMarkersToLocal(currentList);
    }

    public void loadMarkersDetailFromLocal() {
        try {
            List<MarkerDetail> listMarker = DataLocalManager.getListMarkerDetail();
            markerDetailList.setValue(listMarker);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void saveMarkersToLocal(List<MarkerDetail> markerDetails) {
        DataLocalManager.setListMarkerDetail(markerDetails);
    }
}
