package com.example.roomdb.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "marker_detail")
public class MarkerDetail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "id_marker")
    private int idMarker;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMarker() {
        return idMarker;
    }

    public String getName() {
        return name;
    }

    public void setIdMarker(int idMarker) {
        this.idMarker = idMarker;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MarkerDetail(int idMarker, String name) {
        this.idMarker = idMarker;
        this.name = name;
    }
}
