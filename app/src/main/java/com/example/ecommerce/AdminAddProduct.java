package com.example.ecommerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.widget.Toast;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminAddProduct extends AppCompatActivity {

    private String categoryName , desciption,price,pName,saveCurrenrtDate,saveCurrentTime;
    private Button AddNewProductBtn;
    private EditText InputProductName,InputProductPrice,  InputProductDescription;
    private ImageView InputProductPic;
    private ProgressDialog loadingBar;
    private static final int gallerypick=1;
    private Uri ImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        categoryName=getIntent().getExtras().get("category").toString();

        AddNewProductBtn= (Button) findViewById(R.id.add_new_product);
        InputProductPic = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        loadingBar = new ProgressDialog(this);

        InputProductPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                OpenGallery();
            }
        });

        AddNewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });


    }



    private void OpenGallery() {

        //define the intent
        Intent galleryIntent=new Intent();
        //pick the photo from gallery
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        //determine the type pg the picked object
        galleryIntent.setType("image");
        startActivityForResult(galleryIntent,gallerypick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null){

            ImageUri=data.getData();
            InputProductPic.setImageURI(ImageUri);


        }
    }

    private void ValidateProductData() {

        //apply Data to check it

        desciption=InputProductDescription.getText().toString();
        price=InputProductPrice.getText().toString();
       pName=InputProductName.getText().toString();

       //make sure data is good

       if(ImageUri==null){

           Toast.makeText(this, "Product image is mandatory", Toast.LENGTH_SHORT).show();
       } else if(TextUtils.isEmpty(desciption))
        {
            Toast.makeText(this, "please write product description", Toast.LENGTH_SHORT).show();
        }

       else if(TextUtils.isEmpty(price)) {
           Toast.makeText(this, "please write product price", Toast.LENGTH_SHORT).show();
       }

       else if(TextUtils.isEmpty(pName)) {
           Toast.makeText(this, "please write product name", Toast.LENGTH_SHORT).show();
       }
       //if all the data is complete so store it
       else {
           storeProductInformation();
       }


    }

    private void storeProductInformation() {

        //data about date and time of the picture

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentData = new SimpleDateFormat("MM dd, yyyy");
        saveCurrenrtDate=currentData.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());



    }
}