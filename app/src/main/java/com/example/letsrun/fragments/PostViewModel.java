package com.example.letsrun.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.letsrun.model.Model;
import com.example.letsrun.model.User;

public class PostViewModel extends ViewModel {
    MutableLiveData<User> user = Model.instance.getCurrentUser();

    public MutableLiveData<User> getUser() {
        return user;
    }

}
