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
import android.widget.Toast;

import com.example.model.User;
import com.example.prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

      Button mainLogin, JoinNow;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLogin=(Button) findViewById(R.id.mainLoginBtn);
        JoinNow=(Button) findViewById(R.id.JoinNowBtn);
        LoadingBar=new ProgressDialog(this);

        Paper.init(this);

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


       //read the data from the android memory to remember it if the user open the app again

       String UserPhoneKey=  Paper.book().read(prevalent.UserPhoneKey);
       String UserPasKey= Paper.book().read(prevalent.UserPasswordKey);

       //make sure that the pass and phoneNum is not null or empty

       if(UserPhoneKey !=""&&UserPasKey !=""){
           if(!TextUtils.isEmpty(UserPhoneKey)&& !TextUtils.isEmpty(UserPasKey)){
               AllowAccess(UserPhoneKey,UserPasKey);


               LoadingBar.setTitle("Already logged in");
               LoadingBar.setMessage("Please Wait.....");
               LoadingBar.setCanceledOnTouchOutside(false);
               LoadingBar.show();

           }
       }
    }

    private void AllowAccess(String Phone, String passWord) {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.child("users").child(Phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    User userData = snapshot.getValue(User.class);
                    if (userData != null && userData.getPass().equals(passWord)) {


                        Toast.makeText(MainActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                        LoadingBar.dismiss();

                        Intent intent= new Intent(MainActivity.this,homeActivity2.class);
                        startActivity(intent);


                    }else{
                        LoadingBar.dismiss();
                        Toast.makeText(MainActivity.this, "Password is incorrect!", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(MainActivity.this, "Account with this phone number " + Phone + "is not valid", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


}