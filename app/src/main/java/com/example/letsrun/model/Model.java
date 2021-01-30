package com.example.letsrun.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.List;

public class Model {
    public final static Model instance=new Model();
    private Model(){

    }
    public interface getAllFriendesListener {
        void onComplete(List<User> list);
    }
    public void getAllFriendes(getAllFriendesListener listener){

    }
    public interface addFriendListener {
        void onComplete();
    }
    @SuppressLint("StaticFieldLeak")
    public void addFriend(final User user, addFriendListener listener){
    }

}
