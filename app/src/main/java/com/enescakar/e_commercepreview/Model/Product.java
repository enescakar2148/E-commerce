package com.enescakar.e_commercepreview.Model;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("id")
    private long productId;

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private double price;

    @SerializedName("category")
    private String category;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    public Product() {
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
