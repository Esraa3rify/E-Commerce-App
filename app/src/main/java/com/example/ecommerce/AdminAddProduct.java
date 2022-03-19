package com.example.ecommerce;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddProduct extends AppCompatActivity {

    private String categoryName , desciption,price,pName,saveCurrenrtDate,saveCurrentTime;
    private Button AddNewProductBtn;
    private EditText InputProductName,InputProductPrice,  InputProductDescription;
    private ImageView InputProductPic;
    private ProgressDialog loadingBar;
    private static final int gallerypick=1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageURL;
    private StorageReference productImagesRef;
    private DatabaseReference productsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);
        //categoryName=getIntent().getExtras().get("category").toString();

        productImagesRef= FirebaseStorage.getInstance().getReference().child("product images");
        //take  reference from DB
        productsReference= FirebaseDatabase.getInstance().getReference().child("products");

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


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), gallerypick);

        //define the intent
       // Intent galleryIntent=new Intent();
        //pick the photo from gallery
      //  galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        //determine the type pg the picked object
       // galleryIntent.setType("image");
       // startActivityForResult(galleryIntent,gallerypick);
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

        loadingBar.setTitle("Adding new product");
        loadingBar.setMessage("Dear Admin, Please Wait, While we are adding the product!");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        //data about date and time of the picture

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentData = new SimpleDateFormat("MM dd, yyyy");
        saveCurrenrtDate=currentData.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        //generate random key by compining the time & data for each product to be hightly unique

        productRandomKey= saveCurrenrtDate + saveCurrentTime;

        //storing the image to the DB

        StorageReference filepath=productImagesRef.child(ImageUri.getLastPathSegment()+productRandomKey+ ".jpg");

        //Uploading the image from the db to the app

        final UploadTask uploadTask= filepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            
            //check if the uploading fail

            public void onFailure(@NonNull Exception e) {
                String messege= e.toString();
                Toast.makeText(AdminAddProduct.this, "Error: ", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

            //if the uploading success

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminAddProduct.this, "product image uploaded successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(! task.isSuccessful()){

                        throw task.getException();
                        }

                        //downloading the image to the firebase

                        downloadImageURL=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){
                            Log.d("ATTENTION","ERROR");
                            downloadImageURL=task.getResult().toString();

                            Toast.makeText(AdminAddProduct.this, "getting the image uri successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }



                    }
                });
            }
        });



    }

    private void SaveProductInfoToDatabase() {

        HashMap<String, Object> productMap=new HashMap<>();

        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrenrtDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",desciption);
        productMap.put("image",downloadImageURL);
        productMap.put("category",categoryName);
        productMap.put("price",price);
        productMap.put("pname",pName);

        productsReference.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            Intent intent = new Intent(AdminAddProduct.this, navDrawer.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddProduct.this, "product is added successfully...", Toast.LENGTH_SHORT).show();
                        } else{
                            loadingBar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(AdminAddProduct.this, "Error: "+ message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}