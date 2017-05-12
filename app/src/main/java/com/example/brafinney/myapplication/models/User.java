package com.example.brafinney.myapplication.models;

/**
 * Created by pianoafrik on 5/11/17.
 */

public class User {
    private String user_id, email;

    public User(String user_id, String email) {
        this.user_id = user_id;
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }


    public String getEmail() {
        return email;
    }
}
