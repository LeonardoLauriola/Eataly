package com.example.eataly.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.eataly.R;
import com.example.eataly.datamodels.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter {
    private LayoutInflater inflater;
    private ArrayList<Product> products;
    private Context context;

    public ProductAdapter(Context context, ArrayList<Product> products){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.products=products;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view=inflater.inflate(R.layout.item_product, viewGroup,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ProductViewHolder productViewHolder= (ProductViewHolder) viewHolder;
        productViewHolder.productName.setText(products.get(i).getName());
        productViewHolder.prizeTv.setText(String.valueOf(products.get(i).getPrice()).concat("€"));
        productViewHolder.quantityTv.setText(String.valueOf(products.get(i).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public OnQuantityChangeListener getOnQuantityChangeListener() {
        return onQuantityChangeListener;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener onQuantityChangeListener) {
        this.onQuantityChangeListener = onQuantityChangeListener;
    }

    public interface OnQuantityChangeListener{
        public void onChange(float prize);
    }

    public OnQuantityChangeListener onQuantityChangeListener;

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView productName,quantityTv,prizeTv;
        public Button plusBtn,minusBtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_tv);
            plusBtn=itemView.findViewById(R.id.plus_btn);
            minusBtn=itemView.findViewById(R.id.minus_btn);
            quantityTv=itemView.findViewById(R.id.quantity_tv);
            prizeTv=itemView.findViewById(R.id.prize_tv);
            plusBtn.setOnClickListener(this);
            minusBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==plusBtn.getId()){
                products.get(getAdapterPosition()).increaseQuantity();
                notifyItemChanged(getAdapterPosition());
                float f=products.get(getAdapterPosition()).getPrice();
                onQuantityChangeListener.onChange(f);
            }else{
                if(v.getId()==minusBtn.getId()){
                    if(products.get(getAdapterPosition()).getQuantity()==0)return;
                    products.get(getAdapterPosition()).decreaseQuantity();
                    notifyItemChanged(getAdapterPosition());
                    onQuantityChangeListener.onChange(products.get(getAdapterPosition()).getPrice()*(-1));
                }
            }
        }
    }
}