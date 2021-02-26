package com.example.letsrun.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.letsrun.R;
import com.example.letsrun.model.Model;

public class SignUpFragment extends Fragment {


    private EditText email_edittext,password_edittext,password_edittext2,firstName,lastName,age;
    private Button btn_signUp;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        email_edittext = view.findViewById(R.id.email_edittext);
        password_edittext = view.findViewById(R.id.password_edittext);
        password_edittext2 = view.findViewById(R.id.password_edittext2);
        firstName = view.findViewById(R.id.firstname_edittext);
        lastName = view.findViewById(R.id.lastname_edittext);
        age = view.findViewById(R.id.age_edittext);
        btn_signUp = view.findViewById(R.id.btn_signUp);


        progressDialog = new ProgressDialog(this.getContext());

        btn_signUp.setOnClickListener(view1 ->
                Register());

        return view;
    }

    private void Register(){
        String email = email_edittext.getText().toString();
        String password = password_edittext.getText().toString();
        String password2 = password_edittext2.getText().toString();


        if(TextUtils.isEmpty(email)){
            email_edittext.setError("Please enter your email");
            return;
        }
        if(TextUtils.isEmpty(password)){
            password_edittext.setError("Please enter your password");
            return;
        }
        if(TextUtils.isEmpty(password2)){
            password_edittext2.setError("Please confirm your password");
            return;
        }
        if(!password.equals(password2)){
            password_edittext2.setError("Different password");
            return;
        }
        if(password.length()<4){
            password_edittext.setError("Password length should be greater than 4");
            return;
        }
        if(!isValidEmail(email)){
            email_edittext.setError("Invalid email");
            return;
        }

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(true);

        Model.instance.signUp(email,password,firstName.getText().toString(),
                lastName.getText().toString(),age.getText().toString(),getView());
        progressDialog.dismiss();

    }



    private boolean isValidEmail(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}