package com.example.fitnessapplication.model;

public class Trainer {

    private String trainer_name;
    private String trainer_username;
    private String id;

    public Trainer(String id, String name, String username) {
        this.trainer_name = name;
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
