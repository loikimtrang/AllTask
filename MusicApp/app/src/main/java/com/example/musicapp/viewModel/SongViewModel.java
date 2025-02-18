package com.example.musicapp.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.musicapp.database.SongDatabase;
import com.example.musicapp.model.Song;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SongViewModel extends AndroidViewModel {

    private final SongDatabase database;
    private final MutableLiveData<List<Song>> songs = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public SongViewModel(@NonNull Application application) {
        super(application);
        database = SongDatabase.getInstance(application);
        loadSongFromDataBase();
    }

    public LiveData<List<Song>> getSongList() {
        return songs;
    }

    public void addSong(Song song) {
        executorService.execute(() -> {
            database.songDao().insert(song);
            loadSongFromDataBase();
        });
    }

    public void deleteAllSong() {
        executorService.execute(() -> {
            database.songDao().deleteAll();
            loadSongFromDataBase();
        });
    }

    private void loadSongFromDataBase() {
        executorService.execute(() -> {
            List<Song> listSong = database.songDao().getAll();
            songs.postValue(listSong);
        });
    }
}
