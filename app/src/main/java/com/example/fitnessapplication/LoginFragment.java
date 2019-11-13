package com.example.fitnessapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();
    private static final String MY_LOGIN_SHARED_PREFERENCES = "LoginData";

    private View view;
    private EditText et_username, et_password;
    private Button btn_login;
    private TextView tv_registration;
    private CheckBox chb_remember_me;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        initializeViewElements(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void initializeViewElements(View view){
        et_username = view.findViewById(R.id.editText_login_username);
        et_password = view.findViewById(R.id.editText_login_password);
        btn_login = view.findViewById(R.id.button_login);
        tv_registration = view.findViewById(R.id.textView_login_register);
        chb_remember_me = view.findViewById(R.id.checkBox_remember_me);
        setLoginData();
    }

    private void setLoginData(){
        //get data from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MY_LOGIN_SHARED_PREFERENCES, MODE_PRIVATE);
        boolean checkbox_isChecked = sharedPreferences.getBoolean("remember_me",false);
        if (checkbox_isChecked){
            et_username.setText(sharedPreferences.getString("username","username"));
            et_password.setText(sharedPreferences.getString("password","password"));
            chb_remember_me.setChecked(true);
        }
    }

    private void login(){
        //database query
        //we check if the user is in database or not

        //add data to shared preferences if the user exists in database
        if (chb_remember_me.isChecked()){
            SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences(MY_LOGIN_SHARED_PREFERENCES, MODE_PRIVATE).edit();
            sharedPreferences.putString("username", et_username.getText().toString());
            sharedPreferences.putString("password", et_password.getText().toString());
            sharedPreferences.putBoolean("remember_me",true);
            sharedPreferences.apply();
        }
    }
}
