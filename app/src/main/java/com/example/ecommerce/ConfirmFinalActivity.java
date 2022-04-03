package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ConfirmFinalActivity extends AppCompatActivity {

    private EditText nameEditText, AddressEditText, PhoneNumEditTxt,CityEditTxt;
    private Button confirmOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final);


        confirmOrderBtn=(Button) findViewById(R.id.congirmButton);
        nameEditText=(EditText) findViewById(R.id.shipment_name);
        AddressEditText=(EditText) findViewById(R.id.shipment_Adress);
        PhoneNumEditTxt=(EditText) findViewById(R.id.shipment_Phone);
        CityEditTxt=(EditText) findViewById(R.id.shipment_city);

    }
}