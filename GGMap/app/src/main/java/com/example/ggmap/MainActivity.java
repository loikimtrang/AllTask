package com.example.ggmap;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.ggmap.adapter.CustomInfoWindowAdapter;
import com.example.ggmap.database.BookmarkDatabase;
import com.example.ggmap.model.Bookmark;
import com.example.ggmap.utils.ImageUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private Location currentLocation;
    private GoogleMap googleMap;
    private PlacesClient placesClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Places.initialize(getApplicationContext(), "AIzaSyCZbFUXnFOuho5pSs6rN-uNU_k6R7pocYA");
        placesClient = Places.createClient(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void getLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
                mapFragment.getMapAsync(MainActivity.this);
            }
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        HashMap<Marker, Boolean> markerColorState = new HashMap<>();

        if (currentLocation != null) {
            LatLng myPlace = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            Marker myMarker = googleMap.addMarker(new MarkerOptions()
                    .position(myPlace)
                    .title("Vị trí của tôi")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            markerColorState.put(myMarker, false);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 15));
        }

        CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(this, googleMap);
        googleMap.setInfoWindowAdapter(adapter);

        googleMap.setOnMarkerClickListener(marker -> {
            boolean isRed = Boolean.TRUE.equals(markerColorState.getOrDefault(marker, false));

            if (isRed) {
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                Bitmap image = marker.getTag() instanceof Bitmap ? (Bitmap) marker.getTag() : null;
                saveLocation(marker, marker.getTitle(), marker.getTitle(), "", image);
            }

            markerColorState.put(marker, !isRed);
            return false;
        });

        googleMap.setOnPoiClickListener(poi -> {
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            if (marker != null) {
                markerColorState.put(marker, false);
                getImagePlace(marker, poi.placeId);
            }
        });

        loadSavedLocations();
    }


    private void saveLocation(Marker marker, String placeId, String name, String address, Bitmap image) {
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;

        new Thread(() -> {
            BookmarkDatabase db = BookmarkDatabase.getInstance(getApplicationContext());

            Bookmark existingBookmark = db.bookmarkDao().findBookmarkByLocation(latitude, longitude);
            if (existingBookmark != null) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Vị trí này đã được lưu!", Toast.LENGTH_SHORT).show());
                return;
            }

            Bookmark bookmark = new Bookmark(placeId, name, address, latitude, longitude, "", "");
            long id = db.bookmarkDao().insertBookmark(bookmark);

            if (image != null) {
                String imageFileName = "bookmark" + id + ".png";
                ImageUtils.saveImage(getApplicationContext(), image, imageFileName);
            }

            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Đã lưu vị trí!", Toast.LENGTH_SHORT).show());

        }).start();
    }

    private void loadSavedLocations() {
        new Thread(() -> {
            BookmarkDatabase db = BookmarkDatabase.getInstance(getApplicationContext());
            List<Bookmark> savedBookmarks = db.bookmarkDao().getAllBookmarks();

            runOnUiThread(() -> {
                for (Bookmark bookmark : savedBookmarks) {
                    LatLng position = new LatLng(bookmark.getLatitude(), bookmark.getLongitude());

                    Bitmap image = ImageUtils.loadImage(getApplicationContext(), "bookmark" + bookmark.getId() + ".png");

                    Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(bookmark.getName())
                            .snippet(bookmark.getAddress())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    if (marker != null && image != null) {
                        marker.setTag(image);
                    }
                }
            });
        }).start();
    }

    private void getImagePlace(Marker marker, String placeId) {
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, Arrays.asList(Place.Field.PHOTO_METADATAS));

        placesClient.fetchPlace(request).addOnSuccessListener(response -> {
            Place place = response.getPlace();
            if (place.getPhotoMetadatas() != null && !place.getPhotoMetadatas().isEmpty()) {
                PhotoMetadata photoMetadata = place.getPhotoMetadatas().get(0);
                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata).build();

                placesClient.fetchPhoto(photoRequest).addOnSuccessListener(fetchPhotoResponse -> {
                    marker.setTag(fetchPhotoResponse.getBitmap()); // Gán ảnh vào marker
                    marker.showInfoWindow();
                }).addOnFailureListener(e -> {
                    marker.setTag(null);
                    marker.showInfoWindow();
                });
            } else {
                marker.setTag(null);
                marker.showInfoWindow();
            }
        }).addOnFailureListener(e -> {
            marker.setTag(null);
            marker.showInfoWindow();
        });
    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Quyền truy cập bị từ chối, vui lòng cấp quyền", Toast.LENGTH_LONG).show();
            }
        }
    }

}