package com.example.ikitama_larose_api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductApi {
    @GET("/examen_api_android/products")
    Call<List<Product>> getProducts();

    @POST("/examen_api_android/products/create")
    Call<Product> createProduct(@Body Product product);
}
