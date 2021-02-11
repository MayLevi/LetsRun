package com.example.letsrun.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class User {


    @PrimaryKey
    @NonNull
    private String userName;
    private String password;
    private String imageUrl;
    private Long lastUpdated;

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("password", password);
        result.put("imageUrl", imageUrl);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map){
        userName = (String)map.get("userName");
        password = (String)map.get("password");
        imageUrl = (String)map.get("imageUrl");
        Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public User(@NonNull String userName, String password) {
    this.userName = userName;
    this.password = password;
}
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
