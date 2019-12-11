package com.example.fitnessapplication.Model;

public class Trainer {

    private String trainer_name;
    private String trainer_username;
    private String id;

    public Trainer(String id, String user_id,String username) {
        this.trainer_name = user_id;
        this.trainer_username = username;
        this.id = id;
    }

    public String getTrainer_name() {
        return trainer_name;
    }

    public String getTrainer_username() {
        return trainer_username;
    }

    public String getId() {
        return id;
    }
}
