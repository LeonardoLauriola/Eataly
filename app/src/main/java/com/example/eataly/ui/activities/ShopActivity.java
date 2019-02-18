package com.example.eataly.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
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
import com.example.eataly.R;
import com.example.eataly.datamodels.Product;
import com.example.eataly.datamodels.Restaurant;
import com.example.eataly.services.RestController;
import com.example.eataly.ui.adapters.ProductAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements ProductAdapter.OnQuantityChangeListener, View.OnClickListener, Response.Listener<String>,Response.ErrorListener {

    private RecyclerView productRv;
    private RecyclerView.LayoutManager layoutManager;
    private ProductAdapter adapter;
    private ImageView img;
    private TextView name_tv,total_tv,min_order_tv;
    private ProgressBar progressBar;
    private float priceTotal=0;
    private Button checkOutBtn;
    private RestController restController;
    private ProgressBar spinner;
    private ArrayList<Product> products=new ArrayList<Product>();
    private Restaurant restaurant;
    private final static String TAG = ShopActivity.class.getSimpleName();
    private final static int LOGIN_REQUEST_CODE = 2;
    private Menu menu;

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

        adapter=new ProductAdapter(this,products);
        adapter.setOnQuantityChangeListener(this);
        checkOutBtn.setOnClickListener(this);
        checkOutBtn.setEnabled(false);
        productRv.setLayoutManager(layoutManager);
        productRv.setAdapter(adapter);

        Intent i= getIntent();
        String id=i.getStringExtra("id");

        restController=new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT.concat("/").concat(id),this,this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      return false;
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
        checkOutBtn.setEnabled(priceTotal>=restaurant.getMin_order());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.login_menu) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            menu.findItem(R.id.login_menu).setTitle(R.string.profile).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    startActivity(new Intent(ShopActivity.this, ProfileActivity.class));
                    return true;
                }
            });
        }
        else{
            //TODO login not ok
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.checkout_btn){
          startActivity(new Intent(this, CheckoutActivity.class));
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
}