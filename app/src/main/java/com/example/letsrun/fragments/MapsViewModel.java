package com.example.letsrun.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.letsrun.model.Model;
import com.example.letsrun.model.Post;
import com.example.letsrun.model.User;

public class MapsViewModel extends ViewModel {

    MutableLiveData<Post> post;


    public MutableLiveData<Post> getPost(String id) {

        return Model.instance.getPost(id);
        }


}
