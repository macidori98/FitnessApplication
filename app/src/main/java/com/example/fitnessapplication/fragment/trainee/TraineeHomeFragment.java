package com.example.fitnessapplication.fragment.trainee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.model.Trainer;
import com.example.fitnessapplication.utils.Constant;
import com.example.fitnessapplication.utils.FragmentNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TraineeHomeFragment extends Fragment {

    public final static String TAG = TraineeHomeFragment.class.getSimpleName();

    private View view;
    private LinearLayout linearLayoutUserHome;
    private Button btnExercises, btnTrainers, btnTraineeSettings;
    private List<Trainer> trainerList;
    private DatabaseReference mRef;
    private FirebaseDatabase mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_home, container, false);
        initializeViewElements(view);
        getTrainers();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeMuscleGroupsFragment(), R.id.content_fragment);
            }
        });
        btnTrainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeTrainersFragment(), R.id.content_fragment);
            }
        });
        btnTraineeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeSettingsFragment(), R.id.content_fragment);
            }
        });
    }

    private void initializeViewElements(View view) {
        btnExercises = view.findViewById(R.id.button_home_trainee_exercises);
        btnTrainers = view.findViewById(R.id.button_home_trainee_trainer);
        btnTraineeSettings = view.findViewById(R.id.button_home_trainee_settings);
        trainerList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.USERS);
    }

    private void getTrainers() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    boolean bTrainer = Boolean.valueOf(snapshot.child(Constant.TRAINER).getValue().toString());
                    if (bTrainer) {
                        String sDBId = snapshot.child(Constant.ID).getValue().toString();
                        String sDBName = snapshot.child(Constant.NAME).getValue().toString();
                        String sDBUsername = snapshot.child(Constant.USERNAME).getValue().toString();
                        Trainer trainer = new Trainer(sDBId, sDBName, sDBUsername);
                        trainerList.add(trainer);
                    }
                }
                Constant.TRAINERS_LIST = trainerList;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
