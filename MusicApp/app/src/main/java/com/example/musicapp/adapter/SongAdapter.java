package com.example.musicapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.databinding.ItemSongBinding;
import com.example.musicapp.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    private Context mContext;
    private List<Song> songs;

    private IClickItemSong iClickItemSong;
    public interface IClickItemSong {
        void getSong(Song song);
    }
    public SongAdapter(Context context ,IClickItemSong iClickItemSong) {
        this.mContext = context;
        this.iClickItemSong = iClickItemSong;
    }
    public void setData (List<Song> listSong) {
        this.songs = listSong;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSongBinding binding = ItemSongBinding.inflate(inflater, parent, false);
        return new SongViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);

        holder.binding.tvId.setText(String.valueOf(position + 1));
        holder.binding.tvNameSong.setText(song.getName());

        holder.itemView.setOnClickListener(view -> {
            iClickItemSong.getSong(song);
        });
    }

    @Override
    public int getItemCount() {
        return (songs != null) ? songs.size() : 0;
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        private final ItemSongBinding binding;

        public SongViewHolder(@NonNull ItemSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
