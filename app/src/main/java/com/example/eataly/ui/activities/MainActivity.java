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
import com.example.eataly.datamodels.Restaurant;
import com.example.eataly.ui.adapters.RestaurantAdapter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView restaurantRv;
    RecyclerView.LayoutManager layoutManager;
    RestaurantAdapter adapter;
    ArrayList<String> a;
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
        }else if(item.getItemId()==R.id.checkot_menu){
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

    private ArrayList<Restaurant> getData(){
        ArrayList<Restaurant>a =new ArrayList<Restaurant>();

        a.add(new Restaurant("McDonald's","via nazionale", 12.4f,"https://www.mcdonalds.com.my/images/sharer/logo-social.png"));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f,"https://www.prague.eu/object/1839/4564745-d8e972.jpg"));
        a.add(new Restaurant("KFC","Roma EST", 12.4f,"http://www.freelogovectors.net/wp-content/uploads/2018/03/kfc-logo03.png"));
        a.add(new Restaurant("Lo Chalet dei Gourmet","Via Tiburtina",10.0f,"https://gq-images.condecdn.net/image/YokKPaPokWK/crop/1620/f/Romantic-Restaurant-02-GQ-4Apr17_b.jpg"));
        return a;
    }

    private void saveLayoutPreferences(boolean isGridLayout){
        sharedPreferences=getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("VIEW_MODE",isGridLayout);
        editor.apply();
    }

    private RecyclerView.LayoutManager getLayoutManager(boolean isGridLayout){
        return isGridLayout ? new GridLayoutManager(this,2):new LinearLayoutManager(this);
    }

    private boolean getSavedLayoutManager(){
       sharedPreferences= getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        return sharedPreferences.getBoolean("VIEW_MODE",false);
    }

    private void setLayoutManager(){
        layoutManager=getLayoutManager(!adapter.getIsGridMode());
        adapter.setIsGridMode(!adapter.getIsGridMode());
        restaurantRv.setLayoutManager(layoutManager);
        restaurantRv.setAdapter(adapter);
        saveLayoutPreferences(adapter.getIsGridMode());
    }
}