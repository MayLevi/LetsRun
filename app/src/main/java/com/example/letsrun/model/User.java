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
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String age;
    private String imageUrl;
    private Long lastUpdated;

    //Constructors
    public User() { }
    public User(@NonNull String userId ,String firstName, String lastName,String email,String age) {
        this.userId=userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }


    //Getters
    @NonNull
    public String getUserId() {
        return userId;
    }
    public String getEmail() {
        return email;
    }
    public String getAge() {
        return age;
    }
    public Long getLastUpdated() {
        return lastUpdated;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email",email);
        result.put("userId", userId);
        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("age", age);
        result.put("imageUrl", imageUrl);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    //Setters
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }
    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setFirstName(String userName) {
        this.firstName = userName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void fromMap(Map<String, Object> map){
        userId = (String)map.get("userId");
        email = (String)map.get("email");
        age = (String)map.get("age");
        firstName = (String)map.get("firstName");
        lastName = (String)map.get("lastName");
        imageUrl = (String)map.get("imageUrl");
        Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
    }




}
