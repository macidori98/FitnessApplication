package com.example.fitnessapplication.model;

public class ExerciseVideo {
    private String id; //key
    private String trainer_id;
    private String url;
    private String title;
    private String muscle_group;
    private String description;

    public ExerciseVideo(String ID, String trainer_id, String url, String title, String muscle_group, String description) {
        this.id = ID;
        this.trainer_id = trainer_id;
        this.url = url;
        this.title = title;
        this.muscle_group = muscle_group;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainer_id() {
        return trainer_id;
    }

    public void setTrainer_id(String trainer_id) {
        this.trainer_id = trainer_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMuscle_group() {
        return muscle_group;
    }

    public void setMuscle_group(String muscle_group) {
        this.muscle_group = muscle_group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
