package com.enescakar.e_commercepreview.Service.RetrofitService;

import com.enescakar.e_commercepreview.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StoreAPI {

    @GET("/products")
    Call<List<Product>> getProducts();

    @GET("/products?limit=3")
    Call<List<Product>> getProductForLimit();

    @GET("/products?sort=desc&?limit=3")
    Call<List<Product>> getTodayProduct();

}
