package com.example.fitnessapplication.fragment.trainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.utils.FragmentNavigation;

public class TrainerHomeFragment extends Fragment {

    public static final String TAG = TrainerHomeFragment.class.getSimpleName();

    private View view;
    private Button btnUpload, btnMyContent, btnSettings;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trainer_options, container, false);
        initializeViewElements(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnMyContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).replaceFragment(new TrainerMyContentFragment(), R.id.content_fragment);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).replaceFragment(new TrainerUploadFragment(), R.id.content_fragment);
            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getContext()).replaceFragment(new TrainerSettingsFragment(), R.id.content_fragment);
            }
        });
    }

    private void initializeViewElements(View view) {
        btnMyContent = view.findViewById(R.id.button_option_trainer_my_content);
        btnSettings = view.findViewById(R.id.button_option_trainer_settings);
        btnUpload = view.findViewById(R.id.button_option_trainer_upload);
    }
}
