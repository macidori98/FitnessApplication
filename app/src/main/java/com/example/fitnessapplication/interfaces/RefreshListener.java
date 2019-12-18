package com.example.fitnessapplication.interfaces;

import com.example.fitnessapplication.model.ExerciseVideo;

public interface RefreshListener {
    void refreshAdapter(int position, ExerciseVideo exerciseVideo);
    void deleteFromAdapter(int position);
}

