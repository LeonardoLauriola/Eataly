package com.example.eataly.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eataly.Preferences;
import com.example.eataly.R;
import com.example.eataly.datamodels.Order;
import com.example.eataly.datamodels.Product;
import com.example.eataly.services.RestController;
import com.example.eataly.ui.adapters.OrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, OrderAdapter.OnPriceChangedListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private TextView restaurantTv,restaurantAddress,totalTv;
    private RecyclerView productRv;
    private LinearLayoutManager layoutManager;
    private Button payBtn;
    private OrderAdapter adapter;
    private Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        payBtn = findViewById(R.id.pay_btn);
        payBtn.setOnClickListener(this);
        /*restaurantTv=findViewById(R.id.restaurant_name);
        restaurantAddress=findViewById(R.id.restaurant_address);
        totalTv=findViewById(R.id.total_order_tv);
        productRv=findViewById(R.id.product_order_rv);
        dataBind();
        layoutManager=new LinearLayoutManager(this);
        adapter=new OrderAdapter(this,order.getProducts());
        adapter.setOnPriceChanged(this);
        productRv.setLayoutManager(layoutManager);
        productRv.setAdapter(adapter);*/
    }

   private void dataBind(){
        setPrice();
        totalTv.setText(String.valueOf("Total: "+order.getPriceTotal()));
        restaurantTv.setText(order.getRestaurant().getName());
        restaurantAddress.setText(order.getRestaurant().getAddress());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == payBtn.getId()){
            doPayment();
        }
    }

    private void doPayment(){
        Intent i = getIntent();
        String user_id = Preferences.getSavedStringByKey(this,"USER-ID");
        Log.d("user-id",user_id);
        String restaurant_id = i.getStringExtra("RESTAURANT-ID");
        float amount = 14.4f;
        JSONArray array = new JSONArray();
        array.put(new JSONObject());
        JSONObject map = new JSONObject();

        try {
            map.put("restaurant",restaurant_id);
            map.put("user",user_id);
            map.put("amount",amount);
            map.put("product",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RestController restController = new RestController(this);
        restController.postRequest(this,Order.ENDPOINT, map,this,this);

    }
    private void setPrice(){
        float price=0;
        for(int i=0; i<order.getProducts().size();i++){
            price+= order.getProducts().get(i).getSubtotal();
        }
        order.setPriceTotal(price);
        totalTv.setText("Total: "+order.getPriceTotal());

    }

    @Override
    public void onPriceChanged(Product p) {
        order.removeItem(p);
        setPrice();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("risposta", error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Log.d("risposta", response.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
