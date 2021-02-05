package com.example.letsrun;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letsrun.model.Model;
import com.example.letsrun.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class profile extends Fragment {
    View view;
    Button runsBtn;
    Button editBtn;
    Button editImgBtn;
    Button saveChange;
    ImageView profilePic;
    TextView userName;
    String userPass;
    User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName=view.findViewById(R.id.profile_userName);
        String name=profileArgs.fromBundle(getArguments()).getUserName().toString();
        userName.setText(name);
        saveChange=view.findViewById(R.id.profile_saveBtn);
        editBtn=view.findViewById(R.id.profile_EditBtn);
        editImgBtn=view.findViewById(R.id.profile_editImgBtn);
        profilePic = view.findViewById(R.id.profile_image);
        runsBtn = view.findViewById(R.id.profile_MyRunBtn);
        userPass=profileArgs.fromBundle(getArguments()).getUserPass();
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                edit();
            }
        });
        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                save();
            }
        });
        runsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavGraphDirections.ActionGlobalMyRunningTracks action = singingDirections.actionGlobalMyRunningTracks(name, userPass);
                Navigation.findNavController(v).navigate(action);
            }
        });
        editImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editImage();
            }
        });

        return view;
    }

//    private void edit() {
//        user.setName();
//    }
//
//    private void save() {
//
//    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public void editImage() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }

    }



}