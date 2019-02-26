package com.example.eataly.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.eataly.datamodels.Order;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM 'order'")
    List<Order> getAll();

    @Query("DELETE FROM 'order'")
    void deleteAll();

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);
}
