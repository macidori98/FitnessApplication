package com.example.fitnessapplication.fragment.changes_dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

public class DeleteContentDialog  extends AppCompatDialogFragment {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private ExerciseVideo oldExerciseVideo;
    private RefreshListener refreshListener;
    private int position;

    public DeleteContentDialog(ExerciseVideo exerciseVideo,int position){
        this.oldExerciseVideo = exerciseVideo;
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_delete_content, null);
        deleteContent(builder, view);
        return builder.create();
    }

    private void deleteContent(AlertDialog.Builder builder, View view){
        builder.setView(view).setTitle(R.string.delete_content_dialog_title)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), R.string.cancel_delete_content, Toast.LENGTH_SHORT).show();
                    }
                }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDatabase = FirebaseDatabase.getInstance();
                mRef = mDatabase.getReference(Constant.EXERCISE_VIDEO);
                mRef.child(oldExerciseVideo.getId()).removeValue();
                Toast.makeText(getContext(), R.string.delete_content_successful, Toast.LENGTH_SHORT).show();
                refreshListener.deleteFromAdapter(position);
            }
        });
    }

    public void setListener(RefreshListener listener){this.refreshListener = listener;}
}
