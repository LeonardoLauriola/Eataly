package com.example.eataly.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eataly.R;
import com.example.eataly.datamodels.Order;
import com.example.eataly.ui.adapters.OrderAdapter;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView restaurantTv,restaurantAddress, totalTv;
    private RecyclerView productRv;
    private LinearLayoutManager layoutManager;
    private Button payBtn;
    private OrderAdapter adapter;

    //TODO CREATE ADAPTER
    private Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        restaurantTv=findViewById(R.id.restaurant_name);
        restaurantAddress=findViewById(R.id.restaurant_address);
        totalTv=findViewById(R.id.total_order_tv);
        productRv=findViewById(R.id.product_order_rv);
        payBtn=findViewById(R.id.pay_btn);
        order=MainActivity.GetOrder();
        dataBind();
        layoutManager=new LinearLayoutManager(this);
        adapter=new OrderAdapter(this,order.getProducts());
        productRv.setLayoutManager(layoutManager);
        productRv.setAdapter(adapter);

    }

   private void dataBind(){
        totalTv.setText(String.valueOf("Total: "+order.getPriceTotal()));
        restaurantTv.setText(order.getRestaurant().getName());
        restaurantAddress.setText(order.getRestaurant().getAddress());
    }

    @Override
    public void onClick(View v) {

    }
}
