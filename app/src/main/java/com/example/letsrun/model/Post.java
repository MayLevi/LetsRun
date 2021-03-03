package com.example.letsrun.model;

import android.widget.ImageView;

import com.google.type.LatLng;

public class Post {

    private String postId,userId,firstName,lastName,age,kilometers,text,location,img,likes,lat,lon,email;

    //Constructors
    public Post() {}

    //TODO add postId to constructor
//    public Post(String userId, String firstName, String lastName, String age, String kilometers, String text, String img) {
//        this.postId = postId;
//        this.likes = likes;
//        this.userId = userId;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.age = age;
//        this.kilometers = kilometers;
//        this.text = text;
//        this.location = location;
//        this.img = img;
//    }
    public Post(String userId, String firstName, String lastName, String age, String kilometers,
                String text, String img,String email,String lat,String lon) {
        this.postId = postId;
        this.likes = likes;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.kilometers = kilometers;
        this.text = text;
        this.location = location;
        this.img = img;
        this.lat = lat;
        this.lon = lon;
        this.email = email;
    }


    public String getEmail() {
        return email;
    }


    //Setters
    public void setText(String text) {
        this.text = text;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
    public void setLikes(String likes) { this.likes = likes; }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public void setLat(String lat) { this.lat = lat;}
    public void setLon(String lon) { this.lon = lon;  }

    public void setLocation(String location) {
        this.location = location;
    }
    public void setImg(String img) {
        this.img = img;
    }

    //Getters
    public String getUserId() {
        return userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getAge() {
        return age;
    }
    public String getKilometers() {
        return kilometers;
    }
    public String getText() {
        return text;
    }
    public String getLocation() {
        return location;
    }
    public String getImg() {
        return img;
    }
    public String getLikes() { return likes; }
    public String getPostId() {
        return postId;
    }
    public String getLat() {return lat; }
    public String getLon() { return lon; }
}
