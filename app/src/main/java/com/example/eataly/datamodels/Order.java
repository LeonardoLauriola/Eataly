package com.example.eataly.datamodels;

import java.util.ArrayList;

public class Order {
    private Restaurant restaurant;
    private float priceTotal;
    private ArrayList<Product> products;

    public Order() {

    }

    public Order(Restaurant restaurant, float priceTotal, ArrayList<Product> products){
        this.restaurant=restaurant;
        this.priceTotal=priceTotal;
        this.products=products;
    }

    public float getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(float priceTotal) {
        this.priceTotal = priceTotal;
    }

    public ArrayList<Product> getProducts() {

        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
