package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView   tShirts  ,sprotsTshirts,dresses,Jackets;
    private ImageView glasses,bags,hats,shoes;
    private ImageView headPhones,LapTops,Watches,mobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        tShirts = (ImageView) findViewById(R.id.t_shirts);
        sprotsTshirts = (ImageView) findViewById(R.id.sports_t_shirts);
        dresses = (ImageView) findViewById(R.id.female_dresses);
        Jackets = (ImageView) findViewById(R.id.sweathers);

        glasses = (ImageView) findViewById(R.id.glasses);
        hats = (ImageView) findViewById(R.id.hats_caps);
        bags = (ImageView) findViewById(R.id.purses_bags_wallets);
        shoes = (ImageView) findViewById(R.id.shoes);

        headPhones = (ImageView) findViewById(R.id.headphones_handfree);
        LapTops = (ImageView) findViewById(R.id.laptop_pc);
        Watches = (ImageView) findViewById(R.id.watches);
        mobiles = (ImageView) findViewById(R.id.mobilephones);

        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);

                //the values which stored in firebase

                intent.putExtra("category","tshirts");
                startActivity(intent);
            }
        });


        sprotsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","sportstshirts");
                startActivity(intent);
            }
        });

        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","dresses");
                startActivity(intent);
            }
        });

        Jackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","jackets");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","glasses");
                startActivity(intent);
            }
        });

       bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","bags");
                startActivity(intent);
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","hats");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });

        headPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","headphones");
                startActivity(intent);
            }
        });

        LapTops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","laptops");
                startActivity(intent);
            }
        });

        Watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","watches");
                startActivity(intent);
            }
        });

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddProduct.class);
                intent.putExtra("category","mobiles");
                startActivity(intent);
            }
        });
    }


}