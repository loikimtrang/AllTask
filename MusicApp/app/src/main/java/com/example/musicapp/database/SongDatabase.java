package com.example.musicapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.musicapp.model.Song;

@Database(entities = {Song.class}, version = 1)
public abstract class SongDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "song.db";

    private static volatile SongDatabase instance;
    public static SongDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (SongDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    SongDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract SongDao songDao();
}
