package com.example.letsrun;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.letsrun.model.User;
import com.google.android.material.textfield.TextInputEditText;


public class singing extends Fragment {
    View view;
    Button sign_inBtn;
    Button sign_upBtn;
    TextInputEditText sign_upName;
    EditText sign_upPass;
    TextInputEditText sign_inName;
    EditText sign_inPass;
    String userPass;
    String userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_singing, container, false);
        sign_upName = view.findViewById(R.id.signing_upWriteName);
        sign_upPass=view.findViewById(R.id.signing_upWritePass);
        sign_upBtn=view.findViewById(R.id.signing_upButton);
        sign_inName = view.findViewById(R.id.signing_inWriteName);
        sign_inPass =view.findViewById(R.id.signing_inWritePass);
        sign_inBtn=view.findViewById(R.id.signing_inButton);

        sign_inBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userPass=(String) sign_inPass.getText().toString();
                userName=(String) sign_inName.getText().toString();
                NavGraphDirections.ActionGlobalProfile2 action = singingDirections.actionGlobalProfile2(userName, userPass);
                Navigation.findNavController(v).navigate(action);
            }
        });
        sign_upBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 userPass=(String) sign_upPass.getText().toString();
                 userName=(String) sign_upName.getText().toString();
                saveNewUser();
                NavGraphDirections.ActionGlobalProfile2 action = singingDirections.actionGlobalProfile2(userName, userPass);
                Navigation.findNavController(v).navigate(action);
            }
        });



        return view;
    }

    public void saveNewUser() {
        User user = new User(userPass,userName,"","","");

    }
}