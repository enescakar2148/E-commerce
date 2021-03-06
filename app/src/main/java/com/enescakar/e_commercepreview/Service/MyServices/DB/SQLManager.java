package com.enescakar.e_commercepreview.Service.MyServices.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.enescakar.e_commercepreview.Model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLManager implements SQL {
    private final SQLiteDatabase database;

    public SQLManager(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void addToFavorite(long productId) {
        try{
            final String sqlCode = "INSERT INTO favorites(productId) VALUES (?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlCode);
            sqLiteStatement.bindLong(1, productId);
            sqLiteStatement.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addToCart(long productId) {
        try{
            final String sqlCode = "INSERT INTO cart(productId) VALUES (?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlCode);
            sqLiteStatement.bindLong(1, productId);
            sqLiteStatement.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean isCart(long productId) {
        try {
            ArrayList<Long> products = this.getCart();

            if (products != null) {
                for (long product : products) {
                    if (product == productId) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isFavorite(long productId) {
        try {
            ArrayList<Long> products = this.getFavorite();

            if (products != null) {
                for (long product : products) {
                    if (product == productId) {
                        return true;
                    }
                }
                return false;
            }
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Long> getFavorite() {
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM favorites", null);
            int productIdIx = cursor.getColumnIndex("productId");
            ArrayList<Long> products = new ArrayList<>();
            while (cursor.moveToNext()){
                products.add(cursor.getLong(productIdIx));
            }
            cursor.close();

            return products;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Long> getCart() {
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM cart", null);
            int productIdIx = cursor.getColumnIndex("productId");
            ArrayList<Long> products = new ArrayList<>();
            while (cursor.moveToNext()){
                System.out.println("SEPET ??R??NLER?? : " + cursor.getLong(productIdIx));
                products.add(cursor.getLong(productIdIx));
            }
            cursor.close();

            return products;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createTable(String tableName, HashMap<String, Object> fields) {
        String key = "";
        String value = "";
        for(Map.Entry<String, Object> entry : fields.entrySet()) {
            key = entry.getKey();
            value = (String) entry.getValue();
        }
        try{
            database.execSQL("CREATE TABLE IF NOT EXISTS "+ tableName+ "("+key+" "+value.toUpperCase()+")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean removeProductFromCart(long productId) {
        try {
            final String sql = "DELETE FROM cart WHERE productId = (?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sql);
            sqLiteStatement.bindLong(1,productId);
            sqLiteStatement.execute();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean removeProductFromFavorite(long productId) {
        try {
            final String sql = "DELETE FROM favorites WHERE productId = (?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sql);
            sqLiteStatement.bindLong(1,productId);
            sqLiteStatement.execute();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean saveFirstProductData(Product firstProductData) {
        try{
            final String SQL = "INSERT INTO firstProducts(id, title, price, description, category, image) VALUES(?, ?, ?, ?, ?, ?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(SQL);
            sqLiteStatement.bindLong(1, firstProductData.getProductId());
            sqLiteStatement.bindString(2, firstProductData.getTitle());
            sqLiteStatement.bindString(3, String.valueOf(firstProductData.getPrice()));
            sqLiteStatement.bindString(4, firstProductData.getDescription());
            sqLiteStatement.bindString(5, firstProductData.getCategory());
            sqLiteStatement.bindString(6, firstProductData.getImage());
            sqLiteStatement.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getFirstProductSize() {
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM firstProducts", null);
            int productIdX = cursor.getColumnIndex("productId");
            ArrayList<Long> productIds = new ArrayList<>();
            while (cursor.moveToNext()){
                productIds.add(cursor.getLong(productIdX));
            }
            cursor.close();
            return productIds.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<Product> getProductsFromDB() {
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM firstProducts", null);
            int productIdX = cursor.getColumnIndex("id");
            int productTitleX = cursor.getColumnIndex("title");
            int priceX = cursor.getColumnIndex("price");
            int productDescriptionX = cursor.getColumnIndex("description");
            int productCategoryX = cursor.getColumnIndex("category");
            int productImageX = cursor.getColumnIndex("image");
            ArrayList<Product> products = new ArrayList<>();
            Product product;

            while (cursor.moveToNext()){
                product = new Product();
                product.setProductId(cursor.getLong(productIdX));
                product.setCategory(cursor.getString(productCategoryX));
                product.setDescription(cursor.getString(productDescriptionX));
                product.setPrice(cursor.getLong(priceX));
                product.setImage(cursor.getString(productImageX));
                product.setTitle(cursor.getString(productTitleX));
                products.add(product);
            }
            cursor.close();
            return products;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
