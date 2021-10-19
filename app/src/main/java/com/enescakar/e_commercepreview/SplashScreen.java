package com.enescakar.e_commercepreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.Service.MyServices.DB.SQLManager;
import com.enescakar.e_commercepreview.Service.RetrofitService.StoreAPI;
import com.enescakar.e_commercepreview.UI.ContainerActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScreen extends AppCompatActivity {

    private ArrayList<Product> products;
    private final String BASE_URL = "https://fakestoreapi.com";
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Status Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavBar(); // Hide NavigationBar
        setContentView(R.layout.activity_splash);

        Gson gson = new GsonBuilder().setLenient().create();

        //Retrofit Created
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //JSON'u Parse etmesi için gerekli parser
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Shopping", MODE_PRIVATE, null);
        SQLManager sqlManager = new SQLManager(this, sqLiteDatabase);
        try {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS firstProducts (id LONG, title VARCHAR, price LONG, description VARCHAR, category VARCHAR, image VARCHAR)");
        } catch (Exception e) {
            e.printStackTrace();
        }
        StoreAPI storeAPI = retrofit.create(StoreAPI.class);
        Call<List<Product>> call = storeAPI.getProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                if (response.isSuccessful()){
                    products = (ArrayList<Product>) response.body();
                    int savedProductSize = sqlManager.getFirstProductSize();
                    assert response.body() != null;
                    if (savedProductSize != response.body().size() && savedProductSize < response.body().size()){
                        for (Product product: products) {
                            if (sqlManager.saveFirstProductData(product)){
                                startActivity(new Intent(SplashScreen.this, ContainerActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SplashScreen.this, "Bir Hata Meydana Geldi !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        startActivity(new Intent(SplashScreen.this, ContainerActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(SplashScreen.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    // This snippet hides the system bars.
    private void hideNavBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
}