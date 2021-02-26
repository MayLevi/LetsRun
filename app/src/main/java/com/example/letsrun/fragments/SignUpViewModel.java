package com.example.letsrun.fragments;

import android.widget.EditText;

import androidx.lifecycle.ViewModel;

import com.example.letsrun.model.User;

public class SignUpViewModel extends ViewModel {
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
