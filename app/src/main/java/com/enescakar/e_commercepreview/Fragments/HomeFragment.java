package com.enescakar.e_commercepreview.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.enescakar.e_commercepreview.Adapters.RecommendedRecyclerAdapter;
import com.enescakar.e_commercepreview.Adapters.TodaysPopularproducts;
import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.Service.MyServices.RecyclerManager;
import com.enescakar.e_commercepreview.Service.RetrofitService.StoreAPI;
import com.enescakar.e_commercepreview.UI.ProductDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private RecyclerView recommended, todaysPopulerProducts;
    private Context context;

    private ArrayList<Product> products, todaysProduct;
    private final String BASE_URL = "https://fakestoreapi.com";
    private Retrofit retrofit;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Gson gson = new GsonBuilder().setLenient().create();

        //Retrofit Created
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recommended = view.findViewById(R.id.recommentedRecycler);
        todaysPopulerProducts = view.findViewById(R.id.todaysPopularProductsRecycler);

        //Create Service
        StoreAPI storeAPI = retrofit.create(StoreAPI.class);

        //API'ya istek atmak için servisten methodu çağır
        Call<List<Product>> call = storeAPI.getProductForLimit();

        //Asenkton şekilde API'ya isteği at ve cevabı al
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                //Cevap gerçekten başarılı ise
                if (response.isSuccessful()){
                    products = (ArrayList<Product>) response.body();
                    setRecyclerView(recommended, new RecommendedRecyclerAdapter(context, products));
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Herhangi bir hata meydana geldi ise
                t.printStackTrace();
            }
        });

        //TodaysProduct
        //Create Service

        //API'ya istek atmak için servisten methodu çağır
        Call<List<Product>> todaysProductCall = storeAPI.getTodayProduct();

        //Asenkton şekilde API'ya isteği at ve cevabı al
        todaysProductCall.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                //Cevap gerçekten başarılı ise
                if (response.isSuccessful()){
                    todaysProduct = (ArrayList<Product>) response.body();
                    setRecyclerView(todaysPopulerProducts, new TodaysPopularproducts(context, todaysProduct));
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                //Herhangi bir hata meydana geldi ise
                t.printStackTrace();
            }
        });
    }

    private void setRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        RecyclerManager.bind(recyclerView, adapter,new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

    }
}