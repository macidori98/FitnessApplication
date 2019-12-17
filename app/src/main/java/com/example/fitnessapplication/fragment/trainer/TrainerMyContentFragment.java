package com.example.fitnessapplication.fragment.trainer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.adapter.TrainerMyContentAdapter;
import com.example.fitnessapplication.fragment.trainee.TraineeTrainersFragment;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrainerMyContentFragment extends Fragment {

    public final static String TAG = TraineeTrainersFragment.class.getSimpleName();
    private View view;
    private List<ExerciseVideo> videoList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private RecyclerView recyclerView_trainer_my_content;
    private TrainerMyContentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainer_my_content, container, false);


        videoList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.EXERCISE_VIDEO);
        recyclerView_trainer_my_content = view.findViewById(R.id.recyclerView_trainer_my_content);
        recyclerView_trainer_my_content.setLayoutManager(new LinearLayoutManager(getContext()));
        getData();

        initializeViewElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void getData() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String trainer_id = snapshot.child(Constant.TRAINER_ID).getValue().toString();
                    if (trainer_id.toLowerCase().equals(Constant.CURRENT_USER.getId().toLowerCase())) {
                        String id = snapshot.child(Constant.ID).getValue().toString();
                        String url = snapshot.child(Constant.URL).getValue().toString();
                        String title = snapshot.child(Constant.TITLE).getValue().toString();
                        String description = snapshot.child(Constant.DESCRIPTION).getValue().toString();
                        String musclegroup = snapshot.child(Constant.MUSCLE_GROUP).getValue().toString();
                        ExerciseVideo exerciseVideo = new ExerciseVideo(id, trainer_id, url, title, musclegroup, description);
                        videoList.add(exerciseVideo);
                    }
                }
                mAdapter = new TrainerMyContentAdapter(videoList, getActivity());
                recyclerView_trainer_my_content.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Log.e("TraineMuscleGroupError","TraineMuscleGroupError");
            }
        });
    }

    private void initializeViewElements(View view) {

    }
}
