package com.example.eataly.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Restaurant {
    private String name;
    private String address;
    private float min_order;
    private String img;
    private ArrayList<Product> products;


    public Restaurant(String name, String address, float min_order, String img){
        this.name=name;
        this.address=address;
        this.min_order=min_order;
        this.img=img;
    }
    public Restaurant(JSONObject jsonRestaurant){
        try {
            this.name = jsonRestaurant.getString("name");
            this.address = jsonRestaurant.getString("address");
            this.min_order = Float.valueOf(jsonRestaurant.getString("min_order"));
            this.img = jsonRestaurant.getString("image_url");

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
}
