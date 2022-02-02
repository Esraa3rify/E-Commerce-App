package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.user;
import com.example.prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity2 extends AppCompatActivity {
      private   Button login;
       private EditText InputPhoneNumber,InputPassWord;
       private  ProgressDialog LoadingBar;
       private String parentDBName ="users";
       private CheckBox checkBoxV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        login=findViewById(R.id.secLoginBtn);
        InputPassWord=findViewById(R.id.loginPassNum);
        InputPhoneNumber=findViewById(R.id.loginPhoneNum);
        LoadingBar=new ProgressDialog(this);

        checkBoxV=findViewById(R.id.rememberMeCheck);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();

            }
        });
    }

    private void LoginUser(){

        String pass = InputPassWord.getText().toString();
        String phone = InputPhoneNumber.getText().toString();


        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please, Write Your Password...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please, Write Your Phone Number...", Toast.LENGTH_SHORT).show();
        }
        else {

            LoadingBar.setTitle("Login Account");
            LoadingBar.setMessage("Please Wait, While we are Checking the Credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();


            AllowAccessToAccount(pass, phone);
        }

    }
  private void AllowAccessToAccount(String pass,String phone){

      final DatabaseReference RootRef;
      RootRef= FirebaseDatabase.getInstance().getReference();
      RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.child(parentDBName).child(phone).exists()) {
                  user userData = snapshot.child(parentDBName).child(phone).getValue(user.class);


                  if (userData.getPhoneNum().equals(phone)) {

                      if (userData.getPass().equals(pass)) {


                          Toast.makeText(LoginActivity2.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                          LoadingBar.dismiss();


                      }
                  }





              } else {
                  Toast.makeText(LoginActivity2.this, "Account with this phone number " + phone + "is not valid", Toast.LENGTH_SHORT).show();
                  LoadingBar.dismiss();
              }
          }


          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });
  }

}