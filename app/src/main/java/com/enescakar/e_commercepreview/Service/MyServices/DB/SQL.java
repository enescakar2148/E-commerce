package com.enescakar.e_commercepreview.Service.MyServices.DB;

import com.enescakar.e_commercepreview.Model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public interface SQL {
    void addToFavorite(long productId);
    void addToCart(long productId);
    boolean isCart(long productId);
    boolean isFavorite(long productId);
    ArrayList<Long> getFavorite();
    ArrayList<Long> getCart();
    void createTable(String tableName, HashMap<String, Object> fields);
    boolean removeProductFromCart(long productId);
    boolean removeProductFromFavorite(long productId);
    boolean saveFirstProductData(Product firstProductData);
    int getFirstProductSize();
    ArrayList<Product> getProductsFromDB();
}
