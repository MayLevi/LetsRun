package com.example.letsrun.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.letsrun.fragments.MapsFragmentArgs;
import com.example.letsrun.R;
import com.example.letsrun.model.Post;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class MapsFragment extends Fragment {

    String postId;
    ImageView img_profile;
    TextView tv_name,tv_km,tv_email,tv_info;
    Post thisPost;
    Double lat,lon;
    MapsViewModel viewModel;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng location = new LatLng(lat, lon);
            googleMap.addMarker(new MarkerOptions().position(location).title("Marker"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//            float zoomLevel = 6.0f; //This goes up to 21
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));
        }
    };



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_maps, container, false);
        viewModel = new ViewModelProvider(this).get(MapsViewModel.class);

        if(getArguments()!=null){
            MapsFragmentArgs args = MapsFragmentArgs.fromBundle(getArguments());
            postId = args.getPostId();
        }

        img_profile = view.findViewById(R.id.img_profile);
        tv_name = view.findViewById(R.id.tv_name);
        tv_km = view.findViewById(R.id.tv_km);
        tv_email = view.findViewById(R.id.tv_email);
        tv_info = view.findViewById(R.id.tv_info);


        viewModel.getPost(postId).observe(getViewLifecycleOwner(), new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                thisPost = post;
                tv_name.setText(thisPost.getFirstName() + " " + thisPost.getLastName());
                tv_km.setText(thisPost.getKilometers() + " ");
                tv_info.setText(thisPost.getText());
                tv_email.setText(thisPost.getEmail());

                String url = thisPost.getImg();
                if(url!=null){
                    Picasso.get().load(url).placeholder(R.drawable.avatar).into(img_profile);
                }

                lat = Double.valueOf(thisPost.getLat());
                lon = Double.valueOf(thisPost.getLon());

                SupportMapFragment mapFragment =
                        (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(callback);
                }

            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}