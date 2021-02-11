package com.example.letsrun;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.letsrun.model.Model;
import com.example.letsrun.model.User;

import java.util.List;

public class FriendsListViewModel extends ViewModel {
    private LiveData<List<User>> frList;

    public FriendsListViewModel(){
        frList = Model.instance.getAllFriends();
    }
    LiveData<List<User>> getList(){
        return frList;
    }
}
