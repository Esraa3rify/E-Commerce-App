package com.example.ecommerce;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class myadapter extends FirebaseRecyclerAdapter<products,myadapter.myViewHolder> {


    public myadapter(@NonNull FirebaseRecyclerOptions<products> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull products products) {
        myViewHolder.productName.setText(products.getPname());
        myViewHolder.productDescription.setText(products.getDescription());
        myViewHolder.productPrice.setText(products.getPrice());
        Picasso.get().load(products.getImage()).into( myViewHolder.productImg);


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productsinfo,parent,false);

       return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView productName,productDescription,productPrice;
        ImageView productImg;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            productName=itemView.findViewById(R.id.product_name);
            productDescription=itemView.findViewById(R.id.product_description);
            productPrice=itemView.findViewById(R.id.product_price);
            productImg=itemView.findViewById(R.id.product_image);

        }
    }
}
