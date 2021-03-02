package com.example.letsrun.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.letsrun.model.Model;
import com.example.letsrun.model.User;

public class AccountViewModel extends ViewModel {
    MutableLiveData<User> user = Model.instance.getCurrentUser();
    MutableLiveData<String> user_id = Model.instance.getCurrentUserId();

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<String> getUser_id() {
        return user_id;
    }
}
