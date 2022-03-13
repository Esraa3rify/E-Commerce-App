package com.example.ecommerce.settings;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ecommerce.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class settingsFragment extends Fragment {

    private CircleImageView profileImageView;
    private EditText fullNameEditText,userPhoneEditText, addressEditText;
    private TextView profileChangeTextBtn, closeTextBtn,saveTextBtn;

    private SettingsViewModel mViewModel;

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
        profileChangeTextBtn = view. findViewById(R.id.profile_image_change_btn);
        closeTextBtn = view. findViewById(R.id.close_settings_btn);
        saveTextBtn = view. findViewById(R.id.update_account_settings_btn);
        return view;
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