//package com.example.letsrun.model;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.letsrun.R;
//import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
//import com.firebase.ui.firestore.paging.FirestorePagingOptions;
//import com.firebase.ui.firestore.paging.LoadingState;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.squareup.picasso.Picasso;
//
//public class FirestoreAdapter extends FirestorePagingAdapter<Post,FirestoreAdapter.PostsViewHolder> {
//
//    private OnListItemClick onListItemClick;
//    public FirestoreAdapter(@NonNull FirestorePagingOptions<Post> options,OnListItemClick onListItemClick) {
//        super(options);
//        this.onListItemClick = onListItemClick;
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull PostsViewHolder postsViewHolder, int i, @NonNull Post post) {
//        postsViewHolder.listrow_userTextView.setText(post.getFirstName() + " " + post.getLastName());
//        postsViewHolder.listrow_km.setText(post.getKilometers());
//        postsViewHolder.listrow_location.setText(post.getLocation());
////        postsViewHolder.listrow_like.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Model.instance.likePost(post);
////            }
////        });
//
//        String url = post.getImg();
//
//        if(url!=null){
//            Picasso.get().load(url).placeholder(R.drawable.avatar).into(postsViewHolder.listrow_ImageView);
//        }
//    }
//
//    @NonNull
//    @Override
//    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,parent,false);
//        return new PostsViewHolder(view);
//    }
//
//    @Override
//    protected void onLoadingStateChanged(@NonNull LoadingState state) {
//        super.onLoadingStateChanged(state);
//        switch (state){
//            case LOADING_INITIAL:
//                Log.d("TAG","Loading initial data");
//                break;
//            case LOADING_MORE:
//                Log.d("TAG","Loading next page");
//                break;
//            case FINISHED:
//                Log.d("TAG","All data loaded");
//                break;
//            case ERROR:
//                Log.d("TAG","Error loading data");
//                break;
//            case LOADED:
//                Log.d("TAG","Total item loaded: "+ getItemCount());
//                break;
//
//        }
//    }
//
//    public class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//
//        private TextView listrow_userTextView,listrow_location,listrow_km;
//
//        private ImageView listrow_ImageView;
//        private ImageButton listrow_like;
//
//        public PostsViewHolder(@NonNull View itemView) {
//            super(itemView);
//            listrow_userTextView = itemView.findViewById(R.id.listrow_userTextView);
//            listrow_km = itemView.findViewById(R.id.listrow_km);
//            listrow_ImageView = itemView.findViewById(R.id.listrow_ImageView);
//            listrow_like = itemView.findViewById(R.id.listrow_like);
//            listrow_location = itemView.findViewById(R.id.listrow_location);
//
//
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//
//            onListItemClick.onItemClick(getItem(getAdapterPosition()),getAdapterPosition());
//        }
//
//    }
//
//
//    public interface OnListItemClick {
//        void onItemClick(DocumentSnapshot snapshot,int position);
//    }
//
//}