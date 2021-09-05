package com.enescakar.e_commercepreview.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enescakar.e_commercepreview.Model.Product;
import com.enescakar.e_commercepreview.R;
import com.enescakar.e_commercepreview.Service.MyServices.DB.SQLManager;
import com.enescakar.e_commercepreview.UI.ProductDetails;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {

    private Context context;
    private SQLManager sqlManager;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Product> products;

    public CartRecyclerAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;

        //Veritabanı Eğer yok ise oluşturur. Eğer var ise açar
        //Mode_Private => Bizim veritabanımız sadece bizim uygulamamız üzerinden ulaşılabilir olsun
        //sqLiteDatabase = context.openOrCreateDatabase("Shopping", Context.MODE_PRIVATE, null);

        //Kendi Veritabanı Yöneticimizi oluşturur.
        //Bir tane Context ve Açılmış/Oluşturulmuş bir veritbanı alır.
        //sqlManager = new SQLManager(context, sqLiteDatabase);

    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Download Product Image
        if (products != null) {
            Glide.with(context).load(products.get(position).getImage()).into(holder.productImage);

            holder.productPrice.setText("$" + products.get(position).getPrice());

            holder.productName.setText(products.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        if (products != null){
            return products.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageButton removeProduct;
        private ImageView productImage;
        private TextView productName, productPrice;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            productImage= (ImageView) itemView.findViewById(R.id.cart_productImage);
            productName = itemView.findViewById(R.id.cart_ProductName);
            productPrice = itemView.findViewById(R.id.cart_ProductPrice);
            removeProduct = itemView.findViewById(R.id.cart_RomoveProduct);
        }
    }
}
