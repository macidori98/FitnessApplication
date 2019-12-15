package com.example.fitnessapplication.model;

public class ExerciseVideo {
    private String id; //key
    private String trainerId;
    private String url;
    private String title;
    private String muscleGroup;
    private String description;

    public ExerciseVideo(String ID, String trainer_id, String url, String title, String muscle_group, String description) {
        this.id = ID;
        this.trainerId = trainer_id;
        this.url = url;
        this.title = title;
        this.muscleGroup = muscle_group;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
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

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
