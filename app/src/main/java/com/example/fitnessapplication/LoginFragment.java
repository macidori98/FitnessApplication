package com.example.fitnessapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();

    private View view;
    private EditText et_username, et_password;
    private Button btn_login;
    private TextView tv_registration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        initializeViewElements(view);

        return view;
    }



    private void initializeViewElements(View view){
        et_username = view.findViewById(R.id.editText_login_username);
        et_password = view.findViewById(R.id.editText_login_password);
        btn_login = view.findViewById(R.id.button_login);
        tv_registration = view.findViewById(R.id.textView_login_register);
    }
}
