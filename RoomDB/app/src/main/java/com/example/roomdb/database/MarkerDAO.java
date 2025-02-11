package com.example.roomdb.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomdb.model.Marker;

import java.util.List;

@Dao
public interface MarkerDAO {
    @Insert
    void insertMarker(Marker marker);

    @Query("SELECT * FROM MARKER")
    List<Marker> getList();
}
