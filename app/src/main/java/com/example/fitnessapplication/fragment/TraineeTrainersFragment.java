package com.example.fitnessapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.adapter.TraineeTrainerAdapter;
import com.example.fitnessapplication.interfaces.OnItemClickListener;
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

public class TraineeTrainersFragment extends Fragment {

    public final static String TAG = TraineeTrainersFragment.class.getSimpleName();

    private View view;
    private TraineeTrainerAdapter trainerAdapter;
    private List<Trainer> trainerList;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_trainers, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView_trainee_trainers);
        trainerList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Constant.USERS);
        getTrainers();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getTrainers() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    if (Boolean.valueOf(item.child(Constant.TRAINER).getValue().toString())) {
                        String dbUsername = item.child(Constant.USERNAME).getValue().toString();
                        String dbName = item.child(Constant.NAME).getValue().toString();
                        String id = item.child(Constant.ID).getValue().toString();
                        Trainer dbTrainer = new Trainer(id, dbName, dbUsername);
                        trainerList.add(dbTrainer);
                    }
                }
                //create adapter with the previously aquired list
                trainerAdapter = new TraineeTrainerAdapter(getContext(), trainerList);
                trainerAdapter.setOnClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Constant.SELECTED_TRAINER = trainerList.get(position);
                        FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeTrainerMyContentFragment(), R.id.content_fragment);
                    }
                });
                mRecyclerView.setAdapter(trainerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //log
            }
        });
    }
}
