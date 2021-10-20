package com.enescakar.e_commercepreview.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enescakar.e_commercepreview.R;

public class ProductDetails extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        //GET INTENT
        Intent getDatas = getIntent();
        long productId = getDatas.getLongExtra("productId", 0);
        String productName = getDatas.getStringExtra("productName");
        String productDescription = getDatas.getStringExtra("productDescription");
        double productPrice = getDatas.getDoubleExtra("productPrice", 0);
        String productImage = getDatas.getStringExtra("productImage");
        String productCategory = getDatas.getStringExtra("productCategory");

        final TextView productNameText = findViewById(R.id.ProductNane);
        final TextView productPriceText = findViewById(R.id.ProductPrice);
        final TextView productDescriptionText = findViewById(R.id.ProductDescription);
        final TextView productCategoryText = findViewById(R.id.ProductCategory);
        final ImageView productImageView = findViewById(R.id.imageView);

        Glide.with(this).load(productImage).into(productImageView);

        productNameText.setText(productName);
        productDescriptionText.setText(productDescription);
        productPriceText.setText("$"+productPrice);
        productCategoryText.setText("Category: " + productCategory);

    }
}