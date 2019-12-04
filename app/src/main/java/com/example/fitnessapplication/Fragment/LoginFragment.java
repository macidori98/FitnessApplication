package com.example.fitnessapplication.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitnessapplication.R;
import com.example.fitnessapplication.Utils.FragmentNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();
    private static final String MY_LOGIN_SHARED_PREFERENCES = "LoginData";

    private View view;
    private EditText et_username, et_password;
    private Button btn_login;
    private TextView tv_registration;
    private CheckBox chb_remember_me;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        initializeViewElements(view);

        tv_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClicked();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

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
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userRole = "";
                boolean userFound = false;
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String dbUsername = item.child("username").getValue().toString(); //constant
                    String dbPassword = item.child("password").getValue().toString();//constant
                    if (dbUsername.equals(et_username.getText().toString()) && dbPassword.equals(et_password.getText().toString())) {
                        userFound = true;
                        //add data to shared preferences if the user exists in database and remember me is checked
                        if (chb_remember_me.isChecked()) {
                            SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences(MY_LOGIN_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                            sharedPreferences.putString("username", et_username.getText().toString());
                            sharedPreferences.putString("password", et_password.getText().toString());
                            sharedPreferences.putBoolean("remember_me", true);
                            sharedPreferences.apply();
                        }

                        if (item.child("trainer").getValue().toString().equals("true")) {
                            userRole = "Trainer";
                        } else {
                            userRole = "Trainee";
                        }

                        //if user is Trainer
                        if(userRole.equals("Trainer")){
                            FragmentNavigation.getInstance(getContext()).replaceFragment(new TrainerOptionsFragment(),R.id.content_fragment);
                        } else {
                            FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeHomeFragment(),R.id.content_fragment);
                        }
                        break;
                    }
                }
                if(userFound == false) {
                    Toast.makeText(getActivity(), "User not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void onRegisterClicked(){
        FragmentNavigation.getInstance(getContext()).replaceFragment(new RegistrationFragment(),R.id.content_fragment);
    }
}
