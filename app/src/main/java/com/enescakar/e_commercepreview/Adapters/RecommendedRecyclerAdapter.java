package com.enescakar.e_commercepreview.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.enescakar.e_commercepreview.R;

import org.jetbrains.annotations.NotNull;

public class RecommendedRecyclerAdapter extends RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder> {

    private Context context;
    private boolean isAdding = false;
    public RecommendedRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecommendedRecyclerAdapter.ViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageButton addToCart, addToFavorite;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            addToCart = itemView.findViewById(R.id.addToCart);
            addToFavorite = itemView.findViewById(R.id.addToFavorite);
        }
    }
}
