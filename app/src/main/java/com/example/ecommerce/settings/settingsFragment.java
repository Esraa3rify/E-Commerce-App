package com.example.ecommerce.settings;

import static android.app.Activity.RESULT_OK;

import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.resetPassword;
import com.example.model.users;
import com.example.prevalent.prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class settingsFragment extends Fragment {

    private CircleImageView profileImageView;
    private EditText fullNameEditText,userPhoneEditText, addressEditText;
    private TextView profileChangeTextBtn, closeTextBtn,saveTextBtn;

    private SettingsViewModel mViewModel;

    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";
    private Button setSecurityQuestions;

    Context thiscontext;

    public static settingsFragment newInstance() {
        return new settingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.settings_fragment, container, false);

        //used a view from inflate to initialize the user info

        profileImageView = view.findViewById(R.id.settingProfileImage);
        fullNameEditText = view. findViewById(R.id.settings_full_name);
        userPhoneEditText =view.findViewById(R.id.settings_phone_number);
        addressEditText = view. findViewById(R.id.settings_address);
        setSecurityQuestions=view.findViewById(R.id.securityQuestionsBtn);
        profileChangeTextBtn = view. findViewById(R.id.profile_image_change_btn);
        closeTextBtn = view. findViewById(R.id.close_settings_btn);
        saveTextBtn = view. findViewById(R.id.update_account_settings_btn);
        thiscontext = container.getContext();
        storageProfilePrictureRef= FirebaseStorage.getInstance().getReference().child("Profile picture");


        UserInfoDisplay( profileImageView, fullNameEditText,userPhoneEditText, addressEditText );


        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // = finish(); in Activity

                getActivity().onBackPressed();

              //Another way-> container.removeView(view);

            }
        });

        setSecurityQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(), resetPassword.class);
                i.putExtra("check","settings");
                startActivity(i);

            }
        });

        saveTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked")){

                        UserInfoSaved();

                }else{

                        UpdateOnlyUserInfo();

                }

            }

           
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checker="clicked";

                Fragment settings = new Fragment();
                CropImage.activity(imageUri)
                        .start(getContext(), settingsFragment.this);

            }
        });

        return view;
        

    }

    private void UpdateOnlyUserInfo() {

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("users");


        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("name", fullNameEditText.getText().toString());
        userMap. put("address", addressEditText.getText().toString());
        userMap. put("phoneOrder", userPhoneEditText.getText().toString());
        ref.child(prevalent.currentUserOnline.getPhoneNum()).updateChildren(userMap);

        Intent intent = new Intent(getActivity(),settingsFragment.class);
        Toast.makeText(getActivity(),"Profile info updated successfully.",Toast.LENGTH_SHORT).show();
        //getActivity().onBackPressed();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileImageView.setImageURI(imageUri);

        }else{

            Toast.makeText(getActivity(),"Error, Try Again.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),settingsFragment.class);
            getActivity().onBackPressed();

        }
    }

    private void UserInfoSaved() {

        if(TextUtils.isEmpty(fullNameEditText.getText().toString())){
            Toast.makeText(getActivity(),"Name is Mandatory!",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(addressEditText.getText().toString())){
            Toast.makeText(getActivity(),"Address is Mandatory!",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(userPhoneEditText.getText().toString())){
            Toast.makeText(getActivity(),"Phone is Mandatory!",Toast.LENGTH_SHORT).show();
            //if all the fields is not empty
        }else if(checker.equals("clicked")){
            UploadImage();
        }

    }

    private void UploadImage() {
        final ProgressDialog progressDialog=new ProgressDialog(thiscontext);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(imageUri!=null){
            //if there is no child (image) created yet in the database

            StorageReference fileRef=storageProfilePrictureRef.child(imageUri.getLastPathSegment() +  ".jpg");

            uploadTask=fileRef.putFile(imageUri);
             uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                Uri downloadUri = task.getResult();
                                myUrl = downloadUri.toString();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");


                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap.put("name", fullNameEditText.getText().toString());
                                userMap.put("address", addressEditText.getText().toString());
                                userMap.put("phoneOrder", userPhoneEditText.getText().toString());
                                userMap.put("image", myUrl);
                                ref.child(prevalent.currentUserOnline.getPhoneNum()).updateChildren(userMap);

                                progressDialog.dismiss();

                                Toast.makeText(getActivity(), "Profile info updated successfully.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getActivity(),settingsFragment.class);
                                getActivity().onBackPressed();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Error.", Toast.LENGTH_SHORT).show();


                            }


                        }
                    });
        }else{
            Toast.makeText(getActivity(),"Image is not selected.",Toast.LENGTH_SHORT).show();

        }

    }



    private void UserInfoDisplay(CircleImageView profileImageView, EditText fullNameEditText, EditText userPhoneEditText, EditText addressEditText) {

        DatabaseReference UserRef= FirebaseDatabase.getInstance().getReference().child("users").child(users.getPhoneNum());

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    if(snapshot.child("image").exists()){
                        String image=snapshot.child("image").getValue().toString();
                        String name=snapshot.child("name").getValue().toString();
                        String phone=snapshot.child("phoneNum").getValue().toString();
                        String address=snapshot.child("address").getValue().toString();

                        Picasso.get().load(image).into(profileImageView);
                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        addressEditText.setText(address);



                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel

//        Activity productItems = getActivity();
//        if (productItems != null) {
//            startActivity(new Intent(productItems, settingsFragment.class)); // if needed
//            productItems.finish();
//        }
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }

    //    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//    }
}