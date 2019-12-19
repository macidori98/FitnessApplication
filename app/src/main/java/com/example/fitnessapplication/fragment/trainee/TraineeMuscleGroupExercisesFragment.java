package com.example.fitnessapplication.fragment.trainee;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.adapter.TraineeMuscleGroupExercisesAdapter;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TraineeMuscleGroupExercisesFragment extends Fragment {

    public static final String TAG = TraineeMuscleGroupExercisesFragment.class.getSimpleName();

    private View view;
    private TextView tvTitle;
    private RecyclerView recyclerViewTraineeMuscleGroupExercise;
    private TraineeMuscleGroupExercisesAdapter mAdapter;
    private List<ExerciseVideo> videoList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_muscle_group_exercises, container, false);
        initializeElements(view);
        return view;
    }

    private void getData() {
        final String muscle_group = Constant.SELECTED_MUSCLE_GROUP.toLowerCase();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String snapshot_muscle_group = snapshot.child(Constant.MUSCLE_GROUP).getValue().toString().toLowerCase();
                    if (snapshot_muscle_group.equals(muscle_group)) {
                        String sDBId = snapshot.child(Constant.ID).getValue().toString();
                        String sDBTrainerId = snapshot.child(Constant.TRAINER_ID).getValue().toString();
                        String sDBUrl = snapshot.child(Constant.URL).getValue().toString();
                        String sDBTitle = snapshot.child(Constant.TITLE).getValue().toString();
                        String sDBDescription = snapshot.child(Constant.DESCRIPTION).getValue().toString();
                        ExerciseVideo exerciseVideo = new ExerciseVideo(sDBId, sDBTrainerId, sDBUrl, sDBTitle, muscle_group, sDBDescription);
                        videoList.add(exerciseVideo);
                    }
                }
                mAdapter = new TraineeMuscleGroupExercisesAdapter(videoList, getContext());
                recyclerViewTraineeMuscleGroupExercise.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(Constant.FIREBASE_ON_CANCELLED, Constant.FIREBASE_ON_CANCELLED);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initializeElements(View view){
        videoList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.EXERCISE_VIDEO);
        getData();
        tvTitle = view.findViewById(R.id.textView_title);
        tvTitle.setText(Constant.SELECTED_MUSCLE_GROUP);
        recyclerViewTraineeMuscleGroupExercise = view.findViewById(R.id.recyclerView_trainee_muscle_group_exercises);
        recyclerViewTraineeMuscleGroupExercise.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
