package com.example.fitnessapplication;

public class Trainer {

    String user_id;
    String username;

    public Trainer(String user_id,String username) {
        this.user_id = user_id;
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }
}
