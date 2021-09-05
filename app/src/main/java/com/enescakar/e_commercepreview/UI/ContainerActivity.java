package com.enescakar.e_commercepreview.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.Toolbar;

import com.enescakar.e_commercepreview.Fragments.CartFragment;
import com.enescakar.e_commercepreview.Fragments.FavoriteFragment;
import com.enescakar.e_commercepreview.Fragments.HomeFragment;
import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.Service.RetrofitService.StoreAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContainerActivity extends AppCompatActivity {

    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Status Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_container);
        if (savedInstanceState == null){
            getFragment(new HomeFragment(this));
        }

        //init
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        toolbar.setSubtitle((CharSequence) currentDateandTime);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getFragment(new HomeFragment(ContainerActivity.this));
                        return true;
                    case R.id.favorite:
                        getFragment(new FavoriteFragment());
                        return true;
                    case R.id.cart:
                        getFragment(new CartFragment(ContainerActivity.this));
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    //Load or Set fragment
    private void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, fragment).commit();
    }
}