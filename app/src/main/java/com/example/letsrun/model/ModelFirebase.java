package com.example.letsrun.model;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.Navigation;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letsrun.fragments.MapsFragmentDirections;
import com.example.letsrun.MyApplication;
import com.example.letsrun.NavGraphDirections;
import com.example.letsrun.R;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class ModelFirebase implements FirestoreAdapter.OnListItemClick{


    public static void signUp(String email, String password,String firstName,String lastName,String age,View view){
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(MyApplication.context);

                if(task.isSuccessful()){
                    addToFirebase(firstName,lastName,email,age);
                    Toast.makeText(MyApplication.context,"Successfully registered",Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.action_global_menu_account);

                }else{
                    Toast.makeText(MyApplication.context,"Registration failed",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            private void addToFirebase(String firstName,String lastName,String email,String age) {

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                User user = new User(currentUser.getUid(),firstName,lastName,email,age);
                FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                db.collection("users").document(currentUser.getUid()).set(user);

            }


        });

    }
    public static void logIn(String email, String password, View view){
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                ProgressDialog progressDialog = new ProgressDialog(MyApplication.context);
                if(task.isSuccessful()){
                    Toast.makeText(MyApplication.context,"Successfully logged in",Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigate(R.id.menu_wall);


                }else{
                    Toast.makeText(MyApplication.context,"Log in failed",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });

    }


    public void getCurrentUser(Model.getUserListener listener) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getUid()==null){
            User user = new User();
            user.setUserId("none");
            listener.onComplete(user);
            return;
        }
        getUser(firebaseAuth.getUid(),listener);
    }

    public void getCurrentUserId(Model.getCurrentUserIdListener listener) {
        listener.onComplete(FirebaseAuth.getInstance().getUid());
    }

    public void getPost(String id,Model.getPostListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Post post = new Post();

        DocumentReference docRef = db.collection("posts").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Post post = documentSnapshot.toObject(Post.class);
                listener.onComplete(post);

            }
        });
    }


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
        User user = new User();

        DocumentReference docRef = db.collection("users").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
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

    public static void postByUser(User user, String kilometers, String text,String email,String lat, String lon){
        FirebaseFirestore db;

        db = FirebaseFirestore.getInstance();
        Post post = new Post(user.getUserId(), user.getFirstName(),
                user.getLastName(),user.getAge(),kilometers + "km"
                ,text,user.getImageUrl(),email,lat,lon);
        db.collection("posts").add(post);
    }

    public static void logOut(){
        FirebaseAuth.getInstance().signOut();

    }

    public static void likePost(Post post){
        FirebaseFirestore db;

        Log.d("TAG","old likes " + post.getLikes());
        String likes = post.getLikes();
        if (likes == null)
        {
            likes = "0";
        }
        likes = Integer.toString(Integer.parseInt(likes) + 1);
        post.setLikes(likes);

        Log.d("TAG","new likes " + post.getLikes());
        db = FirebaseFirestore.getInstance();
        db.collection("posts").document(post.getPostId()).set(post);

    }

    public static void deletePost(Post post){
        if (!(FirebaseAuth.getInstance().getUid().equals(post.getUserId()))) {
            Log.d("TAG", "Delete post DENIED! User uid: " + FirebaseAuth.getInstance().getUid() + " Post uid: " + post.getUserId());
            return;
        }
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("posts").document(post.getPostId()).delete();
    }


    public void wallFragment(LifecycleOwner lifecycleOwner, View view){
        FirebaseFirestore db;
        RecyclerView list;
        FirestoreAdapter adapter;
        list = view.findViewById(R.id.wall_recycler_view);

        db = FirebaseFirestore.getInstance();

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(3)
                .build();

        //Query
        Query query = db.collection("posts");

        FirestorePagingOptions<Post> options = new FirestorePagingOptions.Builder<Post>()
                .setQuery(query, config, new SnapshotParser<Post>() {
                    @NonNull
                    @Override
                    public Post parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        Post post = snapshot.toObject(Post.class);
                        post.setPostId(snapshot.getId());
                        return post;
                    }
                })
                .setLifecycleOwner(lifecycleOwner)
                .build();

        adapter = new FirestoreAdapter(options,this);


        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(MyApplication.context));
        list.setAdapter(adapter);
    }
    @Override
    public void onItemClick(DocumentSnapshot snapshot, int position) {
        Log.d("TAG","Item click: " + position + " the ID: " + snapshot.getId());
        Post p = snapshot.toObject(Post.class);
        Log.d("TAG","likes" + p.getLikes());
        Log.d("TAG",p.getLastName());
    }





}


