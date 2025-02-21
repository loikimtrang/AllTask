package com.example.ggmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ggmap.database.BookmarkDatabase;
import com.example.ggmap.databinding.ActivityDetailsBinding;
import com.example.ggmap.model.Bookmark;
import com.example.ggmap.utils.ImageUtils;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private Bookmark bookmark;

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

        binding.imgSave.setOnClickListener(v -> updateBookmark());
    }

    private void loadImage(Long bookmarkId) {
        if (bookmarkId == null) return;

        String fileName = "bookmark" + bookmarkId + ".png";
        Bitmap image = ImageUtils.loadImage(this, fileName);

        if (image != null) {
            binding.imgVLocation.setImageBitmap(image);
        } else {
            binding.imgVLocation.setImageResource(R.drawable.default_image);
        }
    }

    private void updateBookmark() {
        if (bookmark == null) return;

        bookmark.setName(binding.edtName.getText().toString());
        bookmark.setNote(binding.edtNote.getText().toString());
        bookmark.setPhone(binding.edtPhone.getText().toString());
        bookmark.setAddress(binding.edtAddress.getText().toString());

        new Thread(() -> {
            BookmarkDatabase db = BookmarkDatabase.getInstance(getApplicationContext());
            db.bookmarkDao().updateBookmark(bookmark);

            runOnUiThread(() -> {
                Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }).start();
    }
}
