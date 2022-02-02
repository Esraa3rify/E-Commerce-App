package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

      Button mainLogin, JoinNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLogin=(Button) findViewById(R.id.mainLoginBtn);
        JoinNow=(Button) findViewById(R.id.JoinNowBtn);


        mainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,LoginActivity2.class);
                startActivity(intent);
            }
        });

        JoinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,registerActivity.class);
                startActivity(intent);
            }
        });
    }
}