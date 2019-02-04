package com.example.eataly.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.eataly.R;
import com.example.eataly.datamodels.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter  extends RecyclerView.Adapter {
    LayoutInflater inflater;
    private ArrayList<Restaurant> data;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_restaurant,viewGroup,false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RestaurantViewHolder vH = (RestaurantViewHolder) viewHolder;
        vH.restaurantName.setText(data.get(position).getName());
        vH.restaurantAddress.setText(data.get(position).getAddress());
        vH.restaurantMinOrder.setText(""+data.get(position).getMin_order());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder
    {   public TextView restaurantName;
        public TextView restaurantAddress;
        public TextView restaurantMinOrder;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.name_tv);
            restaurantAddress=itemView.findViewById(R.id.address_tv);
            restaurantMinOrder=itemView.findViewById(R.id.min_order_tv);

        }
    }
}
