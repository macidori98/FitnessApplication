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
import com.example.fitnessapplication.interfaces.RefreshListener;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrainerMyContentFragment extends Fragment implements RefreshListener {

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
        initializeViewElements(view);
        getData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //Query to get the exercise videos of the current user
    private void getData() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                videoList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String sDBtrainer_id = snapshot.child(Constant.TRAINER_ID).getValue().toString();
                    if (sDBtrainer_id.toLowerCase().equals(Constant.CURRENT_USER.getId().toLowerCase())) {
                        String sDBid = snapshot.child(Constant.ID).getValue().toString();
                        String sDBurl = snapshot.child(Constant.URL).getValue().toString();
                        String sDBtitle = snapshot.child(Constant.TITLE).getValue().toString();
                        String sDBdescription = snapshot.child(Constant.DESCRIPTION).getValue().toString();
                        String sDBmusclegroup = snapshot.child(Constant.MUSCLE_GROUP).getValue().toString();
                        ExerciseVideo exerciseVideo = new ExerciseVideo(sDBid, sDBtrainer_id, sDBurl, sDBtitle, sDBmusclegroup, sDBdescription);
                        videoList.add(exerciseVideo);
                    }
                }
                mAdapter = new TrainerMyContentAdapter(videoList, getActivity());
                recyclerView_trainer_my_content.setAdapter(mAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(String.valueOf(R.string.trainer_my_content_error),String.valueOf(R.string.trainer_my_content_error));
            }
        });
    }

    private void initializeViewElements(View view) {
        videoList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.EXERCISE_VIDEO);
        recyclerView_trainer_my_content = view.findViewById(R.id.recyclerView_trainer_my_content);
        recyclerView_trainer_my_content.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    //Function that updates the recycler view with the updated element
    @Override
    public void refreshAdapter(int position,ExerciseVideo exerciseVideo) {
        mAdapter.notifyItemChanged(position);
        videoList.set(position,exerciseVideo);
    }

    @Override
    public void deleteFromAdapter(int position) {
        videoList.remove(position);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyItemRangeChanged(position,videoList.size());
        this.mAdapter.notifyDataSetChanged();
    }
}
