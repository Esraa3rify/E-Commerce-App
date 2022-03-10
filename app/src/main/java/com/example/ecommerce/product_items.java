package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.ecommerce.settings.settingsFragment;

public class product_items extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_items);



        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container_view_tag,new settingsFragment()).commit();

        findViewById(R.id.nav_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view_tag,new settingsFragment()).commit();
            }
        });
    }
}