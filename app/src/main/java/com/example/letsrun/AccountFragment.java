package com.example.letsrun;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
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

import com.example.letsrun.model.Model;
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

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class


AccountFragment extends Fragment {

    ImageView imagView_profile;
    EditText email_edittext,firstname_edittext,lastname_edittext,age_edittext,password_edittext,password_edittext2;
    TextView textView_login,textview_logout;
    LinearLayout linearLayout , linearLayout2;
    Button btn_edit_update,btn_login,btn_profileAvatar,btn_profileAvatarSave;
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
        btn_profileAvatar = view.findViewById(R.id.btn_profileAvatar);
        imagView_profile = view.findViewById(R.id.imagView_profile);
        btn_profileAvatarSave = view.findViewById(R.id.btn_profileAvatarSave);

        btn_profileAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_profileAvatar.setVisibility(view.GONE);
                btn_profileAvatarSave.setVisibility(view.VISIBLE);
                editImage();


            }
        });

        btn_profileAvatarSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_profileAvatarSave.setVisibility(view.GONE);
                btn_profileAvatar.setVisibility(view.VISIBLE);
                ///////
                BitmapDrawable drawable=(BitmapDrawable) imagView_profile.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Model.instance.uploadImage(bitmap, "myid", new Model.uploadImageListener() {
                    @Override
                    public void onComplete(String url) {
                        if(url==null){

                        }else{
                            Model.instance.getCurrentUser(new Model.getUserListener() {
                                @Override
                                public void onComplete(User user) {
                                    user.setImageUrl(url);
                                    Model.instance.addUser(user, new Model.addUserListener() {
                                        @Override
                                        public void onComplete() {
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
                ///////
            }
        });

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
    static final int REQUEST_IMAGE_CAPTURE = 1;

//    private void editImage() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if(takePictureIntent.resolveActivity(getActivity().getPackageManager())!=null){
//            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
//        }
//    }

    private void editImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imagView_profile.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                imagView_profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }
}