package com.example.eataly.ui.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.eataly.Preferences;
import com.example.eataly.R;
import com.example.eataly.Utility;
import com.example.eataly.datamodels.Order;
import com.example.eataly.datamodels.Product;
import com.example.eataly.datamodels.Restaurant;
import com.example.eataly.services.AppDatabase;
import com.example.eataly.services.RestController;
import com.example.eataly.ui.adapters.ProductAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ShopActivity extends AppCompatActivity implements ProductAdapter.OnQuantityChangeListener, View.OnClickListener, Response.Listener<String>,Response.ErrorListener {
    private final static String TAG = ShopActivity.class.getSimpleName();
    private final static int LOGIN_REQUEST_CODE = 2002;
    private ArrayList<Product> products=new ArrayList<Product>();
    private RecyclerView productRv;
    private RecyclerView.LayoutManager layoutManager;
    private ProductAdapter adapter;
    private ImageView img;
    private TextView name_tv,total_tv,min_order_tv;
    private ProgressBar progressBar, spinner;
    private float priceTotal=0;
    private Button checkOutBtn;
    private RestController restController;
    private Restaurant restaurant;
    private Menu menu;

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String response = intent.getStringExtra("response");
            menu.findItem(R.id.login_menu).setTitle(R.string.profile);
            menu.findItem(R.id.logout_menu).setVisible(true);
        }
    };
    BroadcastReceiver mMessageReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            menu.findItem(R.id.login_menu).setTitle(R.string.login);
            menu.findItem(R.id.logout_menu).setVisible(false);
        }
    };

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
        min_order_tv=findViewById(R.id.min_order_tv);
        spinner = findViewById(R.id.progressBarMain);
        spinner.setVisibility(View.VISIBLE);
        layoutManager=new LinearLayoutManager(this);

        adapter = new ProductAdapter(this,products);
        adapter.setOnQuantityChangeListener(this);
        checkOutBtn.setOnClickListener(this);
        checkOutBtn.setEnabled(false);
        productRv.setLayoutManager(layoutManager);
        productRv.setAdapter(adapter);

        Intent i= getIntent();
        String id=i.getStringExtra("id");

        restController=new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT.concat("/").concat(id),this,this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("login"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver1,
                new IntentFilter("logout"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        this.menu = menu;
        if(Utility.isLogged(this)){
            menu.findItem(R.id.login_menu).setTitle(R.string.profile);
            menu.findItem(R.id.logout_menu).setVisible(true);
        }
        menu.findItem(R.id.change_layout).setVisible(false);
        return true;
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

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void onChange(float price) {
        updateTotal(price);
        updateProgressBar((int)(priceTotal*100));
    }

    public float getPriceTotal() {
        return priceTotal;
    }
    public void setPriceTotal(float priceTotal) {
        this.priceTotal = priceTotal;
    }
    private void enableButton(){
        checkOutBtn.setEnabled(priceTotal>=restaurant.getMin_order());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.login_menu) {
            if(Utility.isLogged(this)){
                startActivity(new Intent(ShopActivity.this, ProfileActivity.class));
            }else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }else{
            if(item.getItemId() == R.id.logout_menu){
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("logout"));
                Preferences.saveStringPreferences(this,"TOKEN","");
                Preferences.saveStringPreferences(this,"USER-ID","");
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.checkout_btn){
            if(Utility.isLogged(this)){
                new SaveOrderAsyncTask().execute();
            }else{
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG,error.getMessage());
        Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject obj=new JSONObject(response);
            restaurant=new Restaurant(obj);
            name_tv.setText(obj.getString("name"));
            progressBar.setMax((int)(obj.getDouble("min_order")*100));
            min_order_tv.setText(getString(R.string.min_order)+" "+(String.valueOf(obj.getDouble("min_order"))));
            Glide.with(this).load(obj.getString("image_url")).into(img);
            JSONArray jsonArray=obj.getJSONArray("products");
            for(int i = 0; i < jsonArray.length(); i++){
                products.add(new Product(jsonArray.getJSONObject(i)));
            }
            adapter.setData(products);
            spinner.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class SaveOrderAsyncTask extends AsyncTask<Void,Void,Void>{

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            Order order = new Order();
            order.setPriceTotal(priceTotal);

            ArrayList<Product> selected = adapter.getData();
            selected.removeIf(a -> a.getQuantity()<1);
            order.setProducts(selected);

            AppDatabase dbInstance = AppDatabase.getAppDatabase(ShopActivity.this);
            dbInstance.orderDao().insert(order);
            
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            startActivity(new Intent(ShopActivity.this, CheckoutActivity.class));
        }
    }
}