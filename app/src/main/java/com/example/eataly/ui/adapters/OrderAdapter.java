package com.example.eataly.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.eataly.R;
import com.example.eataly.datamodels.Product;

import java.util.ArrayList;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderProductViewOlder> {
    private ArrayList<Product> dataSet;
    private Context context;
    private LayoutInflater inflater;


    public OrderAdapter(Context context, ArrayList<Product>dataSet){
        this.context=context;
        this.dataSet=dataSet;
        inflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public OrderProductViewOlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderProductViewOlder(inflater.inflate(R.layout.item_order_product,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductViewOlder orderProductViewOlder, int i) {
        Product product=dataSet.get(i);
        orderProductViewOlder.productNameTv.setText(product.getName());
        orderProductViewOlder.quantityTv.setText(String.valueOf(product.getQuantity()));
        orderProductViewOlder.subtotalTv.setText(String.valueOf(product.getSubtotal()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface OnPriceChangedListener{
        public void onPriceChanged(Product p);
    }

    public OnPriceChangedListener onPriceChanged;

    public OnPriceChangedListener getOnPriceChanged() {
        return onPriceChanged;
    }

    public void setOnPriceChanged(OnPriceChangedListener onPriceChanged) {
        this.onPriceChanged = onPriceChanged;
    }

    public class OrderProductViewOlder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView quantityTv, productNameTv, subtotalTv;
        public ImageButton removeBtn;
        public OrderProductViewOlder(@NonNull View itemView){
            super(itemView);
            quantityTv = itemView.findViewById(R.id.quantity_tv);
            productNameTv = itemView.findViewById(R.id.product_name_tv);
            subtotalTv = itemView.findViewById(R.id.subtotal_tv);
            removeBtn = itemView.findViewById(R.id.remove_btn);
            removeBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.remove_btn){
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Deseleziona prodotto")
                        .setMessage("Sei sicuro di voler rimuovere questo prodotto dall'ordine? ")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                onPriceChanged.onPriceChanged(dataSet.get(getAdapterPosition()));
                                notifyItemRemoved(getAdapterPosition());
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
    }
}
