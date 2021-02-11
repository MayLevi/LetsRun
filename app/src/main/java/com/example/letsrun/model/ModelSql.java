package com.example.letsrun.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ModelSql {

    public LiveData<List<User>> getAllFriends() {
       return AppLocalDB.db.UserDao().getAll();

    }

    public interface getAllFriendsListener {
        void onComplete(List<User> list);
    }

    public interface addFriendListener {
        void onComplete();
    }
    @SuppressLint("StaticFieldLeak")
    public void addFriend(final User user, addFriendListener listener){
        class MyAsyncTask extends AsyncTask{
            @Override
            protected Object doInBackground(Object[] objects) {
                AppLocalDB.db.UserDao().insertAll(user);
                return null;
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if(listener != null){
                    listener.onComplete();
                }
            }
        }
        MyAsyncTask task= new MyAsyncTask();
        task.execute();
    }

}
