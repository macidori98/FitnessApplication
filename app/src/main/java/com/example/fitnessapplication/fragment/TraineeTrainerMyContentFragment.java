package com.example.fitnessapplication.fragment;

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

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.adapter.TraineeTrainerMyContentAdapter;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TraineeTrainerMyContentFragment extends Fragment {
    public static final String TAG = TraineeTrainerMyContentFragment.class.getSimpleName();

    private View view;
    private RecyclerView recyclerViewTraineeTrainerMyContent;
    private TextView textViewSelectedTrainer;
    private List<ExerciseVideo> videoList;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private TraineeTrainerMyContentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_trainer_my_content, container, false);
        initializeElements(view);
        getVideos();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void initializeElements(View view) {
        recyclerViewTraineeTrainerMyContent = view.findViewById(R.id.recyclerView_trainee_trainer_my_content);
        textViewSelectedTrainer = view.findViewById(R.id.textView_trainee_trainer_my_content_trainer_name);
        textViewSelectedTrainer.setText(Constant.SELECTED_TRAINER.getTrainer_name());
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.EXERCISE_VIDEO);
        videoList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewTraineeTrainerMyContent.setLayoutManager(linearLayoutManager);
    }

    private void getVideos() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String sTrainerId = snapshot.child(Constant.TRAINER_ID).getValue().toString();
                    if (sTrainerId.equals(Constant.SELECTED_TRAINER.getId())) {
                        String sId = snapshot.child(Constant.ID).getValue().toString();
                        String sDescription = snapshot.child(Constant.DESCRIPTION).getValue().toString();
                        String sMuscleGroup = snapshot.child(Constant.MUSCLE_GROUP).getValue().toString();
                        String sURL = snapshot.child(Constant.URL).getValue().toString();
                        String sTitle = snapshot.child(Constant.TITLE).getValue().toString();
                        ExerciseVideo exerciseVideo = new ExerciseVideo(sId, sTrainerId, sURL, sTitle, sMuscleGroup, sDescription);
                        videoList.add(exerciseVideo);
                    }
                }
                mAdapter = new TraineeTrainerMyContentAdapter(getContext(), videoList);

                recyclerViewTraineeTrainerMyContent.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
