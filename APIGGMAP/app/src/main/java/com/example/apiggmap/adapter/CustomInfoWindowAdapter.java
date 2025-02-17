package com.example.apiggmap.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.apiggmap.R;
import com.example.apiggmap.databinding.LayoutInfoWindowBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final LayoutInfoWindowBinding binding;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        binding = LayoutInfoWindowBinding.inflate(LayoutInflater.from(context));
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        binding.tvNamePlace.setText(marker.getTitle());

        Object imageObject = marker.getTag();
        if (imageObject instanceof Bitmap) {
            binding.imgVPlace.setImageBitmap((Bitmap) imageObject);
        } else {
            binding.imgVPlace.setImageResource(R.drawable.default_image);
        }

        return binding.getRoot();
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
