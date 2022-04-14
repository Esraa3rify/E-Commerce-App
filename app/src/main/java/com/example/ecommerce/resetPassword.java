package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.users;
import com.example.prevalent.prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;

import de.javakaffee.kryoserializers.CollectionsEmptyListSerializer;

public class resetPassword extends AppCompatActivity {
    private String check="";
    private TextView pageTitle,titleQuestion;
    private EditText PhoneNum,question1,question2;
    private Button verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check=getIntent().getStringExtra("check");
        pageTitle=findViewById(R.id.resetpass);
        PhoneNum=findViewById(R.id.securityPhoneET);
        question1=findViewById(R.id.Question1);
        question2=findViewById(R.id.Question2);
        verifyBtn=findViewById(R.id.verifyBtn);
        titleQuestion=findViewById(R.id.titleQuestion);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(check.equals("settings")){
           pageTitle.setText("Set Question");
            PhoneNum.setVisibility(View.GONE);
            titleQuestion.setText("Answer the following security question!");
            verifyBtn.setText("set");

            displayPreviousAnswers();

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAnswers();
                }
            });



        }else if(check.equals("login")){
            PhoneNum.setVisibility(View.VISIBLE);

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();

                }
            });




        }
    }
    private void setAnswers(){


        String Ans1=question1.getText().toString().toLowerCase();
        String Ans2=question2.getText().toString().toLowerCase();

        if(question1.equals("") && question2.equals("")){

            Toast.makeText(resetPassword.this, "Please, Answer both questions!", Toast.LENGTH_SHORT).show();
        }else{

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("users")
                    .child(prevalent.currentUserOnline.getPhoneNum());
            // ref.child("security question")

            HashMap<String,Object>userDataMap=new HashMap<>();
            userDataMap.put("answer1",Ans1);
            userDataMap.put("answer2",Ans2);

            ref.child("security questions").updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(resetPassword.this, "You have answered the security questions successfully!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(resetPassword.this, navDrawer.class);
                        startActivity(intent);
                    }

                }
            });


        }


    }

    private void displayPreviousAnswers(){

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(prevalent.currentUserOnline.getPhoneNum());

        ref.child("security questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String ans1=snapshot.child("answer1").getValue().toString();
                    String ans2=snapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void verifyUser() {

        String phone = PhoneNum.getText().toString();
        String Ans1 = question1.getText().toString().toLowerCase();
        String Ans2 = question2.getText().toString().toLowerCase();

        if (!phone.equals("") && !Ans1.equals("") && !Ans2.equals("")) {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(phone);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    String mPhone = snapshot.child("phone").getValue().toString();

                    if (snapshot.hasChild("security questions")) {

                        String ans1 = snapshot.child("security questions").child("answer1").getValue().toString();
                        String ans2 = snapshot.child("security questions").child("answer2").getValue().toString();

                        if (!ans1.equals(Ans1)) {

                            Toast.makeText(resetPassword.this, "Your first answer is wrong!", Toast.LENGTH_SHORT).show();
                        } else if (!ans2.equals(Ans2)) {

                            Toast.makeText(resetPassword.this, "Your second answer is wrong!", Toast.LENGTH_SHORT).show();

                        } else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(resetPassword.this);
                            builder.setTitle("New password");

                            final EditText newPassword = new EditText(resetPassword.this);
                            newPassword.setHint("Write password here...");
                            builder.setView(newPassword);

                            builder.setPositiveButton("change", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if (!newPassword.getText().toString().equals("")) {

                                        ref.child("pass").setValue(newPassword.getText().toString())
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            Toast.makeText(resetPassword.this, "Password changed successfully!", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(resetPassword.this, LoginActivity2.class);
                                                            startActivity(intent);
                                                        }

                                                    }
                                                });
                                    }

                                }
                            });

                            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }

                    } else {

                        Toast.makeText(resetPassword.this, "You have not set the security questions!", Toast.LENGTH_SHORT).show();
                    }
                }

            else{

                    Toast.makeText(resetPassword.this, "This phone number not exist!", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }else {
            Toast.makeText(this, "Please, Complete the form!", Toast.LENGTH_SHORT).show();
        }

    }


}