package com.example.fitnessapplication.Model;

public class User {

    public static final String TAG = User.class.getSimpleName();

    private String name, username, password;
    private boolean trainer, trainee;

    public User() {
    }

    public User(String name, String username, String password, boolean trainer, boolean trainee) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.trainer = trainer;
        this.trainee = trainee;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isTrainer() {
        return trainer;
    }

    public boolean isTrainee() {
        return trainee;
    }
}
