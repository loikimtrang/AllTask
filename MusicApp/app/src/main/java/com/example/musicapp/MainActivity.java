package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.adapter.SongAdapter;
import com.example.musicapp.databinding.ActivityMainBinding;
import com.example.musicapp.model.Song;
import com.example.musicapp.viewModel.SongViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private SongAdapter songAdapter;

    private SongViewModel songViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        songViewModel = new ViewModelProvider(this).get(SongViewModel.class);

        loadSongList();
        createSong();
    }

    private void loadSongList() {
        binding.rcvListSong.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        songAdapter = new SongAdapter(this, new SongAdapter.IClickItemSong() {
            @Override
            public void getSong(Song song) {
                clickSong(song);
            }
        });
        binding.rcvListSong.setAdapter(songAdapter);

        songViewModel.getSongList().observe(this, songs -> {
            if (songs != null) {
                songAdapter.setData(songs);
            }
        });
    }

    private void clickSong(Song song) {
        binding.tvNameSongPlay.setText(song.getName());
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);

        intent.putExtra("song", song);

        startService(intent);
    }


    private void createSong() {
        songViewModel.deleteAllSong();
        Song song1 = new Song("Anh Đau Từ Lúc Em ĐI", R.raw.anhdautulucemdi);
        Song song2 = new Song("E Là Không Thể", R.raw.elakhongthe);
        Song song3 = new Song("Bạc Phận", R.raw.bacphan);

        songViewModel.addSong(song1);
        songViewModel.addSong(song2);
        songViewModel.addSong(song3);
    }
}