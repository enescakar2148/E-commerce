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
    private Context context;
    private SQLiteDatabase database;

    public SQLManager(Context context, SQLiteDatabase database) {
        this.context = context;
        this.database = database;
    }

    @Override
    public boolean addToFavorite(long productId) {
        try{
            final String sqlCode = "INSERT INTO favorites(productId) VALUES (?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlCode);
            sqLiteStatement.bindLong(1, productId);
            sqLiteStatement.execute();
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addToCart(long productId) {
        try{
            final String sqlCode = "INSERT INTO cart(productId) VALUES (?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlCode);
            sqLiteStatement.bindLong(1, productId);
            sqLiteStatement.execute();
            return true;

        } catch (Exception e){
            e.printStackTrace();
            return false;
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
                System.out.println("SEPET ÜRÜNLERİ : " + cursor.getLong(productIdIx));
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
    public boolean createTable(String tableName, HashMap<String, Object> fields) {
        HashMap<String, Object> data =fields;
        String key = "";
        String value = "";
        for(Map.Entry<String, Object> entry : data.entrySet()) {
            key = entry.getKey();
            value = (String) entry.getValue();
        }
        try{
            database.execSQL("CREATE TABLE IF NOT EXISTS "+ tableName+ "("+key+" "+value.toUpperCase()+")");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

}
