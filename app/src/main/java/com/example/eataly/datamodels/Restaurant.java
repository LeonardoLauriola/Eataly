package com.example.eataly.datamodels;

import java.util.ArrayList;

public class Restaurant {
    private String name;
    private String address;
    private float min_order;
    private String img;
    private ArrayList<String> products;
    private ArrayList<Product> prodotti; //TO Change name  the change from string to product type


    public Restaurant(String name, String address, float min_order, String img){
        this.name=name;
        this.address=address;
        this.min_order=min_order;
        this.img=img;
    }

    public String getMin_order() {
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


    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }
}
