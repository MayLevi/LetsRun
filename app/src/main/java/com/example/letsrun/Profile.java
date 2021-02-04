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

public class Profile extends Fragment {
   View view;
   Button runsBtn;
   Button editBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile, container, false);
        runsBtn=view.findViewById(R.id.profile_MyRunBtn);
        runsBtn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Navigation.findNavController(v).navigate(R.id.action_profile_to_myRunningTracks);
                }
        });

        return view;
    }

}