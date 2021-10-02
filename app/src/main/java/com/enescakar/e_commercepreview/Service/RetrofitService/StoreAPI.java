package com.enescakar.e_commercepreview.Service.RetrofitService;

import com.enescakar.e_commercepreview.Model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface StoreAPI {

    @GET("/products")
    Call<List<Product>> getProducts();

    @GET("/products/{productId}")
    Call<Product> getProduct(@Path("productId") long productId);

    @GET("/products?limit=3")
    Call<List<Product>> getProductForLimit();

    @GET("/products?limit=3&sort=desc")
    Call<List<Product>> getTodayProduct();

    @GET("/products/categories")
    Call<List> getAllCategories();

    @GET("/products/category/{category}")
    Call<List> getInCategory(@Path("category") String category);
}
