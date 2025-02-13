package com.example.roomdb.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdb.model.MarkerDetail;

import java.util.List;

@Dao
public interface MarkerDetailDAO {
    @Insert
    void insertMarkerDetail(MarkerDetail markerDetail);

    @Query("SELECT * FROM MARKER_DETAIL")
    List<MarkerDetail> getList();

    @Query("SELECT * FROM marker_detail WHERE id_marker = :idMarker")
    List<MarkerDetail> getListByMarkerId(int idMarker);

    @Query("DELETE FROM marker_detail WHERE id_marker = :idMarker")
    void deleteByMarkerId(int idMarker);


    @Update
    void updateMarkerDetail(MarkerDetail markerDetail);

    @Delete
    void deleteMarkerDetail (MarkerDetail markerDetail);
}
