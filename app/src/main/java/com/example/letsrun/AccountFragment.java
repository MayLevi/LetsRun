package com.example.letsrun;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.letsrun.model.Model;
import com.example.letsrun.model.User;
import java.util.Map;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import javax.annotation.Nullable;



public class AccountFragment extends Fragment {

    ImageView imagView_profile;
    EditText email_edittext,firstname_edittext,lastname_edittext,age_edittext,password_edittext,password_edittext2;
    TextView textView_login,textview_logout;
    LinearLayout linearLayout , linearLayout2;
    Button btn_edit_update,btn_login,btn_profileAvatar,btn_profileAvatarSave;
    CardView cardView,cardView_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_account, container, false);

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

//        MutableLiveData<User> userLiveData = Model.instance.getCurrentUser();
//        userLiveData.observe(getViewLifecycleOwner(), new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//
//            }
//        });


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
//                            Model.instance.getCurrentUser(new Model.getUserListener() {
//                                @Override
//                                public void onComplete(User user) {
//                                    user.setImageUrl(url);
//                                    Model.instance.addUser(user, new Model.addUserListener() {
//                                        @Override
//                                        public void onComplete() {
//                                        }
//                                    });
//                                }
//                            });


                        }
                    }
                });
                ///////
            }
        });

        Model.instance.getCurrentUserId(new Model.getCurrentUserIdListener() {
            @Override
            public void onComplete(String id) {
                if(id!=null){
                    Model.instance.getUser(id, new Model.getUserListener() {
                        @Override
                        public void onComplete(User user) {
                            lastname_edittext.setText(user.getLastName());
                            email_edittext.setText(user.getEmail());
                            firstname_edittext.setText(user.getFirstName());
                            age_edittext.setText(user.getAge());
                        }
                    });
                }else{
                    linearLayout.setVisibility(View.GONE);
                    linearLayout2.setVisibility(View.VISIBLE);
                    cardView_profile.setVisibility(View.INVISIBLE);
                }
            }
        });

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

                    Model.instance.getCurrentUserId(new Model.getCurrentUserIdListener() {
                        @Override
                        public void onComplete(String id) {

                                    User u = new User(id,firstname_edittext.getText().toString(),lastname_edittext.getText().toString(),email_edittext.getText().toString(),age_edittext.getText().toString());

                                    Model.instance.addUser(u, new Model.addUserListener() {
                                        @Override
                                        public void onComplete() {
                                            Log.d("TAG1","BINGO!");
                                        }
                                    });
                        }
                    });


                    firstname_edittext.setEnabled(false);
                    lastname_edittext.setEnabled(false);
                    age_edittext.setEnabled(false);

                }

            }
        });

        textview_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance.logOut();
                Navigation.findNavController(getView()).navigate(R.id.action_global_menu_account);

            }
        });

        return view;
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

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