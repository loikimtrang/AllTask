package com.example.ggmap.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ggmap.DetailsActivity;
import com.example.ggmap.database.BookmarkDatabase;
import com.example.ggmap.model.Bookmark;
import com.example.ggmap.databinding.LayoutInfoWindowBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final LayoutInfoWindowBinding binding;
    private final Context mContext;
    private final GoogleMap mGoogleMap;

    public CustomInfoWindowAdapter(Context context, GoogleMap googleMap) {
        this.mContext = context;
        this.mGoogleMap = googleMap;
        binding = LayoutInfoWindowBinding.inflate(LayoutInflater.from(context));

        mGoogleMap.setOnInfoWindowClickListener(marker -> {
            double latitude = marker.getPosition().latitude;
            double longitude = marker.getPosition().longitude;

            new Thread(() -> {
                BookmarkDatabase db = BookmarkDatabase.getInstance(mContext);
                Bookmark bookmark = db.bookmarkDao().findBookmarkByLocation(latitude, longitude);

                if (bookmark != null) {
                    Intent intent = new Intent(mContext, DetailsActivity.class);
                    intent.putExtra("bookmark", bookmark);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Không tìm thấy Bookmark", Toast.LENGTH_SHORT).show();
                }
            }).start();
        });
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        binding.tvNamePlace.setText(marker.getTitle());

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
