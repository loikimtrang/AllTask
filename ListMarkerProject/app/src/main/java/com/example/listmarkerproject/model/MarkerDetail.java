package com.example.listmarkerproject.model;

public class MarkerDetail {
    private String idMarker;
    private String name;

    public String getIdMarker() {
        return idMarker;
    }

    public String getName() {
        return name;
    }

    public void setIdMarker(String idMarker) {
        this.idMarker = idMarker;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MarkerDetail(String idMarker, String name) {
        this.idMarker = idMarker;
        this.name = name;
    }
}
