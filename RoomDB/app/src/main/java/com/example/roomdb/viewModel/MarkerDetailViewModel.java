package com.example.roomdb.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomdb.database.MarkerDetailDatabase;
import com.example.roomdb.model.MarkerDetail;


import java.util.List;

public class MarkerDetailViewModel extends AndroidViewModel {
    private final MarkerDetailDatabase database;
    private final MutableLiveData<List<MarkerDetail>> markerDetailList = new MutableLiveData<>();

    public MarkerDetailViewModel(Application application) {
        super(application);
        database = MarkerDetailDatabase.getInstance(application);
        loadMarkersDetailFromDataBase();
    }

    public LiveData<List<MarkerDetail>> getMarkerDetailList() {
        return markerDetailList;
    }

    public void addMarkerDetail(MarkerDetail markerDetail) {
        database.markerDetailDAO().insertMarkerDetail(markerDetail);
        loadMarkersDetailFromDataBase();
    }

    private void loadMarkersDetailFromDataBase() {
        List<MarkerDetail> listMarker = database.markerDetailDAO().getList();
        markerDetailList.setValue(listMarker);
    }
}
