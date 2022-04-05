package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.users;
import com.example.prevalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalActivity extends AppCompatActivity {

    private EditText nameEditText, AddressEditText, PhoneNumEditTxt, CityEditTxt;
    private Button confirmOrderBtn;
    private String totalAmount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final);


        confirmOrderBtn = (Button) findViewById(R.id.congirmButton);
        nameEditText = (EditText) findViewById(R.id.shipment_name);
        AddressEditText = (EditText) findViewById(R.id.shipment_Adress);
        PhoneNumEditTxt = (EditText) findViewById(R.id.shipment_Phone);
        CityEditTxt = (EditText) findViewById(R.id.shipment_city);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }


        });

    }

    private void Check() {

        if (TextUtils.isEmpty(nameEditText.getText().toString())) {
            Toast.makeText(this, "Please, Provide your full name!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PhoneNumEditTxt.getText().toString())) {

            Toast.makeText(this, "Please, Provide your Phone Number!", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(AddressEditText.getText().toString())) {

            Toast.makeText(this, "Please, Provide your Address!", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(CityEditTxt.getText().toString())) {

            Toast.makeText(this, "Please, Provide your City name!", Toast.LENGTH_SHORT).show();

        } else {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {

        String saveCurrentDate, saveCurrentTime;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentData = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentData.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        try {


            final DatabaseReference OrdersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                    .child(users.getPhoneNum());

            HashMap<String, Object> OrdersMap = new HashMap<>();
            OrdersMap.put("totalAmount", totalAmount);
            OrdersMap.put("name", nameEditText.getText().toString());
            OrdersMap.put("phone", PhoneNumEditTxt.getText().toString());
            OrdersMap.put("address", AddressEditText.getText().toString());
            OrdersMap.put("city", CityEditTxt.getText().toString());
            OrdersMap.put("date", saveCurrentDate);
            OrdersMap.put("time", saveCurrentTime);
            OrdersMap.put("state", "not shipped!");

            OrdersRef.updateChildren(OrdersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        FirebaseDatabase.getInstance().getReference()
                                .child("Cart List")
                                .child("User View")
                                .child(users.getPhoneNum())
                                .removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ConfirmFinalActivity.this, " Your final Order has been placed successfully!", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(ConfirmFinalActivity.this, navDrawer.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                    }

                }
            });

        } catch (NullPointerException ignored) {

        }
    }
}