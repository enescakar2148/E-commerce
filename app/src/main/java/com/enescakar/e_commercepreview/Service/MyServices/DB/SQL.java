package com.enescakar.e_commercepreview.Service.MyServices.DB;

import java.util.ArrayList;
import java.util.HashMap;

public interface SQL {
    boolean addToFavorite(long productId);
    boolean addToCart(long productId);
    boolean isCart();
    boolean isFavorite(long productId);
    ArrayList<Long> getFavorite();
    boolean getCart();
    boolean createTable(String tableName, HashMap<String, Object> fields);
}
