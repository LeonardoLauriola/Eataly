package com.example.eataly.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eataly.R;
import com.example.eataly.datamodels.Restaurant;
import com.example.eataly.ui.adapters.ProductAdapter;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {
    private RecyclerView productRv;
    private RecyclerView.LayoutManager layoutManager;
    private ProductAdapter adapter;
    private ImageView img;
    private TextView name_tv;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        img=findViewById(R.id.restaurant_img);
        name_tv=findViewById(R.id.name_tv);
        productRv=findViewById(R.id.product_place_rv);
        layoutManager=new LinearLayoutManager(this);
        progressBar=findViewById(R.id.progressBar);
        ArrayList<Restaurant> p=MainActivity.getData();
        adapter=new ProductAdapter(this, p.get(0).getProducts());
        productRv.setLayoutManager(layoutManager);
        productRv.setAdapter(adapter);
        progressBar.setMax(10);
        Intent i= getIntent();
        String s = i.getStringExtra("img");
        Glide.with(this).load(s).into(img);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
