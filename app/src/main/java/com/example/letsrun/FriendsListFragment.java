package com.example.letsrun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.letsrun.model.Model;
import com.example.letsrun.model.User;

import java.util.LinkedList;
import java.util.List;

public class FriendsListFragment extends Fragment {
    View view;
    Button btn;
    List<User> usersList = new LinkedList<User>();
    MyAdapter adapter;
    String userPass;
    String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_friends_list, container, false);
        userName=profileArgs.fromBundle(getArguments()).getUserName();
        userPass=profileArgs.fromBundle(getArguments()).getUserPass();
        ListView list =view.findViewById(R.id.FriendsList);
        NavGraphDirections.ActionGlobalProfile2 action = singingDirections.actionGlobalProfile2(userName.toString(), userPass);
        Navigation.findNavController(view).navigate(action);
        NavGraphDirections.ActionGlobalMyRunningTracks action2 = singingDirections.actionGlobalMyRunningTracks(userName.toString(), userPass);
        Navigation.findNavController(view).navigate(action);
        return view;
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return usersList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.list_row, null);
            }

//            TextView tv = view.findViewById(R.id.list_rowText);
//            User user = usersList.get(i);
//            tv.setText(user.getId());
            return view;
        }
    }
//    void reloadData(){
//        Model.instance.getAllFriendes(new Model.getAllFriendesListener() {
//            @Override
//            public void onComplete(List<User> data) {
//                usersList=data;
//            }
//        });
//    }
}