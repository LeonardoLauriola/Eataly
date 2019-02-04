package com.example.eataly.datamodels;

public class Restaurant {
    private String name;
    private String address;
    private float min_order;

    public Restaurant(String name, String address, float min_order){
        this.name=name;
        this.address=address;
        this.min_order=min_order;
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
}
