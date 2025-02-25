package com.example.ggmap.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.ggmap.database.BookmarkDao;
import com.example.ggmap.database.BookmarkDatabase;
import com.example.ggmap.model.Bookmark;

import java.util.List;

public class BookmarkRepo {

    private Context mContext;

    private BookmarkDatabase db;

    private BookmarkDao bookmarkDao;

    public BookmarkRepo(Context context) {
        this.mContext = context;
        db = BookmarkDatabase.getInstance(context);
        bookmarkDao = db.bookmarkDao();
    }

    public Long addBookmark(Bookmark bookmark) {
        return bookmarkDao.insertBookmark(bookmark);
    }

    public void updateBookmark(Bookmark bookmark) {
        bookmarkDao.updateBookmark(bookmark);
    }

    public Bookmark getBookmarkByLocation(double latitude, double longitude) {
        return bookmarkDao.findBookmarkByLocation(latitude, longitude);
    }

    public LiveData<List<Bookmark>> getAllBookmark() {
        return bookmarkDao.getAllBookmarks();
    }
}
