package com.example.eataly.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eataly.R;
import com.example.eataly.datamodels.Order;
import com.example.eataly.datamodels.Product;
import com.example.eataly.datamodels.Restaurant;
import com.example.eataly.services.RestController;
import com.example.eataly.ui.adapters.RestaurantAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    private RecyclerView restaurantRv;
    private ProgressBar spinner;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;
    public SharedPreferences sharedPreferences;
    private static final String SharedPrefs="com.example.eataly.preferences";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int LOGIN_REQUEST_CODE = 1;
    private ArrayList<Restaurant> restaurants =new ArrayList<Restaurant>() ;
    private RestController restController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantRv = findViewById(R.id.places_rv);
        spinner = findViewById(R.id.progressBarMain);
        spinner.setVisibility(View.VISIBLE);
        layoutManager = getLayoutManager(getSavedLayoutManager());
        adapter = new RestaurantAdapter(this,restaurants);
        adapter.setIsGridMode(getSavedLayoutManager());
        restaurantRv.setLayoutManager(layoutManager);
        restaurantRv.setAdapter(adapter);

        restController=new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT,this,this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        if(adapter.getIsGridMode()) {
            (menu.findItem(R.id.change_layout)).setIcon(R.drawable.ic_list_view);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.login_menu) {

            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent, LOGIN_REQUEST_CODE);
            return true;
        }else if(item.getItemId()==R.id.checkout_menu){
                   startActivity(new Intent(this,CheckoutActivity.class));
                }else if(item.getItemId()==R.id.change_layout){
                        if(!adapter.getIsGridMode()) {
                            item.setIcon(R.drawable.ic_list_view);
                            setLayoutManager();
                        }else {
                            item.setIcon(R.drawable.ic_grid_view);
                            setLayoutManager();
                        }
                saveLayoutPreferences(adapter.getIsGridMode());
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveLayoutPreferences(boolean isGridLayout){
        sharedPreferences = getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("VIEW_MODE",isGridLayout);
        editor.apply();
    }

    private boolean getSavedLayoutManager(){
        sharedPreferences = getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        return sharedPreferences.getBoolean("VIEW_MODE",false);
    }

    private RecyclerView.LayoutManager getLayoutManager(boolean isGridLayout){
        return isGridLayout ? new GridLayoutManager(this,2):new LinearLayoutManager(this);
    }

    private void setLayoutManager(){
        layoutManager = getLayoutManager(!adapter.getIsGridMode());
        adapter.setIsGridMode(!adapter.getIsGridMode());
        restaurantRv.setLayoutManager(layoutManager);
        restaurantRv.setAdapter(adapter);
        saveLayoutPreferences(adapter.getIsGridMode());
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public ArrayList<Restaurant> getRestaurants(){
        return restaurants;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG,error.getMessage());
        Toast.makeText(this,error.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONArray jsonArray=new JSONArray(response);

            for(int i = 0; i < jsonArray.length(); i++){
                restaurants.add(new Restaurant(jsonArray.getJSONObject(i)));
            }

            adapter.setData(restaurants);
            spinner.setVisibility(View.GONE);

        } catch (JSONException e) {
            Log.e(TAG,e.getMessage());
        }

    }
}