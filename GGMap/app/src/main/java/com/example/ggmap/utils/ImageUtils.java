package com.example.ggmap.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {
    private static final String TAG = "ImageUtils";

    public static String saveImage(Context context, Bitmap bitmap, String fileName) {
        File directory = context.getFilesDir();
        File imageFile = new File(directory, fileName);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.d(TAG, "Hình ảnh đã được lưu: " + imageFile.getAbsolutePath());
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            Log.e(TAG, "Lỗi khi lưu hình ảnh", e);
            return null;
        }
    }

    public static Bitmap loadImage(Context context, String fileName) {
        File directory = context.getFilesDir();
        File imageFile = new File(directory, fileName);

        if (imageFile.exists()) {
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }
        return null;
    }

    public static void deleteImage(Context context, String fileName) {
        File directory = context.getFilesDir();
        File imageFile = new File(directory, fileName);

        if (imageFile.exists()) {
            if (imageFile.delete()) {
                Log.d(TAG, "Hình ảnh đã được xóa: " + imageFile.getAbsolutePath());
            } else {
                Log.e(TAG, "Không thể xóa hình ảnh: " + imageFile.getAbsolutePath());
            }
        } else {
            Log.e(TAG, "Hình ảnh không tồn tại: " + fileName);
        }
    }
}

