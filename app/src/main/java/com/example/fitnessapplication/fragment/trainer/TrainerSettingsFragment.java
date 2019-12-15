package com.example.fitnessapplication.fragment.trainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.R;

public class TrainerSettingsFragment extends Fragment {
    public static final String TAG = TrainerSettingsFragment.class.getSimpleName();

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainer_settings, container, false);
        initializeElements(view);
        return view;
    }

    private void initializeElements(View view){

    }
}
