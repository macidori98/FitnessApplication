package com.example.fitnessapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class TraineeHomeFragment extends Fragment {

    public final static String TAG = TraineeHomeFragment.class.getSimpleName();

    private View view;
    private LinearLayout linearLayout_user_home;
    private Button btn_exercises, btn_trainers, btn_trainee_settings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_home, container, false);

        initializeViewElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_exercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onExercisesClicked();
            }
        });

        btn_trainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTrainersClicked();
            }
        });
        btn_trainee_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSettingsClicked();
            }
        });
    }

    private void initializeViewElements(View view){
        btn_exercises = view.findViewById(R.id.button_home_trainee_exercises);
        btn_trainers = view.findViewById(R.id.button_home_trainee_trainer);
        btn_trainee_settings = view.findViewById(R.id.button_home_trainee_settings);

    }

    private void onExercisesClicked(){
        FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeMuscleGroupsFragment(),R.id.content_fragment);
    }

    private void onTrainersClicked(){
        FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeTrainersFragment(),R.id.content_fragment);
    }

    private void onSettingsClicked(){
        FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeSettingsFragment(),R.id.content_fragment);
    }

}
