package com.example.letsrun.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.example.letsrun.LogInFragment;
import com.example.letsrun.MainActivity;
import com.example.letsrun.MyApplication;
import com.example.letsrun.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;

public class Model {

    public final static Model instance=new Model();
    ModelFirebase modelFirebase=new ModelFirebase();
    ModelSql modelSql=new ModelSql();

    private Model(){}

    public void logOut() {
        ModelFirebase.logOut();
    }
        public interface logInListener {
        void onComplete();
    }
    public void logIn(String email, String password, View view){
        ModelFirebase.logIn(email, password,view);

    }

    public void signUp(String email, String password,String firstName,String lastName,String age,View view){
        ModelFirebase.signUp(email, password,firstName,lastName,age,view);

    }
    public void postByUser(User user, String kilometers,String text,String location){
        ModelFirebase.postByUser(user,kilometers,text,location);
    }

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

    MutableLiveData<User> userLiveData = new MutableLiveData<User>();
    public MutableLiveData<User> getCurrentUser(){
        modelFirebase.getCurrentUser(new getUserListener() {
            @Override
            public void onComplete(User user) {
                userLiveData.setValue(user);
            }
        });
        return userLiveData;
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
