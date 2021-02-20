package com.example.letsrun.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.example.letsrun.MyApplication;

import java.util.List;

public class Model {

    public final static Model instance=new Model();
    ModelFirebase modelFirebase=new ModelFirebase();
    ModelSql modelSql=new ModelSql();

    private Model(){}

    public interface getAllFriendsListener {
        void onComplete();
    }

    LiveData<List<User>> friendsList;

    public LiveData<List<User>> getAllFriends() {
        if (friendsList == null){
            friendsList = modelSql.getAllFriends();
            refreshAllFriends(null);
        }
        return friendsList;
    }

    private void refreshAllFriends(final getAllFriendsListener listener) {
        final SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        long lastUpdated = sp.getLong("lastUpdated",0);
        modelFirebase.getAllFriends(lastUpdated, new ModelFirebase.getAllFriendsListener() {
            @Override
            public void onComplete(List<User> result) {
                long lastU = 0;
                for (User s: result) {
                    modelSql.addFriend(s,null);
                    if (s.getLastUpdated()>lastU){
                        lastU = s.getLastUpdated();
                    }
                }
                sp.edit().putLong("lastUpdated", lastU).commit();
                if(listener != null){
                    listener.onComplete();
                }
            }
        });
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

    public void getUser(final String id, getUserListener listener){
        modelFirebase.getUser(id,listener);
    }

    public interface getCurrentUserIdListener{
        void onComplete(String id);
    }
    public void getCurrentUserId(getCurrentUserIdListener listener){
        modelFirebase.getCurrentUserId(listener);
    }
    public void getCurrentUser( getUserListener listener){
        modelFirebase.getCurrentUser(listener);
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
