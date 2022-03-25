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

public class myadapter extends FirebaseRecyclerAdapter<products,myadapter.myViewHolder> {

     Context context;


    public myadapter(@NonNull FirebaseRecyclerOptions<products> options) {
        super(options);
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


    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull products products) {
        myViewHolder.productName.setText(products.getPname());
        myViewHolder.productDescription.setText(products.getDescription());
        myViewHolder.productPrice.setText(products.getPrice());
        Picasso.get().load(products.getImage()).into( myViewHolder.productImg);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
}
