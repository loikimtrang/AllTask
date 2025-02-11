package com.example.roomdb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdb.model.MarkerDetail;

@Database(entities = {MarkerDetail.class}, version = 1)
public abstract class MarkerDetailDatabase extends RoomDatabase {

    private static final  String DATABASE_NAME = "marker_detail.db";
    private static MarkerDetailDatabase instance;

    public static synchronized MarkerDetailDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MarkerDetailDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract MarkerDetailDAO markerDetailDAO();
}
