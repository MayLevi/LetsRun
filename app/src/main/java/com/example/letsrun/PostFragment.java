package com.example.letsrun;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letsrun.model.Model;
import com.example.letsrun.model.Post;
import com.example.letsrun.model.User;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment {

    Button btn_location,btn_post;
    TextView textview_results;
    MapView mapView;
    ImageView listrow_ImageView;
    int PLACE_PICKER_REQUEST = 1;
    private FirebaseFirestore db;
//    private FirebaseAuth firebaseAuth;
    User currentUser;


    EditText edittext_info,edittext_kilometers,edittext_Date,edittext_location,edittext_cotact;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
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

    User user = new User();
    User u = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        ///
        textview_results = view.findViewById(R.id.textview_results);
        mapView = view.findViewById(R.id.mapView);
        btn_location =view.findViewById(R.id.btn_location);
        btn_post = view.findViewById(R.id.btn_post);
        edittext_location = view.findViewById(R.id.edittext_location);
        edittext_cotact = view.findViewById(R.id.edittext_cotact);
        edittext_Date = view.findViewById(R.id.edittext_Date);
        edittext_info = view.findViewById(R.id.edittext_info);
        edittext_kilometers = view.findViewById(R.id.edittext_kilometers);
        listrow_ImageView = view.findViewById(R.id.listrow_ImageView);
        ///

//        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = new User();
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()),PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

//        Model.instance.getCurrentUser(new Model.getUserListener() {
//            @Override
//            public void onComplete(User user) {
//                currentUser = user;
//                postClick(user);
//            }
//
//        });

//        Model.instance.getUser("7JM8YunqeHdEIALhpxMbgnYVez43", new Model.getUserListener() {
//            @Override
//            public void onComplete(User user) {
//
//
//            }
//        });


        return view;
    }

    private void postClick(User user) {
        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = FirebaseFirestore.getInstance();
                Post post = new Post(currentUser.getUserId(), currentUser.getFirstName(),
                        currentUser.getLastName(),currentUser.getAge(),edittext_kilometers.getText().toString() + "km"
                        ,edittext_info.getText().toString(),edittext_location.getText().toString(),currentUser.getImageUrl());
                db.collection("posts").add(post);

                Navigation.findNavController(getView()).navigate(R.id.action_global_menu_wall);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PLACE_PICKER_REQUEST){
            if(resultCode== RESULT_OK){
                Place place = PlacePicker.getPlace(data,this.getContext());
                mapView.getDisplay();
                StringBuilder stringBuilder = new StringBuilder();
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                stringBuilder.append("LATITUDE :");
                stringBuilder.append(latitude);
                stringBuilder.append("\n");
                stringBuilder.append("LONGITUDE :");
                stringBuilder.append(longitude);
                textview_results.setText(stringBuilder.toString());
            }
        }
    }
}