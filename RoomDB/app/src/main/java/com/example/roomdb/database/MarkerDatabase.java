package com.example.roomdb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdb.model.Marker;

@Database(entities = {Marker.class}, version = 1)
public abstract class MarkerDatabase extends RoomDatabase {

    private static final  String DATABASE_NAME = "marker.db";
    private static MarkerDatabase instance;

    public static synchronized MarkerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MarkerDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract MarkerDAO markerDAO();
}
