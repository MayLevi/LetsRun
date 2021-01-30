package com.example.letsrun.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.util.List;

public class Model {
    public final static Model instance=new Model();
    ModelFirebase modelFirebase=new ModelFirebase();
    ModelSql modelSql=new ModelSql();
    private Model(){

    }
    public interface getAllFriendesListener {
        void onComplete(List<User> list);
    }
    public void getAllFriendes(getAllFriendesListener listener){
        modelFirebase.getAllFriends(listener);

    }
    public interface addFriendListener {
        void onComplete();
    }
    @SuppressLint("StaticFieldLeak")
    public void addFriend(final User user, addFriendListener listener){
        modelFirebase.addFriend(user,listener);
    }

}
