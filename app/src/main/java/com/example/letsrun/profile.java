package com.example.letsrun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letsrun.model.Model;
import com.example.letsrun.model.User;


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
        User user1=new User(userPass,userName.toString(),"","","");
        BitmapDrawable drawable=(BitmapDrawable) profilePic.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        Model.instance.uploadImage(bitmap, user1.getUserId(), new Model.uploadImageListener() {
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

//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        try {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        } catch (ActivityNotFoundException e) {
//            // display error state to the user
//        }
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

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