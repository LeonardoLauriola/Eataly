package com.example.eataly.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eataly.R;
import com.example.eataly.datamodels.Restaurant;
import com.example.eataly.ui.activities.ShopActivity;

import java.util.ArrayList;

public class RestaurantAdapter  extends RecyclerView.Adapter {
    LayoutInflater inflater;
    private ArrayList<Restaurant> data;
    Context context;
    private boolean isGridMode;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if(getIsGridMode()) {
            view = inflater.inflate(R.layout.item_grid_restaurant, viewGroup, false);
        }else{
            view = inflater.inflate(R.layout.item_restaurant, viewGroup, false);
        }
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        RestaurantViewHolder vH = (RestaurantViewHolder) viewHolder;
        vH.restaurantName.setText(data.get(position).getName());
        vH.restaurantAddress.setText(data.get(position).getAddress());
        vH.restaurantMinOrder.setText(""+data.get(position).getMin_order());
        //vH.img.setImageResource(data.get(position).getImg());
        Glide.with(context).load(data.get(position).getImg()).into(vH.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public boolean getIsGridMode(){
        return isGridMode;
    }

    public void setIsGridMode(boolean isGrid){
        isGridMode=isGrid;
    }


    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {   public TextView restaurantName;
        public TextView restaurantAddress;
        public TextView restaurantMinOrder;
        public ImageView img;
        public CardView cardBtn;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.name_tv);
            restaurantAddress=itemView.findViewById(R.id.address_tv);
            restaurantMinOrder=itemView.findViewById(R.id.min_order_tv);
            cardBtn=itemView.findViewById(R.id.card_view);
            img=itemView.findViewById(R.id.imgv);
            cardBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.card_view){
                Intent intent = new Intent(context, ShopActivity.class);
                Restaurant item = data.get(getAdapterPosition());
                intent.putExtra("img", item.getImg());
                context.startActivity(intent);

            }
        }
    }
}
