package com.example.eataly.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    ArrayList<String> arrayList;
    public static boolean lay=true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurantRv=findViewById(R.id.places_rv);
        layoutManager=new LinearLayoutManager(this);
        adapter=new RestaurantAdapter(this,getData());

        restaurantRv.setLayoutManager(layoutManager);
        restaurantRv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
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
            lay=!lay;
            if(lay) {
                item.setIcon(R.drawable.ic_grid_view);
                                
            }else {
                item.setIcon(R.drawable.ic_list_view);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Restaurant> getData(){
        ArrayList<Restaurant>a =new ArrayList<Restaurant>();
        a.add(new Restaurant("McDonald's","Via Tiburtina",10.0f, R.drawable.immagine1));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f, R.drawable.immagine1));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f,R.drawable.immagine1));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f,R.drawable.immagine1));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f,R.drawable.immagine1));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f,R.drawable.immagine1));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f,R.drawable.immagine1));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f,R.drawable.immagine1));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f,R.drawable.immagine1));
        a.add(new Restaurant("Burger King","via nazionale", 12.4f,R.drawable.immagine1));
        a.add(new Restaurant("KFC","Roma est", 12.3f, R.drawable.immagine1));
        return a;
    }
}