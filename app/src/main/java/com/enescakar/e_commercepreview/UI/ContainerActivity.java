package com.enescakar.e_commercepreview.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.enescakar.e_commercepreview.Adapters.CategoriesAdapter;
import com.enescakar.e_commercepreview.Fragments.CartFragment;
import com.enescakar.e_commercepreview.Fragments.FavoriteFragment;
import com.enescakar.e_commercepreview.Fragments.HomeFragment;
import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.Service.MyServices.DB.SQLManager;
import com.enescakar.e_commercepreview.Service.MyServices.RecyclerManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ContainerActivity extends AppCompatActivity {

    private ArrayList<String> categories;

    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Status Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Shopping", MODE_PRIVATE, null);
        SQLManager sqlManager = new SQLManager(sqLiteDatabase);
        ArrayList<Product> products = getSavedProductsFromDB(sqlManager);

        setContentView(R.layout.activity_container);
        if (savedInstanceState == null){
            getFragment(new HomeFragment(this, products, products));
        }

        //init
        categories = new ArrayList<>();
        categories.add("all");
        categories.add("electronics");
        categories.add("jewelery");
        categories.add("men's clothing");
        categories.add("women's clothing");

        RecyclerView categoryCeycler = findViewById(R.id.categoriesRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerManager.bind(categoryCeycler, new CategoriesAdapter(categories, this), layoutManager);

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
                        getFragment(new HomeFragment(ContainerActivity.this, products, products));
                        return true;
                    case R.id.favorite:
                        getFragment(new FavoriteFragment(ContainerActivity.this));
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


    private ArrayList<Product> getSavedProductsFromDB(SQLManager sqlManager){
        return sqlManager.getProductsFromDB();
    }
}