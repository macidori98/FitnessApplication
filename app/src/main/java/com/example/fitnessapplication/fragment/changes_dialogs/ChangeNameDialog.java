package com.example.fitnessapplication.fragment.changes_dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.model.User;
import com.example.fitnessapplication.utils.Constant;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeNameDialog extends AppCompatDialogFragment {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private EditText etNewName;

    public ChangeNameDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_change_name, null);
        etNewName = view.findViewById(R.id.editText_change_name);
        changeName(builder, view);
        return builder.create();
    }

    private void changeName(AlertDialog.Builder builder, View view) {
        builder.setView(view).setTitle(R.string.change_name_dialog_title)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), R.string.cancel_change_name, Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase = FirebaseDatabase.getInstance();
                        mRef = mDatabase.getReference(Constant.USERS);
                        if (isNameLengthOk(etNewName.getText().toString())) {
                            User user = new User(Constant.CURRENT_USER.getId(), etNewName.getText().toString(),
                                    Constant.CURRENT_USER.getUsername(), Constant.CURRENT_USER.getPassword(),
                                    Constant.CURRENT_USER.isTrainer(), Constant.CURRENT_USER.isTrainee());
                            mRef.child(Constant.CURRENT_USER.getId()).setValue(user);
                            Toast.makeText(getContext(), R.string.change_name_successful, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), R.string.reg_characters_fail, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private boolean isNameLengthOk(String name) {
        return name.length() >= 6 && name.length() <= 20;
    }
}
