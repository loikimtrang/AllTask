package com.example.ggmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ggmap.database.BookmarkDatabase;
import com.example.ggmap.databinding.ActivityDetailsBinding;
import com.example.ggmap.model.Bookmark;
import com.example.ggmap.utils.ImageUtils;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private Bookmark bookmark;
    private String newImagePath = null;
    private String oldImagePath = null;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("bookmark")) {
            bookmark = (Bookmark) intent.getSerializableExtra("bookmark");

            if (bookmark != null) {
                binding.edtName.setText(bookmark.getName());
                binding.edtNote.setText(bookmark.getNote());
                binding.edtPhone.setText(bookmark.getPhone());
                binding.edtAddress.setText(bookmark.getAddress());

                loadImage(bookmark.getId());
            }
        }
        binding.imgVLocation.setOnClickListener(v -> showImagePickerDialog());
        binding.imgSave.setOnClickListener(v -> updateBookmark());
    }

    private void loadImage(Long bookmarkId) {
        if (bookmarkId == null) return;

        oldImagePath = "bookmark" + bookmarkId + ".png";
        Bitmap image = ImageUtils.loadImage(this, oldImagePath);

        if (image != null) {
            binding.imgVLocation.setImageBitmap(image);
        } else {
            binding.imgVLocation.setImageResource(R.drawable.default_image);
        }
    }
    private void handleNewImage(Bitmap newImage) {
        if (newImage == null) return;

        newImagePath = "bookmark" + bookmark.getId() + ".png";

        binding.imgVLocation.setImageBitmap(newImage);
    }



    private void updateBookmark() {
        if (bookmark == null) return;

        bookmark.setName(binding.edtName.getText().toString());
        bookmark.setNote(binding.edtNote.getText().toString());
        bookmark.setPhone(binding.edtPhone.getText().toString());
        bookmark.setAddress(binding.edtAddress.getText().toString());

        new Thread(() -> {
            BookmarkDatabase db = BookmarkDatabase.getInstance(getApplicationContext());

            if (newImagePath != null) {
                if (oldImagePath != null) {
                    ImageUtils.deleteImage(this, oldImagePath);
                }
                Bitmap newBitmap = ((BitmapDrawable) binding.imgVLocation.getDrawable()).getBitmap();
                ImageUtils.saveImage(this, newBitmap, newImagePath);
                oldImagePath = newImagePath;
            }

            db.bookmarkDao().updateBookmark(bookmark);

            runOnUiThread(() -> {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                intent.putExtra("LATITUDE", bookmark.getLatitude());
                intent.putExtra("LONGITUDE", bookmark.getLongitude());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }).start();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                handleNewImage(photo);
            } else if (requestCode == REQUEST_GALLERY) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                    handleNewImage(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void showImagePickerDialog() {
        String[] options = {"Camera", "Gallery", "Cancel"};

        new AlertDialog.Builder(this)
                .setTitle("Choose Image Source")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        openCamera();
                    } else if (which == 1) {
                        openGallery();
                    } else {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

}
