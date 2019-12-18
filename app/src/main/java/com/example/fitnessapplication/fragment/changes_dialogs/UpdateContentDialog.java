package com.example.fitnessapplication.fragment.changes_dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.interfaces.RefreshListener;
import com.example.fitnessapplication.model.ExerciseVideo;
import com.example.fitnessapplication.utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateContentDialog extends AppCompatDialogFragment {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private EditText etNewTitle, etNewDescription;
    private Spinner spNewGroup;
    private ExerciseVideo oldExerciseVideo;
    private ArrayAdapter<CharSequence> adapter;
    private RefreshListener refreshListener;
    private int position;

    public UpdateContentDialog(ExerciseVideo exerciseVideo,int position){
        this.oldExerciseVideo = exerciseVideo;
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_change_content, null);
        initializeViewElements(view);
        changeContent(builder, view);
        return builder.create();
    }

    //Function that sets up the spinner with the muscle groups
    private void setupSpinner(View view){
        spNewGroup = view.findViewById(R.id.spinner_changecontent);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.trainee_muscle_groups, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spNewGroup.setAdapter(adapter);
    }

    private void initializeViewElements(View view) {
        etNewTitle = view.findViewById(R.id.editText_change_content_title);
        etNewDescription = view.findViewById(R.id.editText_change_content_description);
        setupSpinner(view);
    }

    //Function that updates the existing exercise video with the inputs given in the edit texts
    private void changeContent(AlertDialog.Builder builder, View view) {
        builder.setView(view).setTitle(R.string.change_content_dialog_title)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), R.string.cancel_change_content, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase = FirebaseDatabase.getInstance();
                        mRef = mDatabase.getReference(Constant.EXERCISE_VIDEO);
                        String oldID = oldExerciseVideo.getId();
                        String oldTrainerID = oldExerciseVideo.getTrainerId();
                        String oldURL = oldExerciseVideo.getUrl();
                        String title = etNewTitle.getText().toString();
                        int idx = (int) spNewGroup.getSelectedItemId();
                        String muscleGroupSelected = adapter.getItem(idx).toString();
                        String description = etNewDescription.getText().toString();
                        ExerciseVideo exerciseVideo = new ExerciseVideo(oldID,oldTrainerID,oldURL,title,muscleGroupSelected,description);
                        mRef.child(oldID).setValue(exerciseVideo);
                        Toast.makeText(getContext(), R.string.change_content_successful, Toast.LENGTH_SHORT).show();
                        refreshListener.refreshAdapter(position,exerciseVideo);
                    }
                });
    }
    public void setListener(RefreshListener listener){this.refreshListener = listener;}
}