// FirestoreAdapter
class FirestoreAdapter extends FirestorePagingAdapter<Post,FirestoreAdapter.PostsViewHolder> {

    private OnListItemClick onListItemClick;
    public FirestoreAdapter(@NonNull FirestorePagingOptions<Post> options,OnListItemClick onListItemClick) {
        super(options);
        this.onListItemClick = onListItemClick;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostsViewHolder postsViewHolder, int i, @NonNull Post post) {
        postsViewHolder.listrow_userTextView.setText(post.getFirstName() + " " + post.getLastName());
        postsViewHolder.listrow_km.setText(post.getKilometers());
        postsViewHolder.listrow_Info.setText(post.getText());
        postsViewHolder.listrow_likecounter.setText(post.getLikes());
        if((FirebaseAuth.getInstance().getUid()!=null)&&(FirebaseAuth.getInstance().getUid().equals(post.getUserId()))) {
                postsViewHolder.listrow_delete.setVisibility(View.VISIBLE);
        }

        postsViewHolder.listrow_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance.deletePost(post);
                Navigation.findNavController(view).navigate(R.id.menu_wall);
            }
        });
        postsViewHolder.listrow_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.instance.likePost(post);
                postsViewHolder.listrow_likecounter.setText(post.getLikes());
            }
        });


        String url = post.getImg();

        if(url!=null){
            Picasso.get().load(url).placeholder(R.drawable.avatar).into(postsViewHolder.listrow_ImageView);
        }

        postsViewHolder.listrow_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG","my id is: " + post.getPostId());
                NavGraphDirections.ActionGlobalMapsFragment action = MapsFragmentDirections.actionGlobalMapsFragment(post.getPostId());
                Navigation.findNavController(view).navigate(action);


            }
        });


    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
        return new PostsViewHolder(view);
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state){
            case LOADING_INITIAL:
                Log.d("TAG","Loading initial data");
                break;
            case LOADING_MORE:
                Log.d("TAG","Loading next page");
                break;
            case FINISHED:
                Log.d("TAG","All data loaded");
                break;
            case ERROR:
                Log.d("TAG","Error loading data");
                break;
            case LOADED:
                Log.d("TAG","Total item loaded: "+ getItemCount());
                break;

        }
    }

    public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView listrow_userTextView,listrow_Info,listrow_km, listrow_likecounter,
                listrow_delete;

        private ConstraintLayout listrow_layout;
        private ImageView listrow_ImageView;
        private ImageButton listrow_like;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);
            listrow_userTextView = itemView.findViewById(R.id.listrow_userTextView);
            listrow_km = itemView.findViewById(R.id.listrow_km);
            listrow_ImageView = itemView.findViewById(R.id.listrow_ImageView);
            listrow_like = itemView.findViewById(R.id.listrow_like);
            listrow_Info = itemView.findViewById(R.id.listrow_Info);
            listrow_likecounter = itemView.findViewById(R.id.listrow_likecounter);
            listrow_delete = itemView.findViewById(R.id.listrow_delete);
            listrow_layout = itemView.findViewById(R.id.listrow_layout);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
        }

    }


    public interface OnListItemClick {
        void onItemClick(DocumentSnapshot snapshot,int position);
    }

}

