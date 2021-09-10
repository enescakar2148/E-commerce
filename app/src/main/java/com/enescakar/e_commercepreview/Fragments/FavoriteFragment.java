package com.enescakar.e_commercepreview.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enescakar.e_commercepreview.Adapters.CartRecyclerAdapter;
import com.enescakar.e_commercepreview.Adapters.FavoriteRecyclerAdapter;
import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.Service.MyServices.DB.SQLManager;
import com.enescakar.e_commercepreview.Service.MyServices.RecyclerManager;
import com.enescakar.e_commercepreview.Service.RetrofitService.StoreAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteFragment extends Fragment {

    private final Context context;
    private RecyclerView recyclerView;
    private final ArrayList<Product> products = new ArrayList<>();

    private Retrofit retrofit;

    private final SQLManager sqlManager;

    private long price = 0;
    private TextView totalPrice ;

    public FavoriteFragment(Context context) {
        this.context = context;

        SQLiteDatabase sqliteDatabase = context.openOrCreateDatabase("Shopping", Context.MODE_PRIVATE, null);

        sqlManager = new SQLManager(context, sqliteDatabase);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Gson gson = new GsonBuilder().setLenient().create();

        //Retrofit Created
        String BASE_URL = "https://fakestoreapi.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //JSON'u Parse etmesi için gerekli parser
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //init
        StoreAPI storeAPI = retrofit.create(StoreAPI.class);
        ArrayList<Long> favoriteProductsFromDB = sqlManager.getFavorite();
        totalPrice = view.findViewById(R.id.cart_totalPrice);

        if (favoriteProductsFromDB != null){
            getProduct(favoriteProductsFromDB, storeAPI);

        } else {
            Toast.makeText(context, "Sepetinizde Ürün Yok :/", Toast.LENGTH_LONG).show();
        }
        new CountDownTimer(1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                totalPrice.setText("Total Price : $" + price);
                recyclerView = view.findViewById(R.id.cartRecyclerView);
                RecyclerManager.bind(recyclerView, new FavoriteRecyclerAdapter(context, products), new LinearLayoutManager(context));
            }
        }.start();
    }

    private void getProduct(ArrayList<Long> cartProductsFromDb, StoreAPI storeAPI){
        for (long productId:cartProductsFromDb) {
            Call<Product> getProduct = storeAPI.getProduct(productId);
            getProduct.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                    products.add(response.body());
                    assert response.body() != null;
                    price = price+ (long) response.body().getPrice();
                }

                @Override
                public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {

                }
            });
        }
    }
}