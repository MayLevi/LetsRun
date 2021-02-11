package com.example.letsrun;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.media.MediaBrowserServiceCompat;
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
    User user2;


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
//        Model.instance.getUser(userPass,name, new Model.getUserListener() {
//            @Override
//            public void onComplete(User user) {
//                user2=user;
//                if (user.getImageUrl() != null){
//                    Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.runner).into(profilePic);
//                }
//            }
//        });
        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
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

    private void save() {
        User user1=new User(userPass,userName.toString());
        BitmapDrawable drawable=(BitmapDrawable) profilePic.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        Model.instance.uploadImage(bitmap, user1.getUserName(), new Model.uploadImageListener() {
            @Override
            public void onComplete(String url) {
                if (url == null){
                }else {
                    user1.setImageUrl(url);
                    Model.instance.addUser(user1, new Model.addUserListener() {
                        @Override
                        public void onComplete() {
                            Navigation.findNavController(saveChange).popBackStack();
                        }
                    });
                }
            }
        });
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profilePic.setImageBitmap(imageBitmap);
        }
    }



}