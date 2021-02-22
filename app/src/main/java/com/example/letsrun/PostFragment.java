package com.example.letsrun;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letsrun.model.Model;
import com.example.letsrun.model.User;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.MapView;
import static android.app.Activity.RESULT_OK;


public class PostFragment extends Fragment {

    Button btn_location,btn_post;
    TextView textview_results;
    MapView mapView;
    ImageView listrow_ImageView;
    int PLACE_PICKER_REQUEST = 1;

    User currentUser;


    EditText edittext_info,edittext_kilometers,edittext_Date,edittext_location,edittext_cotact;



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



        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance.getCurrentUserId(new Model.getCurrentUserIdListener() {
                    @Override
                    public void onComplete(String id) {
                        Model.instance.getUser(id, new Model.getUserListener() {
                            @Override
                            public void onComplete(User user) {
                                Model.instance.postByUser(user,edittext_kilometers.getText().toString()
                                        ,edittext_info.getText().toString(),
                                        edittext_location.getText().toString());
                                Navigation.findNavController(getView()).navigate(R.id.action_global_menu_wall);

                            }
                        });
                    }
                });
            }
        });


        return view;
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