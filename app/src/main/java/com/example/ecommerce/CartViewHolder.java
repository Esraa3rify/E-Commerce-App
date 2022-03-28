package com.example.ecommerce;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName=itemView.findViewById(R.id.productNameCart);
        txtProductPrice=itemView.findViewById(R.id.productPriceCart);
        txtProductQuantity=itemView.findViewById(R.id.productQuantityCart);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(), false );
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
