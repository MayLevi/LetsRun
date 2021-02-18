package com.example.letsrun;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.letsrun.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Attr;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    ImageButton imageButton;
    EditText email_edittext,firstname_edittext,lastname_edittext,age_edittext,password_edittext,password_edittext2;
    TextView textView_login,textview_logout;
    LinearLayout linearLayout , linearLayout2;
    Button btn_edit_update,btn_login;
    CardView cardView,cardView_profile;

    private FirebaseFirestore db;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        View view =inflater.inflate(R.layout.fragment_account, container, false);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Users");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        email_edittext = view.findViewById(R.id.email_edittext);
        firstname_edittext =view.findViewById(R.id.firstname_edittext);
        lastname_edittext = view.findViewById(R.id.lastname_edittext);
        age_edittext = view.findViewById(R.id.age_edittext);
        password_edittext = view.findViewById(R.id.password_edittext);
        password_edittext2 = view.findViewById(R.id.password_edittext2);
        btn_edit_update = view.findViewById(R.id.btn_edit_update);
        cardView = view.findViewById(R.id.cardView);
        btn_login = view.findViewById(R.id.btn_login);
        linearLayout = view.findViewById(R.id.linearLayout);
        linearLayout2 = view.findViewById(R.id.linearLayout2);
        textView_login = view.findViewById(R.id.textView_login);
        textview_logout = view.findViewById(R.id.textview_logout);
        cardView_profile = view.findViewById(R.id.cardView_profile);

        if(currentUser!=null){

            db = FirebaseFirestore.getInstance();

            final DocumentReference docRef = db.collection("users").document(currentUser.getUid());
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w("TAG", "Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d("TAG", "Current data: " + snapshot.getData());
                        //User user;
                        Map<String,Object> b =snapshot.getData();

                        lastname_edittext.setText((String) b.get("lastName"));
                        email_edittext.setText((String) b.get("email"));
                        firstname_edittext.setText((String) b.get("firstName"));
                        age_edittext.setText((String) b.get("age"));

                    } else {
                        Log.d("TAG", "Current data: null");
                    }
                }
            });

        }else{
            linearLayout.setVisibility(View.GONE);
            linearLayout2.setVisibility(View.VISIBLE);
            cardView_profile.setVisibility(View.INVISIBLE);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_global_menu_login);
            }
        });


        btn_edit_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_edit_update.getText().equals("EDIT"))
                {
                    btn_edit_update.setText("UPDATE");
                    firstname_edittext.setEnabled(true);
                    lastname_edittext.setEnabled(true);
                    age_edittext.setEnabled(true);
                }else{
                    btn_edit_update.setText("EDIT");

                    User user = new User(currentUser.getUid(),firstname_edittext.getText().toString(),lastname_edittext.getText().toString(),email_edittext.getText().toString(),age_edittext.getText().toString());

                    db = FirebaseFirestore.getInstance();
                    db.collection("users").document(currentUser.getUid()).set(user);

                    firstname_edittext.setEnabled(false);
                    lastname_edittext.setEnabled(false);
                    age_edittext.setEnabled(false);

                }

            }
        });

        textview_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(getView()).navigate(R.id.action_global_menu_account);

            }
        });

        return view;
    }
}