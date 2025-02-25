package com.example.ggmap.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.ggmap.DetailsActivity;
import com.example.ggmap.database.BookmarkDatabase;
import com.example.ggmap.model.Bookmark;
import com.example.ggmap.databinding.LayoutInfoWindowBinding;
import com.example.ggmap.viewmodel.BookmarkViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;



public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final LayoutInfoWindowBinding binding;
    private final Context mContext;
    private final GoogleMap mGoogleMap;
    private OnInfoWindowClickListener listener;
    private BookmarkViewModel bookmarkViewModel;

    public interface OnInfoWindowClickListener {
        void onInfoWindowClick(double latitude, double longitude);
    }

    public void setOnInfoWindowClickListener(OnInfoWindowClickListener listener) {
        this.listener = listener;
    }

    public CustomInfoWindowAdapter(Context context, GoogleMap googleMap) {
        this.mContext = context;
        this.mGoogleMap = googleMap;
        binding = LayoutInfoWindowBinding.inflate(LayoutInflater.from(context));
        bookmarkViewModel = new ViewModelProvider((ViewModelStoreOwner) mContext).get(BookmarkViewModel.class);

        setupInfoWindowClickListener();
    }

    private void setupInfoWindowClickListener() {
        mGoogleMap.setOnInfoWindowClickListener(marker -> {
            double latitude = marker.getPosition().latitude;
            double longitude = marker.getPosition().longitude;

            if (listener != null) {
                listener.onInfoWindowClick(latitude, longitude);
            }
        });
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        binding.tvNamePlace.setText(marker.getTitle());
        binding.tvPhone.setText(null);
        binding.tvNote.setText(null);

        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;

        Bookmark bookmark = bookmarkViewModel.getBookmarkByLocation(latitude, longitude);

        if (bookmark != null) {
            binding.tvPhone.setText(bookmark.getPhone());
            binding.tvNote.setText(bookmark.getNote());
        }

        Object imageObject = marker.getTag();
        if (imageObject instanceof Bitmap) {
            binding.imgVPlace.setImageBitmap((Bitmap) imageObject);
        } else {
            binding.imgVPlace.setImageResource(com.example.ggmap.R.drawable.default_image);
        }

        return binding.getRoot();
    }


    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        return null;
    }
}
