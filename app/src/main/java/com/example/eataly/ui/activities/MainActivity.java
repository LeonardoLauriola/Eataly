package com.example.eataly.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.eataly.R;
import com.example.eataly.datamodels.Order;
import com.example.eataly.datamodels.Product;
import com.example.eataly.datamodels.Restaurant;
import com.example.eataly.ui.adapters.RestaurantAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView restaurantRv;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;
    public SharedPreferences sharedPreferences;
    private static final String SharedPrefs="com.example.eataly.preferences";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantRv=findViewById(R.id.places_rv);
        layoutManager=getLayoutManager(getSavedLayoutManager());
        adapter=new RestaurantAdapter(this,getData());
        adapter.setIsGridMode(getSavedLayoutManager());
        restaurantRv.setLayoutManager(layoutManager);
        restaurantRv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
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
        ArrayList<Restaurant>a =new ArrayList<Restaurant>();
        Restaurant r;
        ArrayList<Product> prod=new ArrayList<>();

        prod.add(new Product("Pasta",0,10.5f));
        prod.add(new Product("Pizza",0,10.5f));
        prod.add(new Product("Bruschetta",0,10.5f));
        prod.add(new Product("Cipolle fritte",0,10.5f));
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

        return a;
    }
    public static Order GetOrder(){
        Order o=new Order();
        o.setRestaurant(getData().get(0));
        o.setProducts(getData().get(0).getProducts());
        o.setPriceTotal(30.00f);
        return o;
    }
}