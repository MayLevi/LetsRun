package com.example.letsrun.model;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class ModelFirebase {
    public interface getAllFriendsListener {
        void onComplete(List<User> list);
    }
    public void getAllFriends(long lastUpdated,getAllFriendsListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(lastUpdated,0);
        db.collection("users").whereGreaterThanOrEqualTo("lastUpdated",ts).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<User> data = new LinkedList<User>();
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult()) {
                        User us = new User(null,null,null,null,null);
                        us.fromMap(doc.getData());
                        //Student st = doc.toObject(Student.class);
                        data.add(us);
                    }
                }
                listener.onComplete(data);
            }
        });
    }



    public void addFriend(User user, Model.addFriendListener listener) {
    }

    public void getUser(String id,Model.getUserListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = null;
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        user = task.getResult().toObject(User.class);
                    }
                    else{
                        user=new User(id,null,null,null,null);
                        addUser(user, new Model.addUserListener() {
                            @Override
                            public void onComplete() { }
                        });
                    }
                }
                listener.onComplete(user);
            }
        });
    }

    public void addUser(User user, Model.addUserListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUserId())
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","student added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","fail adding student");
                listener.onComplete();
            }
        });
    }

    public void uploadImage(Bitmap imageBmp, String name, final Model.uploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }

}
