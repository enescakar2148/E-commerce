package com.enescakar.e_commercepreview.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryRowAdapter extends RecyclerView.Adapter<CategoryRowAdapter.Holder> {

    private Context context;
    private ArrayList<Product> products;

    public CategoryRowAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_row, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRowAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class Holder extends RecyclerView.ViewHolder{
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
