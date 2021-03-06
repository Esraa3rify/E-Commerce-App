package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerce.cart.cartFragment;
import com.example.model.users;
import com.example.prevalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class productdetail extends AppCompatActivity {

    private Button addToCart;
    private ImageView productImageDetails;
    private ElegantNumberButton numberBtn;
    private TextView productPrice, productDescription, productName;
    private String productId = "", state = "Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);

        // productId=getIntent().getStringExtra("pid");

        numberBtn = (ElegantNumberButton) findViewById(R.id.elegantNumberButton);
        productImageDetails = (ImageView) findViewById(R.id.product_image_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productName = (TextView) findViewById(R.id.product_name_details);
        addToCart = (Button) findViewById(R.id.AddToCArtBtn);

        getProductDetails(productId);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state.equals("Order placed!") || state.equals("Order shipped!")){
                    Toast.makeText(productdetail.this, "You can add more products, once your order is shipped!", Toast.LENGTH_SHORT).show();
                }else {
                    addingToCartList();
                }


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
    }

    private void addingToCartList() {

        String saveCurrentDate, saveCurrentTime;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentData = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentData.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //create new node
        //DatabaseReference CartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        //create new root->cart list

        final HashMap<String, Object> CartMap = new HashMap<>();
        CartMap.put("pid", productId);
        CartMap.put("name", productName.getText().toString());
        CartMap.put("price", productPrice.getText().toString());
        CartMap.put("date", saveCurrentDate);
        CartMap.put("time", saveCurrentTime);
        CartMap.put("quantity", numberBtn.getNumber());
        CartMap.put("discount", "");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference CartListRef = database.getReference().child("Cart List");
        try {
            CartListRef.child("User view").child(users.getPhoneNum())
                    .child("products").child(productId)
                    .updateChildren(CartMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                CartListRef.child("Admin view").child(users.getPhoneNum())
                                        .child("products").child(productId)
                                        .updateChildren(CartMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(productdetail.this, "Added to cart successfully!", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(productdetail.this, navDrawer.class);
                                                    startActivity(intent);


                                                }
                                            }
                                        });

                            }

                        }
                    });


        } catch (NullPointerException ignored) {


        }
    }

    private void getProductDetails(String productId) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("products");
        productRef.child("productId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    products product = snapshot.getValue(products.class);
                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImageDetails);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void CheckOrderState() {


        try {


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference OrdersRef = database.getReference();
            OrdersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(prevalent.currentUserOnline.getPhoneNum());

            OrdersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        String shippingState = snapshot.child("state").getValue().toString();
                        if (shippingState.equals("shipped")) {
                            state="Order shipped!";

                        }else if (shippingState.equals("Not shipped")) {
                            state="Order placed!";

                        }

                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (NullPointerException ignored) {

        }
    }
}