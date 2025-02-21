package com.example.ggmap.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ggmap.model.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertBookmark(Bookmark bookmark);

    @Update
    void updateBookmark(Bookmark bookmark);

    @Delete
    void deleteBookmark(Bookmark bookmark);

    @Query("SELECT * FROM Bookmark")
    LiveData<List<Bookmark>> loadAll();

    @Query("SELECT * FROM Bookmark WHERE id = :bookmarkId")
    LiveData<Bookmark> loadLiveBookmark(Long bookmarkId);

    @Query("SELECT * FROM Bookmark WHERE id = :bookmarkId")
    Bookmark loadBookmark(Long bookmarkId);

    @Query("SELECT * FROM bookmark WHERE latitude = :latitude AND longitude = :longitude LIMIT 1")
    Bookmark findBookmarkByLocation(double latitude, double longitude);

    @Query("SELECT * FROM bookmark")
    List<Bookmark> getAllBookmarks();

}
