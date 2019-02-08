package com.example.eataly.datamodels;

public class Product {
    private String name;
    private int quantity=0;
    private float prize;

    public Product(String name, int quantity, float prize){
        this.name=name;
        this.quantity=quantity;
        this.prize=prize;
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

    public float getPrize() {
        return prize;
    }

    public void setPrize(float prize) {
        this.prize = prize;
    }

    public void increaseQuantity(){
        this.quantity++;
    }
    public void decreaseQuantity(){
        if(this.quantity==0)return;
        this.quantity--;
    }
}
