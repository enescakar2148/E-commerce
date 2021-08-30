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
import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.Service.MyServices.RecyclerManager;
import com.enescakar.e_commercepreview.UI.ProductDetails;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {

    private RecyclerView recommended, todaysPopulerProducts;
    private Context context;

    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        setRecyclerView(recommended, new RecommendedRecyclerAdapter(context));
        setRecyclerView(todaysPopulerProducts, new TodaysPopularproducts(context));
    }

    private void setRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        RecyclerManager.bind(recyclerView, adapter,new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

    }
}