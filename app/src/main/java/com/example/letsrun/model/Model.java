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
        class MyAsynchtask extends AsyncTask {
            List<User> data;
            @Override
            protected Object doInBackground(Object[] objects) {
                data = AppLocalDB.db.UserDao().getAll();

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                listener.onComplete(data);
            }
        }
        MyAsynchtask task= new MyAsynchtask();
        task.execute();

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
