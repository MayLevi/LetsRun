package com.example.letsrun;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.letsrun.model.FirestoreAdapter;
import com.example.letsrun.model.Post;
import com.example.letsrun.model.User;
import java.util.LinkedList;

//TODO delete this imports
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class WallFragment extends Fragment implements FirestoreAdapter.OnListItemClick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    LinkedList<User> userList = new LinkedList<>();

    private FirebaseFirestore db;
    private RecyclerView list;
    FirestoreAdapter adapter;


    public WallFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WallFragment newInstance(String param1, String param2) {
        WallFragment fragment = new WallFragment();
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
        View view = inflater.inflate(R.layout.fragment_wall, container, false);


        db = FirebaseFirestore.getInstance();
        list = view.findViewById(R.id.wall_recycler_view);

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
                .setLifecycleOwner(this)
                .build();

        adapter = new FirestoreAdapter(options,this);


        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        return view;

    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot,int position) {
        Log.d("TAG","Item click: " + position + " the ID: " + snapshot.getId());
    }
}