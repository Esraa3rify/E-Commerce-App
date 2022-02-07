package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.User;
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
       public String parentDBName ="users";
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

        String passWord = InputPassWord.getText().toString();
        String Phone = InputPhoneNumber.getText().toString();



        if (TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "Please, Write Your Password...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Phone)) {
            Toast.makeText(this, "Please, Write Your Phone Number...", Toast.LENGTH_SHORT).show();
        }
        else {

            LoadingBar.setTitle("Login Account");
            LoadingBar.setMessage("Please Wait, While we are Checking the Credentials");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();


            AllowAccessToAccount(passWord, Phone);
        }

    }
  private void AllowAccessToAccount(final String passWord,final String Phone){

        //knowing if thr remember me btn is checked or not.

        if(checkBoxV.isChecked()){
            //store the phone&pass into the android memory
            Paper.book().write(prevalent.UserPhoneKey,Phone);
            Paper.book().write(prevalent.UserPasswordKey,passWord);
        }

      final DatabaseReference RootRef;
      RootRef= FirebaseDatabase.getInstance().getReference();
      RootRef.child(parentDBName).child(Phone).addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {

              if (snapshot.exists()) {
                  User userData = snapshot.getValue(User.class);
                  if (userData != null && userData.getPass().equals(passWord)) {


                      Toast.makeText(LoginActivity2.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                      LoadingBar.dismiss();

                      Intent intent= new Intent(LoginActivity2.this,homeActivity2.class);
                      startActivity(intent);


                  }else{
                      LoadingBar.dismiss();
                      Toast.makeText(LoginActivity2.this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
                  }


              } else {
                  Toast.makeText(LoginActivity2.this, "Account with this phone number " + Phone + "is not valid", Toast.LENGTH_SHORT).show();
                  LoadingBar.dismiss();
              }


          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }

      });
      }


  }


