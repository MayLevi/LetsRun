package com.example.letsrun;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.letsrun.model.Model;
import com.example.letsrun.model.User;

public class LogInFragment extends Fragment {
    private EditText email_edittext,password_edittext;
    private Button btn_signUp,btn_signIn;
    private User user;
    private ProgressDialog progressDialog;
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public LogInFragment() {
//        // Required empty public constructor
//    }
//
//    // TODO: Rename and change types and number of parameters
//    public static LogInFragment newInstance(String param1, String param2) {
//        LogInFragment fragment = new LogInFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        email_edittext = view.findViewById(R.id.email_edittext);
        password_edittext = view.findViewById(R.id.password_edittext);
        btn_signUp = view.findViewById(R.id.btn_signUp);


        progressDialog = new ProgressDialog(this.getContext());
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_global_menu_signup);

            }
        });



        btn_signUp = view.findViewById(R.id.btn_signUp);
        btn_signIn = view.findViewById(R.id.btn_signIn);
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();

            }
        });
        return view;
    }

    private void Login(){
        String email = email_edittext.getText().toString();
        String password = password_edittext.getText().toString();

        if(TextUtils.isEmpty(email)){
            email_edittext.setError("Please enter your email");
            return;
        }
        if(TextUtils.isEmpty(password)){
            password_edittext.setError("Please enter your password");
            return;
        }



        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(true);
        Model.instance.logIn(email, password,getView());
        progressDialog.dismiss();

    }


}
