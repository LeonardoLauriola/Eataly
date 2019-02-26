package com.example.eataly.ui.adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
    private ArrayList<Product> data;
    private Context context;

    public ProductAdapter(Context context, ArrayList<Product> products){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.data=products;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateProductById(String id){
        Product p1 = data.stream().filter(product -> product.getId().equals(id)).findFirst().get();
        onQuantityChangeListener.onChange((p1.getPrice()*p1.getQuantity())*-1);
        p1.setQuantity(0);
        notifyDataSetChanged();
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
        productViewHolder.productName.setText(data.get(i).getName());
        productViewHolder.prizeTv.setText(String.valueOf(data.get(i).getPrice()).concat("€"));
        productViewHolder.quantityTv.setText(String.valueOf(data.get(i).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public OnQuantityChangeListener getOnQuantityChangeListener() {
        return onQuantityChangeListener;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener onQuantityChangeListener) {
        this.onQuantityChangeListener = onQuantityChangeListener;
    }

    public interface OnQuantityChangeListener{
        void onChange(float prize);
    }

    public ArrayList<Product> getData() {
        return data;
    }

    public void setData(ArrayList<Product>data){
        this.data = data;
        notifyDataSetChanged();
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
                data.get(getAdapterPosition()).increaseQuantity();
                notifyItemChanged(getAdapterPosition());
                float f=data.get(getAdapterPosition()).getPrice();
                onQuantityChangeListener.onChange(f);
            }else{
                if(v.getId()==minusBtn.getId()){
                    if(data.get(getAdapterPosition()).getQuantity()==0)return;
                    data.get(getAdapterPosition()).decreaseQuantity();
                    notifyItemChanged(getAdapterPosition());
                    onQuantityChangeListener.onChange(data.get(getAdapterPosition()).getPrice()*(-1));
                }
            }
        }
    }
}