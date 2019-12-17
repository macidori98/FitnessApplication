package com.example.fitnessapplication.fragment.trainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrainerUploadFragment extends Fragment {

    public final static String TAG = TrainerUploadFragment.class.getSimpleName();

    private View view;
    private EditText etUploadTitle, etUploadDescription;
    private Button btnUpload;
    private Spinner spMuscleGroup;
    private ArrayAdapter<CharSequence> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainer_upload, container, false);
        initializeViewElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickUpload();
            }
        });
    }

    private void initializeViewElements(View view) {
        etUploadTitle = view.findViewById(R.id.et_trainer_upload_title);
        etUploadDescription = view.findViewById(R.id.et_trainer_upload_description);
        spMuscleGroup = view.findViewById(R.id.sp_upload_content);
        btnUpload = view.findViewById(R.id.btn_trainer_upload_upload);
        setupSpinner(view);
    }

    private void onClickUpload(){
        FirebaseDatabase p = FirebaseDatabase.getInstance();
        DatabaseReference ref = p.getReference(Constant.EXERCISE_VIDEO);
        String key = ref.push().getKey();
        String title = etUploadTitle.getText().toString();
        String description = etUploadDescription.getText().toString();
        int idx = (int) spMuscleGroup.getSelectedItemId();
        String muscleGroupSelected = adapter.getItem(idx).toString();
        ExerciseVideo exerciseVideo = new ExerciseVideo(key,Constant.CURRENT_USER.getId(),"https://firebasestorage.googleapis.com/v0/b/fitnessapplication2.appspot.com/o/video-1576262209.mp4?alt=media&token=c9e0521d-6e34-4166-a690-967b8da344ca",title,muscleGroupSelected,description);
        ref.child(key).setValue(exerciseVideo);
        Toast.makeText(getContext(), R.string.upload_successful, Toast.LENGTH_SHORT).show();
    }

    private void setupSpinner(View view){
        spMuscleGroup = view.findViewById(R.id.sp_upload_content);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.trainee_muscle_groups, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spMuscleGroup.setAdapter(adapter);
    }
}
