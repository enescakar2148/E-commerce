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

public class RecommendedRecyclerAdapter extends RecyclerView.Adapter<RecommendedRecyclerAdapter.ViewHolder> {

    private final Context context;
    private final SQLManager sqlManager;
    private final ArrayList<Product> products;

    public RecommendedRecyclerAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;

        //Veritabanı Eğer yok ise oluşturur. Eğer var ise açar
        //Mode_Private => Bizim veritabanımız sadece bizim uygulamamız üzerinden ulaşılabilir olsun
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("Shopping", Context.MODE_PRIVATE, null);

        //Kendi Veritabanı Yöneticimizi oluşturur.
        //Bir tane Context ve Açılmış/Oluşturulmuş bir veritbanı alır.
        sqlManager = new SQLManager(sqLiteDatabase);
        HashMap<String, Object> map = new HashMap<>();
        map.put("productId", "long");
        sqlManager.createTable("favorites",map);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_row, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecommendedRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Download Product Image
        Glide.with(context).load(products.get(position).getImage()).into(holder.productImage);

        holder.productName.setText(products.get(position).getTitle());
        holder.productPrice.setText("$"+products.get(position).getPrice());

        //Uygulama yüklenip ürünler ekrana gösterilir gösterilmez
        //ürünlerin Favoriye ve Sepete eklenip eklenmediği sorgulanır
        //Ardından değişkene atanır ve ona göre iconlar ayarlanır, işlemler yapılır
        boolean isFavorite = this.isFavorite(sqlManager, holder, position);
        boolean isCart = this.isCart(sqlManager, holder, position);

        //Ürünün Favoriye eklenmesi
        holder.addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  /*
                Ürünü sepete eklemeden önce veritabanında herhagi bir hata olmaması için
                bir tablo oluşturmamız gerekir. Her sepete ekleme tuşuna basıldığında
                ilk önce tablo yaratma işlemi yapılır

                Sürekli Sürekli tablo oluşturmak sıkıntı değil mi
                dediğinizi duyar gibiym;

                SQLManager Sınıfımızda (Veritabanı Yönetim) tablo oluşturma işlemine
                ait fonksiyonda "CREATE TABLE IF NOT EXISTS" dedik
                bunun anlamı "EĞER TABLO YOKSA; OLUŞTURULMAMIŞ İSE OLUŞTUR".

                BU nedenle biz bu methodu ne kadar çağırırsak çağıralım eğer tablo zaten halihazırda var ise
                es geçilecek ve tekrardan oluşturulmayacaktır.

                Bu işlem sadece okuma/yazma işlemlerinde eğer ki tablo henüz mevcut değil ise
                uygulamamızın çökmemesi için aldığımız bir önlemdir.
                 */

                //Tablo oluşturma işleminde bizden hasmap istenir
                //Key kısmında o tabloya ait field verilir
                //value kısmında ise  field'ın tipi  verilir (Örneğin: varchar)
                HashMap<String, Object> map = new HashMap<>();
                map.put("productId", "varchar");

                //Oluşturulması istenilen tablo ismi
                //ve tabloya ait fieldları içeren Hashmap veritabanı yönetim sınıfına ait olan
                //tablo yaratmaktan sorumlu methoda verirlir
                sqlManager.createTable("favorites", map);

