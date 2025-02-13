package com.example.roomdb.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.roomdb.model.Marker;

import java.util.List;

@Dao
public interface MarkerDAO {
    @Insert
    void insertMarker(Marker marker);

    @Query("SELECT * FROM MARKER")
    List<Marker> getList();

    @Update
    void updateMarker(Marker marker);

    @Delete
    void deleteMarker(Marker marker);

}
