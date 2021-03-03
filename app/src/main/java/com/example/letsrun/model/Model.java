package com.example.letsrun.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.letsrun.MyApplication;

import java.util.List;

public class Model {

    public final static Model instance=new Model();
    ModelFirebase modelFirebase=new ModelFirebase();
    ModelSql modelSql=new ModelSql();

    private Model(){}

    MutableLiveData<Post> postLiveData = new MutableLiveData<Post>();
    public interface getPostListener {
        void onComplete(Post post);
    }
    public MutableLiveData<Post> getPost(String id){
        modelFirebase.getPost(id,new getPostListener() {
            @Override
            public void onComplete(Post post) {
                postLiveData.setValue(post);
            }
        });
        return postLiveData;
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

    LiveData<List<User>> friendsList;

    public LiveData<List<User>> getAllFriends() {
        if (friendsList == null){
            friendsList = modelSql.getAllFriends();
            refreshAllFriends(null);
        }
        return friendsList;
    }

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
    public void postByUser(User user, String kilometers,String text,String email,String lat,String lon){
        ModelFirebase.postByUser(user,kilometers,text,email,lat,lon);
    }

    public interface getAllFriendsListener {
        void onComplete();
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
    MutableLiveData<User> getUser = new MutableLiveData<User>();
    public MutableLiveData<User> getUser(final String id){
        modelFirebase.getUser(id, new getUserListener() {
            @Override
            public void onComplete(User user) {
                getUser.setValue(user);
            }
        });
        return getUser;
    }

    public interface getCurrentUserIdListener{
        void onComplete(String id);
    }
//    public void getCurrentUserId(getCurrentUserIdListener listener){
//        modelFirebase.getCurrentUserId(listener);
//    }

    MutableLiveData<String> userIdLiveData = new MutableLiveData<String>();
    public MutableLiveData<String> getCurrentUserId(){
        modelFirebase.getCurrentUserId(new getCurrentUserIdListener() {
            @Override
            public void onComplete(String id) {

                userIdLiveData.setValue(id);
            }
        });
        return userIdLiveData;
    }


    public interface addUserListener {
        void onComplete();
    }
    public void addUser(User user, addUserListener listener){
        modelFirebase.addUser(user,listener);
    }


    ///
    public interface uploadImageListener{
        void onComplete(String url);}
    public void uploadImage(Bitmap imageBmp, String name, final uploadImageListener listener) {
      modelFirebase.uploadImage(imageBmp,name,listener);
    }

    public void likePost(Post post){
        ModelFirebase.likePost(post);
    }

    public void deletePost(Post post){
        ModelFirebase.deletePost(post);
    }

    public void wallFragment(LifecycleOwner lifecycleOwner, View view){
        ModelFirebase mfb = new ModelFirebase();
        mfb.wallFragment(lifecycleOwner,view);
    }


}
