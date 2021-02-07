package com.example.letsrun.model;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

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
    public interface getUserListener {
        void onComplete(User user);
    }

    public void getUser(final String pass,String name, getUserListener listener){
        modelFirebase.getUser(pass,name,listener);
    }
    public interface addUserListener {
        void onComplete();
    }
    public void addUser(User user, addUserListener listener){
        modelFirebase.addUser(user,listener);
    }
    public interface uploadImageListener{
        public void onComplete(String url);
    }
    public void uploadImage(Bitmap imageBmp, String name, final uploadImageListener listener) {
      modelFirebase.uploadImage(imageBmp,name,listener);
    }

}
