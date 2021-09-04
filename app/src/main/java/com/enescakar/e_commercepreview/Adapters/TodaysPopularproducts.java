package com.enescakar.e_commercepreview.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.UI.ProductDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TodaysPopularproducts extends RecyclerView.Adapter<TodaysPopularproducts.ViewHolder> {

    private Context context;
    private boolean isAdding = false;
    private ArrayList<Product> products;

    public TodaysPopularproducts(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TodaysPopularproducts.ViewHolder holder, int position) {

        Glide.with(context).load(products.get(position).getImage()).into(holder.productImage);

        holder.addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (isAdding){
                   isAdding = !isAdding;
                   holder.addToFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_ligth));
                   Toast.makeText(context, "Remove Favorite!", Toast.LENGTH_SHORT).show();
               } else {
                   isAdding = !isAdding;
                   holder.addToFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_add_ing_favorite));
                   Toast.makeText(context, "Adding Favorite!", Toast.LENGTH_SHORT).show();
               }
            }
        });
        
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Adding Cart!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageButton addToCart, addToFavorite;
        private CardView productCard;
        private ImageView productImage;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            productImage= (ImageView) itemView.findViewById(R.id.productImage);
            addToCart = itemView.findViewById(R.id.addToCart);
            addToFavorite = itemView.findViewById(R.id.addToFavorite);
            productCard = itemView.findViewById(R.id.product);
        }
    }
}