                //tablo oluşturulduğuna göre artık bu ürün daha önce favoriye eklenmiş mi kontrol edilir
                //edilmemiş ise
                if (!isFavorite){
                    //favoriye eklenir
                    sqlManager.addToFavorite(products.get(position).getProductId());
                    //iconlar güncellenir
                    isFavorite(sqlManager, holder, position);
                    //mesaj
                    Toast.makeText(context, "Add To Favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //ürünü sepete eklemek
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Ürünü sepete eklemeden önce veritabanında herhagi bir hata olmaması için
                bir tablo oluşturmamız gerekir. Her sepete ekleme tuşuna basıldığında
                ilk önce tablo yaratma işlemi yapılır

                Sürekli Sürekli tablo oluşturmak sıkıntı değil mi
                dediğinizi duyar gibiym;

                SQLManager Sınıfımızda (Veritabanı Yönetim) tablo oluşturma işlemine
                ait fonksiyonda "CREATE TABLE IF NOT EXISTS" dedik
                bunun anlamı "EĞER TABLO YOKSA; OLUŞTURULMAMIŞ İSE OLUŞTUR".

                BU nedenle biz bu methodu ne kadar çağırırsak çağıralım eğer tablo zaten halihazırda var ise
                es geçilecek ve tekrardan oluşturulmayacaktır.

                Bu işlem sadece okuma/yazma işlemlerinde eğer ki tablo henüz mevcut değil ise
                uygulamamızın çökmemesi için aldığımız bir önlemdir.
                 */

                //Tablo oluşturma işleminde bizden hasmap istenir
                //Key kısmında o tabloya ait field verilir
                //value kısmında ise  field'ın tipi  verilir (Örneğin: varchar)
                HashMap<String, Object> map = new HashMap<>();
                map.put("productId", "varchar");

                //Oluşturulması istenilen tablo ismi
                //ve tabloya ait fieldları içeren Hashmap veritabanı yönetim sınıfına ait olan
                //tablo yaratmaktan sorumlu methoda verirlir
                sqlManager.createTable("cart", map);

                //tablo oluşturulduğuna göre artık bu ürün daha önce sepete eklenmiş mi kontrol edilir
                //edilmemiş ise
                if (!isCart){
                    //sepete eklme işlemi yapılır
                    sqlManager.addToCart(products.get(position).getProductId());
                    //iconlar güncellenir
                    isCart(sqlManager, holder, position);
                    //mesaj
                    Toast.makeText(context, "Add To Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Ürünün detaylarına gitmek
        holder.productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("productId", products.get(position).getProductId());
                intent.putExtra("productName", products.get(position).getTitle());
                intent.putExtra("productDescription", products.get(position).getDescription());
                intent.putExtra("productPrice", products.get(position).getPrice());
                intent.putExtra("productImage", products.get(position).getImage());
                intent.putExtra("productCategory", products.get(position).getCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageButton addToCart;
        private final ImageButton addToFavorite;
        private final CardView productCard;
        private final ImageView productImage;
        private final TextView productName;
        private final TextView productPrice;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            productImage= itemView.findViewById(R.id.productImage);
            addToCart = itemView.findViewById(R.id.addToCart);
            addToFavorite = itemView.findViewById(R.id.addToFavorite);
            productCard = itemView.findViewById(R.id.product);
            productName = itemView.findViewById(R.id.recommendedProductNameText);
            productPrice = itemView.findViewById(R.id.recommendedProductPriceText);
        }
    }

    //ürün sepete eklenmiş mi
    @SuppressLint("UseCompatLoadingForDrawables")
    private boolean isCart(SQLManager sqlManager, ViewHolder holder, int position) {
        //Ürünü veritabanında sorgulamak için ürün ID'sini değiştirilemez şekilde oluşturulması
        final long productId = products.get(position).getProductId();

        //Veritabanıyla iletişim kurulması
        if (sqlManager.isCart(productId)){
            //ürün sepete eklenmiş ise iconun ayarlanması
            holder.addToCart.setImageDrawable(context.getDrawable(R.drawable.adding_cart));
            //ürün sepete eklendi anlamına gelen true değeri döndürülmesi
            return true;
        } else {
            //ürün sepete eklenmemiş ise iconun ayarlanması
            holder.addToCart.setImageDrawable(context.getDrawable(R.drawable.ic_add_cart));
            //ürün sepete eklenmedi anlamına gelen false değerinin döndürülmesi
            return false;
        }
    }

    //ürün favoriye eklenmiş mi
    @SuppressLint("UseCompatLoadingForDrawables")
    private boolean isFavorite(SQLManager sqlManager, ViewHolder holder, int position){
        //Ürünü veritabanında sorgulamak için ürün ID'sini değiştirilemez şekilde oluşturulması
        final long productId = products.get(position).getProductId();

        //Veritabanıyla iletişim kurulması
        if (sqlManager.isFavorite(productId)){
            //ürün favoriye eklenmiş ise iconun ayarlanması
            holder.addToFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_add_ing_favorite));
            //ürün favoriye eklendi anlamına gelen true değeri döndürülmesi
            return true;
        } else {
            //ürün favoriye eklenmemiş ise iconun ayarlanması
            holder.addToFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_ligth));
            //ürün favoriye eklenmedi anlamına gelen false değerinin döndürülmesi
            return false;
        }

    }
}
