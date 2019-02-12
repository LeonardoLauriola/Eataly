package com.example.eataly.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
import com.example.eataly.ui.adapters.RestaurantAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView restaurantRv;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;
    public SharedPreferences sharedPreferences;
    private static final String SharedPrefs="com.example.eataly.preferences";
    private static final String TAG=MainActivity.class.getSimpleName();
    public ArrayList<Restaurant> restaurants ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantRv = findViewById(R.id.places_rv);
        layoutManager = getLayoutManager(getSavedLayoutManager());
        adapter = new RestaurantAdapter(this,getData());
        adapter.setIsGridMode(getSavedLayoutManager());
        restaurantRv.setLayoutManager(layoutManager);
        restaurantRv.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://5ba19290ee710f0014dd764c.mockapi.io/api/v1/restaurant";

        StringRequest stringRequest=new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG,response);
                        //parsing
                        try {
                            JSONArray restaurantJsonArray = new JSONArray(response);
                            for(int i = 0; i < restaurantJsonArray.length(); i++){
                                Restaurant restaurant = new Restaurant(restaurantJsonArray.getJSONObject(i));
                                restaurants.add(restaurant);
                            }
                            adapter.setData(restaurants);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,error.getMessage()+" "+error.networkResponse.statusCode);
                    }
                }
        );

        queue.add(stringRequest);

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
            startActivity(new Intent(this, LoginActivity.class));
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
        sharedPreferences=getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("VIEW_MODE",isGridLayout);
        editor.apply();
    }

    private boolean getSavedLayoutManager(){
        sharedPreferences= getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        return sharedPreferences.getBoolean("VIEW_MODE",false);
    }

    private RecyclerView.LayoutManager getLayoutManager(boolean isGridLayout){
        return isGridLayout ? new GridLayoutManager(this,2):new LinearLayoutManager(this);
    }

    private void setLayoutManager(){
        layoutManager=getLayoutManager(!adapter.getIsGridMode());
        adapter.setIsGridMode(!adapter.getIsGridMode());
        restaurantRv.setLayoutManager(layoutManager);
        restaurantRv.setAdapter(adapter);
        saveLayoutPreferences(adapter.getIsGridMode());
    }

    public static ArrayList<Restaurant> getData(){
       /* ArrayList<Restaurant>a =new ArrayList<Restaurant>();
        Restaurant r;
        ArrayList<Product> prod=new ArrayList<>();

        prod.add(new Product("Pasta",1,10.5f));
        prod.add(new Product("Pizza",3,10.5f));
        prod.add(new Product("Bruschetta",0,10.5f));
        prod.add(new Product("Cipolle fritte",1,10.5f));
        prod.add(new Product("Fried Chicken",0,10.5f));
        r=new Restaurant("McDonald's","via Nazionale", 30f,"https://www.mcdonalds.com.my/images/sharer/logo-social.png");
        r.setProducts(prod);
        a.add(r);
        r=new Restaurant("Burger King","via Italia", 40f,"https://www.prague.eu/object/1839/4564745-d8e972.jpg");
        r.setProducts(prod);
        a.add(r);
        r=new Restaurant("KFC","Roma EST", 12.4f,"http://www.freelogovectors.net/wp-content/uploads/2018/03/kfc-logo03.png");
        r.setProducts(prod);
        a.add(r);
        r=new Restaurant("Lo Chalet dei Gourmet","Via Tiburtina",10.0f,"https://gq-images.condecdn.net/image/YokKPaPokWK/crop/1620/f/Romantic-Restaurant-02-GQ-4Apr17_b.jpg");
        r.setProducts(prod);
        a.add(r);

      */ return null;
    }

    public static Order GetOrder(){
        Order o=new Order(getData().get(0),30.0f,getData().get(0).getProducts());
        return o;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
    public ArrayList<Restaurant> getRestaurants(){
        return restaurants;
    }
}