package com.example.eataly.ui.activities;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.eataly.services.AppDatabase;
import com.example.eataly.services.RestController;
import com.example.eataly.ui.adapters.OrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, OrderAdapter.OnPriceChangedListener {

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
        restaurantTv=findViewById(R.id.restaurant_name);
        restaurantAddress=findViewById(R.id.restaurant_address);
        totalTv=findViewById(R.id.total_order_tv);
        productRv=findViewById(R.id.product_order_rv);
        new ReadOrder().execute();
    }

   private void dataBind(){
       layoutManager=new LinearLayoutManager(this);
       adapter=new OrderAdapter(this, (ArrayList<Product>) order.getProducts());
       adapter.setOnPriceChanged(this);
       productRv.setLayoutManager(layoutManager);
       productRv.setAdapter(adapter);
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

       /* RestController restController = new RestController(this);
        restController.postRequest(this,Order.ENDPOINT, map,this,this);*/
    }

    @Override
    public void onPriceChanged(Product p) {
        order.setPriceTotal(order.getPriceTotal()-(p.getQuantity()*p.getPrice()));
        order.removeItem(p);
        totalTv.setText("Total: "+String.valueOf(order.getPriceTotal()));
    }

    public class ReadOrder extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase dbInstance = AppDatabase.getAppDatabase(CheckoutActivity.this);
            List<Order> orders = dbInstance.orderDao().getAll();
            order = orders.get(0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dataBind();
        }
    }
}
