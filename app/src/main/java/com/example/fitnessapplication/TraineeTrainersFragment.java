package com.example.fitnessapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TraineeTrainersFragment extends Fragment {

    public final static String TAG = TraineeTrainersFragment.class.getSimpleName();

    private View view;
    private TraineeTrainerAdapter trainerAdapter;
    private List<Trainer> trainerslist;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_trainers, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView_trainee_trainers);
        trainerslist = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //fill trainerslist with trainers from database
        trainerslist.add(new Trainer("alma","korte"));
        trainerAdapter = new TraineeTrainerAdapter(getContext(), trainerslist);
        trainerAdapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //onclick should do something useful
                Toast.makeText(getContext(), "X", Toast.LENGTH_SHORT).show();
                Trainer pickedTrainer = trainerslist.get(trainerAdapter.getSelectedPosition());
            }
        });
        mRecyclerView.setAdapter(trainerAdapter);
    }
}
