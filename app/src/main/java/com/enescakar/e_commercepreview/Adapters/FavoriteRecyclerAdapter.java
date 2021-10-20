package com.enescakar.e_commercepreview.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.Service.MyServices.DB.SQLManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final SQLManager sqlManager;
    private final ArrayList<Product> products;

    public FavoriteRecyclerAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;

        //Veritabanı Eğer yok ise oluşturur. Eğer var ise açar
        //Mode_Private => Bizim veritabanımız sadece bizim uygulamamız üzerinden ulaşılabilir olsun
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Shopping", Context.MODE_PRIVATE, null);

        //Kendi Veritabanı Yöneticimizi oluşturur.
        //Bir tane Context ve Açılmış/Oluşturulmuş bir veritbanı alır.
        sqlManager = new SQLManager(sqLiteDatabase);

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Download Product Image
        if (products != null) {
            Glide.with(context).load(products.get(position).getImage()).into(holder.productImage);

            holder.productPrice.setText("$" + products.get(position).getPrice());

            holder.productName.setText(products.get(position).getTitle());

            holder.removeProduct.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View v) {
                    //Ürün favorilerden silinir ve geriye başarılı olup olmaması durumuna göre boolean döner
                    boolean isSucceded = sqlManager.removeProductFromFavorite(products.get(position).getProductId());

                    //Eğer başarılı ise artık favori listesinden Item Silinir
                    if (isSucceded){
                        products.remove(position);
                        Toast.makeText(context, "Ürün Favorilerden Kaldırıldı", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged(); //Adaptöre verilerin değiştiğini ve kendini güncellemesi gerektiğini söyler
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (products != null){
            return products.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageButton removeProduct;
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            productImage= itemView.findViewById(R.id.cart_productImage);
            productName = itemView.findViewById(R.id.cart_ProductName);
            productPrice = itemView.findViewById(R.id.cart_ProductPrice);
            removeProduct = itemView.findViewById(R.id.cart_RomoveProduct);
        }
    }
}
