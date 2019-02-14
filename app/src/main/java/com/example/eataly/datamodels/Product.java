package com.example.eataly.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {

    private String name;
    private int quantity=0;
    private float price;
    private String id;

    public Product(String name, int quantity, float price){
        this.name=name;
        this.quantity=quantity;
        this.price=price;
    }

    public Product(JSONObject jsonObjectProduct){
        try {
            name=jsonObjectProduct.getString("name");
            id=jsonObjectProduct.getString("id");
            price=(float)jsonObjectProduct.getDouble("price");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public void increaseQuantity(){ quantity++; }
    public void decreaseQuantity(){
        if(quantity==0)
            return;
        quantity--;
    }

    public float getSubtotal(){
        return quantity * price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
