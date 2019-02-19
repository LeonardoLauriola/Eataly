package com.example.eataly.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.annotation.Nullable;

import com.example.eataly.Utility;
import com.example.eataly.datamodels.Restaurant;
import com.example.eataly.services.RestController;
import com.example.eataly.ui.adapters.RestaurantAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eataly.Preferences;
import com.example.eataly.R;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int LOGIN_REQUEST_CODE = 2001;
    private RecyclerView restaurantRv;
    private ProgressBar spinner;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;
    private ArrayList<Restaurant> restaurants =new ArrayList<Restaurant>() ;
    private RestController restController;
    private Menu menu;

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

        String pref = Preferences.getSavedStringByKey(this,"TOKEN");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;
        if(Utility.isLogged(this)) {
            menu.findItem(R.id.login_menu).setTitle(R.string.profile);
            menu.findItem(R.id.logout_menu).setVisible(true);
        }
        if (adapter.getIsGridMode()) {
            (menu.findItem(R.id.change_layout)).setIcon(R.drawable.ic_list_view);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.login_menu) {
            if(Utility.isLogged(this)){
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
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
                }else{
                    if(item.getItemId() == R.id.logout_menu){
                        Preferences.saveStringPreferences(this,"TOKEN","");
                        menu.findItem(R.id.logout_menu).setVisible(false);
                        menu.findItem(R.id.login_menu).setTitle(R.string.login);
                    }
                }
        return super.onOptionsItemSelected(item);
    }

    public void saveLayoutPreferences(boolean isGridLayout){
        Preferences.saveBooleanPreferences(this,"VIEW_MODE",isGridLayout);
    }

    public boolean getSavedLayoutManager(){
        return Preferences.getSavedBooleanByKey(this,"VIEW_MODE");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            menu.findItem(R.id.login_menu).setTitle(R.string.profile).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    return true;
                }
            });
            menu.findItem(R.id.logout_menu).setVisible(true);
        }
        else{
            //TODO login not ok
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