package com.example.fitnessapplication.fragment.trainee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.fragment.changes_dialogs.ChangeNameDialog;
import com.example.fitnessapplication.fragment.changes_dialogs.ChangePasswordDialog;
import com.example.fitnessapplication.utils.Constant;

public class TraineeSettingsFragment extends Fragment {

    public final static String TAG = TraineeSettingsFragment.class.getSimpleName();

    private View view;
    private Button btnChangeName, btnChangePassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainee_settings, container, false);
        initializeViewElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeName();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
        changePasswordDialog.show(getActivity().getSupportFragmentManager(), Constant.CHANGE_PASSWORD);
    }

    private void changeName() {
        ChangeNameDialog changeNameDialog = new ChangeNameDialog();
        changeNameDialog.show(getActivity().getSupportFragmentManager(), Constant.CHANGE_NAME);
    }

    private void initializeViewElements(View view) {
        btnChangeName = view.findViewById(R.id.button_trainee_settings_change_name);
        btnChangePassword = view.findViewById(R.id.button_trainee_settings_change_password);
    }
}
