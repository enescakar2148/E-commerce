package com.enescakar.e_commercepreview.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.enescakar.e_commercepreview.Adapters.CategoryRowAdapter;
import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.Service.MyServices.RecyclerManager;
import com.enescakar.e_commercepreview.Service.RetrofitService.StoreAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectedCategory extends AppCompatActivity {

    private final String BASE_URL = "https://fakestoreapi.com";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);
        recyclerView = findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        StoreAPI storeAPI = retrofit.create(StoreAPI.class);
        //Category = All
        //Get All Products
        if (category.matches("all")){
            Call<List<Product>> categoryProducts = storeAPI.getProducts();
            categoryProducts.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(@NonNull Call<List<Product>> call, Response<List<Product>> response) {
                    if (response.isSuccessful()){
                        ArrayList<Product> products = (ArrayList<Product>) response.body();
                        RecyclerManager.bind(recyclerView, new CategoryRowAdapter(SelectedCategory.this, products), new GridLayoutManager(SelectedCategory.this, 2));
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        //Get In Categories
        Call<List<Product>> categoryProducts = storeAPI.getInCategory(category);
        categoryProducts.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()){
                    //get in category
                    ArrayList<Product> products = (ArrayList<Product>) response.body();
                    RecyclerManager.bind(recyclerView, new CategoryRowAdapter(SelectedCategory.this, products), new GridLayoutManager(SelectedCategory.this, 2));
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}