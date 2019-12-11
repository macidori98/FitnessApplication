package com.example.fitnessapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.Adapter.TraineeMuscleGroupExercisesAdapter;
import com.example.fitnessapplication.Model.ExerciseVideo;
import com.example.fitnessapplication.R;
import com.example.fitnessapplication.Utils.Constant;
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
    private TextView tv_title;
    private RecyclerView recyclerView_trainee_muscle_group_exercise;
    private TraineeMuscleGroupExercisesAdapter mAdapter;
    private List<ExerciseVideo> videoList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_muscle_group_exercises, container, false);
        videoList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.EXERCISE_VIDEO);
        getData();
        tv_title = view.findViewById(R.id.textView_title);
        tv_title.setText(Constant.SELECTED_MUSCLE_GROUP);
        recyclerView_trainee_muscle_group_exercise = view.findViewById(R.id.recyclerView_trainee_muscle_group_exercises);
        recyclerView_trainee_muscle_group_exercise.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void getData(){
        final String muscle_group = Constant.SELECTED_MUSCLE_GROUP.toLowerCase();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String snapshot_muscle_group = snapshot.child(Constant.MUSCLE_GROUP).getValue().toString().toLowerCase();
                    if (snapshot_muscle_group.equals(muscle_group)){
                        String id = snapshot.child(Constant.ID).getValue().toString();
                        String trainer_id = snapshot.child(Constant.TRAINER_ID).getValue().toString();
                        String url = snapshot.child(Constant.URL).getValue().toString();
                        String title = snapshot.child(Constant.TITLE).getValue().toString();
                        String description = snapshot.child(Constant.DESCRIPTION).getValue().toString();
                        ExerciseVideo exerciseVideo = new ExerciseVideo(id,trainer_id, url, title, muscle_group, description);
                        videoList.add(exerciseVideo);
                    }
                }
                mAdapter = new TraineeMuscleGroupExercisesAdapter(videoList, getContext());
                recyclerView_trainee_muscle_group_exercise.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
