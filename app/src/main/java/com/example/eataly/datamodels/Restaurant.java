package com.example.eataly.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.support.annotation.ColorLong;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Restaurant {

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "restaurant_id")
    private String id;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "rating")
    private float rating;

    @ColumnInfo(name = "minimun_order")
    private float min_order;

    @Ignore
    private ArrayList<Product> products;

    public final static String ENDPOINT = "restaurants";

    public Restaurant(String name, String address, float min_order, String img){
        this.name=name;
        this.address=address;
        this.min_order=min_order;
        this.imageUrl=img;
    }

    public Restaurant(){}
    public Restaurant(JSONObject jsonRestaurant){

        try {
            this.name = jsonRestaurant.getString("name");
            this.address = jsonRestaurant.getString("address");
            this.min_order = (float)jsonRestaurant.getDouble("min_order");
            this.imageUrl = jsonRestaurant.getString("image_url");
            this.id = jsonRestaurant.getString ("id");
            this.rating = (float)(jsonRestaurant.getDouble("rating")/10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public float getMin_order() {
        return min_order;
    }
    public void setMin_order(float min_order) {
        this.min_order = min_order;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setImageUrl(String img) {
        this.imageUrl = img;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    public void addProducts(Product p){
        products.add(p);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
