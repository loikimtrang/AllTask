package com.example.retrofit_img.api;

import com.example.retrofit_img.model.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Headers;

public interface ApiService {
    @Headers("x-api-key: YOUR_API_KEY_HERE")
    @GET("v1/images/search?limit=10")
    Call<List<Image>> getImages();
}

