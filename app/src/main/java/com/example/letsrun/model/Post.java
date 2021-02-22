package com.example.letsrun.model;

import android.widget.ImageView;

public class Post {

    private String postId,userId,firstName,lastName,age,kilometers,text,location;
    private String img;

    //Constructors
    public Post() {}
    //TODO add postId to constructor
    public Post(String userId, String firstName, String lastName, String age, String kilometers, String text, String location, String img) {
        this.postId = postId;

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.kilometers = kilometers;
        this.text = text;
        this.location = location;
        this.img = img;
    }

    //Setters
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
    public void setText(String text) {
        this.text = text;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public void setPostId(String postId) {
        this.postId = postId;
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
    public String getPostId() {
        return postId;
    }
}
