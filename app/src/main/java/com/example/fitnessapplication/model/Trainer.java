package com.example.fitnessapplication.model;

public class Trainer {

    private String trainerName;
    private String trainerUsername;
    private String id;

    public Trainer(String id, String name, String username) {
        this.trainerName = name;
        this.trainerUsername = username;
        this.id = id;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public String getId() {
        return id;
    }
}
