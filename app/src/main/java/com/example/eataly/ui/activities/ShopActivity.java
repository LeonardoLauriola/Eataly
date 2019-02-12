package com.example.eataly.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.eataly.R;
import com.example.eataly.datamodels.Restaurant;
import com.example.eataly.ui.adapters.ProductAdapter;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements ProductAdapter.OnQuantityChangeListener, View.OnClickListener {
    private RecyclerView productRv;
    private RecyclerView.LayoutManager layoutManager;
    private ProductAdapter adapter;
    private ImageView img;
    private TextView name_tv,total_tv;
    private ProgressBar progressBar;
    private float priceTotal=0;
    private Button checkOutBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop);
        img=findViewById(R.id.restaurant_img);
        name_tv=findViewById(R.id.restaurant_name);
        productRv=findViewById(R.id.product_place_rv);
        total_tv=findViewById(R.id.total_tv);
        progressBar=findViewById(R.id.progressBar);
        checkOutBtn=findViewById(R.id.checkout_btn);
        layoutManager=new LinearLayoutManager(this);

        ArrayList<Restaurant> p=MainActivity.getData();
        adapter=new ProductAdapter(this, p.get(0).getProducts());
        adapter.setOnQuantityChangeListener(this);
        checkOutBtn.setOnClickListener(this);
        checkOutBtn.setEnabled(false);
        productRv.setLayoutManager(layoutManager);
        productRv.setAdapter(adapter);

        Intent i= getIntent();
        String s = i.getStringExtra("img");
        String s1 = i.getStringExtra("name");
        name_tv.setText(s1);
        progressBar.setMax((int)p.get(0).getMin_order()*100);

        Glide.with(this).load(s).into(img);
    }

    public void updateProgressBar(int price){
        progressBar.setProgress(price);
    }

    public void updateTotal(float price){
        setPriceTotal(getPriceTotal()+price);
        total_tv.setText("Total: "+" "+(String.valueOf(getPriceTotal())));
        enableButton();
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

    @Override
    public void onChange(float price) {
        updateTotal(price);
        updateProgressBar((int)priceTotal*100);
    }

    public float getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(float priceTotal) {
        this.priceTotal = priceTotal;
    }


    private void enableButton(){
        checkOutBtn.setEnabled(priceTotal>=MainActivity.getData().get(0).getMin_order());
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.checkout_btn){
          startActivity(new Intent(this, CheckoutActivity.class));
        }
    }
}