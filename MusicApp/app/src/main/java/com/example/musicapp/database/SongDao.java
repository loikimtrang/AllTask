package com.example.musicapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.musicapp.model.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Insert
    void insert(Song song);

    @Query("SELECT * FROM song")
    List<Song> getAll();

    @Query("DELETE FROM song")
    void deleteAll();
}
