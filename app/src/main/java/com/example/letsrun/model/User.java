package com.example.letsrun.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class User {


    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String imageUrl;
    //private List<RunningTracks> runs;

//    public List<RunningTracks> getRuns() {
//        return runs;
//    }
//
//    public void setRuns(List<RunningTracks> runs) {
//        this.runs = runs;
//    }
public User(@NonNull String id, String name) {
    this.id = id;
    this.name = name;
}
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
