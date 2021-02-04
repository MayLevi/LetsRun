package com.example.letsrun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
        profilePic = view.findViewById(R.id.profile_image);
        runsBtn = view.findViewById(R.id.profile_MyRunBtn);
        userPass=profileArgs.fromBundle(getArguments()).getUserPass();
        Model.instance.getUser(userPass,name, new Model.getUserListener() {
            @Override
            public void onComplete(User usr) {
                user = usr;
//                if (usr.getImageUrl() != null){
//                    Picasso.get().load(usr.getImageUrl()).placeholder(R.drawable.runner).into(profilePic);
//                }
            }
        });
        runsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavGraphDirections.ActionGlobalMyRunningTracks action = singingDirections.actionGlobalMyRunningTracks(name, userPass);
                Navigation.findNavController(v).navigate(action);
            }
        });

        return view;
    }


    }