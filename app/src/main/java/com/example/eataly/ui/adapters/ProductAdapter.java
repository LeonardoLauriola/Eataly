package com.example.eataly.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.example.eataly.R;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private ArrayList<String> products;
    private Context context;

    public ProductAdapter(Context context, ArrayList<String> products){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.products=products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        view=inflater.inflate(R.layout.item_product, viewGroup,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ProductViewHolder productViewHolder= (ProductViewHolder) viewHolder;
        productViewHolder.productName.setText(products.get(i));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder
    {
        public TextView productName;
        public NumberPicker numberPicker;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_tv);
            numberPicker=itemView.findViewById(R.id.number_picker);
        }
    }
}
