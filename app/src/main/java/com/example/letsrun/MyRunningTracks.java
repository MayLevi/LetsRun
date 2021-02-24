package com.example.letsrun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class MyRunningTracks extends Fragment {
    View view;
    String userPass;
    String userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_my_running_tracks, container, false);
        userName=profileArgs.fromBundle(getArguments()).getUserName();
        userPass=profileArgs.fromBundle(getArguments()).getUserPass();


        return view;
    }
}