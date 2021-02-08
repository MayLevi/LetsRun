package com.example.letsrun;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private EditText email_edittext,password_edittext,password_edittext2,firstName,lastName,age;
    private Button btn_signUp;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        email_edittext = view.findViewById(R.id.email_edittext);
        password_edittext = view.findViewById(R.id.password_edittext);
        password_edittext2 = view.findViewById(R.id.password_edittext2);
        firstName = view.findViewById(R.id.firstname_edittext);
        lastName = view.findViewById(R.id.lastname_edittext);
        age = view.findViewById(R.id.age_edittext);
        btn_signUp = view.findViewById(R.id.btn_signUp);


        progressDialog = new ProgressDialog(this.getContext());
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });

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

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    addToFirebase();
                    Toast.makeText(getActivity(),"Successfully registered",Toast.LENGTH_LONG).show();
                    Navigation.findNavController(getView()).navigate(R.id.action_global_menu_account);



                }else{
                    Toast.makeText(getContext(),"Registration failed",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            private void addToFirebase() {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());
                database.child("id").setValue(firebaseAuth.getUid());
                database.child("email").setValue(email);
                database.child("first name").setValue(firstName.getText().toString());
                database.child("last name").setValue(lastName.getText().toString());
                database.child("age").setValue(age.getText().toString());

            }


        });
    }



    private boolean isValidEmail(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}