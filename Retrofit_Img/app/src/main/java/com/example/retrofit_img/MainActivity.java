package com.example.retrofit_img;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit_img.adapter.ImageAdapter;
import com.example.retrofit_img.api.ApiService;
import com.example.retrofit_img.api.RetrofitClient;
import com.example.retrofit_img.databinding.ActivityMainBinding;
import com.example.retrofit_img.databinding.ItemImageBinding;
import com.example.retrofit_img.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageAdapter imageAdapter;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);

        binding.rcvListImg.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL , false));
        callApiGetImage();
    }
    private void callApiGetImage() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getImages().enqueue(new Callback<List<Image>>() {



            @Override
            public void onResponse(@NonNull Call<List<Image>> call, @NonNull Response<List<Image>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    imageAdapter = new ImageAdapter(MainActivity.this, response.body());
                    binding.rcvListImg.setAdapter(imageAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Image>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối API: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}