package com.example.ecommerce;

import android.content.Context;
import android.content.Intent;
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

public class myadapter extends FirebaseRecyclerAdapter<products,ProductViewHolder> {

    Context context;
    public RecyclerView searchList;


    public myadapter(@NonNull FirebaseRecyclerOptions<products> options) {
        super(options);

    }
//
//    public class myViewHolder extends RecyclerView.ViewHolder{
//
//        TextView productName,productDescription,productPrice;
//        ImageView productImg;
//
//        public myViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            productName=itemView.findViewById(R.id.product_name);
//            productDescription=itemView.findViewById(R.id.product_description);
//            productPrice=itemView.findViewById(R.id.product_price);
//            productImg=itemView.findViewById(R.id.product_image);
//
//        }
//    }


        @Override
        protected void onBindViewHolder (@NonNull ProductViewHolder productViewHolder,int i,
        @NonNull products products){

            productViewHolder.txtProductName.setText(products.getPname());
            productViewHolder.txtProductDescription.setText(products.getDescription());
            productViewHolder.txtProductPrice.setText(products.getPrice());
            Picasso.get().load(products.getImage()).into(productViewHolder.imageView);

            productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //to intent from adapter
                    //to putExtra in adapter
                    Intent intent = new Intent(v.getContext(), productdetail.class);
                    intent.putExtra("pid", products.getPid());
                    v.getContext().startActivity(intent);
                }
            });

        }


        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productsinfo, parent, false);
            ProductViewHolder holder = new ProductViewHolder(view);
            return holder;

        }





}
