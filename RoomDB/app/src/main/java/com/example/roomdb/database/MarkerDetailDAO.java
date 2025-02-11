package com.example.roomdb.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomdb.model.MarkerDetail;

import java.util.List;

@Dao
public interface MarkerDetailDAO {
    @Insert
    void insertMarkerDetail(MarkerDetail markerDetail);

    @Query("SELECT * FROM MARKER_DETAIL")
    List<MarkerDetail> getList();
}
