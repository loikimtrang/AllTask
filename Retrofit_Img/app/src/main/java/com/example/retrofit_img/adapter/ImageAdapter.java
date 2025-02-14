package com.example.retrofit_img.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofit_img.databinding.ItemImageBinding;
import com.example.retrofit_img.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Image> imgLists = new ArrayList<>();
    private final Context mContext;

    public ImageAdapter (Context context, List<Image> imgLists) {
        this.mContext = context;
        this.imgLists = imgLists;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemImageBinding binding = ItemImageBinding.inflate(inflater, parent, false);
        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image = imgLists.get(position);
        holder.binding.tvId.setText(String.valueOf(position+1));
        Glide.with(mContext).load(image.getUrl()).into(holder.binding.imgItem);
    }

    @Override
    public int getItemCount() {
        return imgLists.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ItemImageBinding binding;

        public ImageViewHolder(@NonNull ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
