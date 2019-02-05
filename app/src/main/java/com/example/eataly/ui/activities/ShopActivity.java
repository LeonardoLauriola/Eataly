package com.example.eataly.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eataly.R;

public class ShopActivity extends AppCompatActivity {
    ImageView img;
    TextView name_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        String s;
        img=findViewById(R.id.restaurant_img);
        name_tv=findViewById(R.id.name_tv);
        Intent i= getIntent();

        s = i.getStringExtra("img");
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
