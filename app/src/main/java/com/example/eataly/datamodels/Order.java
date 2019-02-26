package com.example.eataly.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "order")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded
    private Restaurant restaurant;

    @ColumnInfo (name = "total")
    private float priceTotal;

    @ColumnInfo(name = "products")
    @TypeConverters(ProductConverter.class)
    private List<Product> products;

    @Ignore
    public final static String ENDPOINT = "orders";

    @Ignore
    public Order(Restaurant restaurant, float priceTotal, ArrayList<Product> products){
        this.restaurant=restaurant;
        this.priceTotal=priceTotal;
        this.products=products;
    }

    public Order(){

    }

    public float getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(float priceTotal) {
        this.priceTotal = priceTotal;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void removeItem(Product p){
        products.remove(p);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}