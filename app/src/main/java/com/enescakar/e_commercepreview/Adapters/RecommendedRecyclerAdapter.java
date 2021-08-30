package com.enescakar.e_commercepreview.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.Service.MyServices.DB.SQLManager;
import com.enescakar.e_commercepreview.UI.ProductDetails;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RecommendedRecyclerAdapter extends RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder> {

    private Context context;
    private boolean isAdding = false;
    private SQLManager sqlManager;
    private SQLiteDatabase sqLiteDatabase;

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
                //Veritabanı Eğer yok ise oluşturur. Eğer var ise açar
                //Mode_Private => Bizim veritabanımız sadece bizim uygulamamız üzerinden ulaşılabilir olsun
                sqLiteDatabase = context.openOrCreateDatabase("Shopping", Context.MODE_PRIVATE, null);

                //Kendi Veritabanı Yöneticimizi oluşturur.
                //Bir tane Context ve Açılmış/Oluşturulmuş bir veritbanı alır.
                sqlManager = new SQLManager(context, sqLiteDatabase);

                //------ TABLO OLUŞTURMA - BAŞLANGIÇ ----------------------
                /*
                Tablo oluşturmak için Bir HashMap oluşturmak gerekir.
                Bunun sebebi; tablo içerisinde yer alacak özellikleri verebilmektir.

                HashMap'in key bölümü -> Tabloya ait column (özellik) anlamına gelir
                HashMap'in value bölümü -> Tabloya ait column (özellik)'un TİPİ anlamına gelir.
                 */
                HashMap<String, Object> favorites = new HashMap<>();
                //Column (Özellik) = productId
                //productId Column'unun tipi = long
                favorites.put("productId", "long");
                sqlManager.createTable("favorites", favorites);
                //------ TABLO OLUŞTURMA - BİTİŞ ----------------------

                //Ürün Daha Önce Favoriye Eklenmiş mi ?
                System.out.println(sqlManager.isFavorite(2));

                //Ürünü favoriye ekleme
                //boolean success = sqlManager.addToFavorite(1);


                //sqlManager.getFavorite();

               /*if (isAdding){
                   isAdding = !isAdding;
                   holder.addToFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_ligth));
                   Toast.makeText(context, "Remove Favorite!", Toast.LENGTH_SHORT).show();
               } else {
                   isAdding = !isAdding;
                   holder.addToFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_add_ing_favorite));
                   Toast.makeText(context, "Adding Favorite!", Toast.LENGTH_SHORT).show();
               }*/
            }
        });
        
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteDatabase = context.openOrCreateDatabase("Shopping", Context.MODE_PRIVATE, null);
                sqlManager = new SQLManager(context, sqLiteDatabase);
                HashMap<String, Object> carts = new HashMap<>();
                carts.put("productId", "long");
                sqlManager.createTable("carts", carts);
                boolean success = sqlManager.addToCart(6);
               // boolean success = sqlManager.getCart( );
                System.out.println("SUCCESS: " + success);
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
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageButton addToCart, addToFavorite;
        private CardView productCard;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            addToCart = itemView.findViewById(R.id.addToCart);
            addToFavorite = itemView.findViewById(R.id.addToFavorite);
            productCard = itemView.findViewById(R.id.product);
        }
    }
}
