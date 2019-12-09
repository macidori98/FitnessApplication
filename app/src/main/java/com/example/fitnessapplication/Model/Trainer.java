package com.example.fitnessapplication.Model;

public class Trainer {

    String Trainer_name;
    String Trainer_username;

    public Trainer(String user_id,String username) {
        this.Trainer_name = user_id;
        this.Trainer_username = username;
    }

    public String getTrainer_name() {
        return Trainer_name;
    }

    public String getTrainer_username() {
        return Trainer_username;
    }
}
