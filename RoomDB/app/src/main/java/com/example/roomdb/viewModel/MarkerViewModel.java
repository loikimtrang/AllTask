package com.example.roomdb.viewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.roomdb.database.MarkerDatabase;
import com.example.roomdb.model.Marker;

import java.util.List;

public class MarkerViewModel extends AndroidViewModel {

    private final MarkerDatabase database;
    private final MutableLiveData<List<Marker>> markerList = new MutableLiveData<>();

    public MarkerViewModel(Application application) {
        super(application);
        database = MarkerDatabase.getInstance(application);
        loadMarkersFromDataBase();
    }

    public LiveData<List<Marker>> getMarkerList() {
        return markerList;
    }

    public void addMarker(Marker marker) {
        database.markerDAO().insertMarker(marker);
        loadMarkersFromDataBase();
    }

    private void loadMarkersFromDataBase() {
        List<Marker> listMarker = database.markerDAO().getList();
        markerList.setValue(listMarker);
    }
}
