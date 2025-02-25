package com.example.ggmap.viewmodel;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.ggmap.model.Bookmark;
import com.example.ggmap.repository.BookmarkRepo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BookmarkViewModel extends AndroidViewModel {
    private final BookmarkRepo bookmarkRepo;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        bookmarkRepo = new BookmarkRepo(application);
    }

    public void updateBookmark(Bookmark bookmark) {
        executor.execute(() -> {
            if (bookmark != null) {
                bookmarkRepo.updateBookmark(bookmark);
            }
        });
    }
    public Long addBookmark(Bookmark bookmark) {
        return bookmarkRepo.addBookmark(bookmark);
    }
    public LiveData<List<Bookmark>> getAllBookmarks() {
        return bookmarkRepo.getAllBookmark();
    }


    public Bookmark getBookmarkByLocation(double latitude, double longitude) {
        return bookmarkRepo.getBookmarkByLocation(latitude, longitude);
    }
}

