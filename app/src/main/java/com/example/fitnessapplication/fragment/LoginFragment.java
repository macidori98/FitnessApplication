package com.example.fitnessapplication.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.fitnessapplication.model.User;
import com.example.fitnessapplication.utils.Constant;
import com.example.fitnessapplication.utils.FragmentNavigation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();
    private static final String MY_LOGIN_SHARED_PREFERENCES = "LoginData";

    private View view;
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegistration;
    private CheckBox checkboxRememberMe;
    private DatabaseReference mDatabase;

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
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Constant.USERS);
        textViewRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClicked();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void initializeViewElements(View view) {
        editTextUsername = view.findViewById(R.id.editText_login_username);
        editTextPassword = view.findViewById(R.id.editText_login_password);
        buttonLogin = view.findViewById(R.id.button_login);
        textViewRegistration = view.findViewById(R.id.textView_login_register);
        checkboxRememberMe = view.findViewById(R.id.checkBox_remember_me);
        setLoginData();
    }

    private void setLoginData() {
        //get data from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MY_LOGIN_SHARED_PREFERENCES, MODE_PRIVATE);
        boolean checkbox_isChecked = sharedPreferences.getBoolean(Constant.REMEMBER_ME, false);
        if (checkbox_isChecked) {
            editTextUsername.setText(sharedPreferences.getString(Constant.USERNAME, Constant.USERNAME));
            editTextPassword.setText(sharedPreferences.getString(Constant.PASSWORD, Constant.PASSWORD));
            checkboxRememberMe.setChecked(true);
        }
    }

    private void login() {
        //database query
        //we check if the user is in database or not
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sUserRole = "";
                boolean bUserFound = false;
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    String sDBUsername = item.child(Constant.USERNAME).getValue().toString(); //constant
                    String sDBPassword = item.child(Constant.PASSWORD).getValue().toString();//constant
                    boolean bTrainer = false;
                    boolean bTrainee = false;
                    if (sDBUsername.equals(editTextUsername.getText().toString()) && sDBPassword.equals(editTextPassword.getText().toString())) {
                        bUserFound = true;
                        String sName = item.child(Constant.USERNAME).getValue().toString();
                        String sId = item.child(Constant.ID).getValue().toString();
                        //add data to shared preferences if the user exists in database and remember me is checked
                        if (checkboxRememberMe.isChecked()) {
                            SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences(MY_LOGIN_SHARED_PREFERENCES, MODE_PRIVATE).edit();
                            sharedPreferences.putString(Constant.USERNAME, editTextUsername.getText().toString());
                            sharedPreferences.putString(Constant.PASSWORD, editTextPassword.getText().toString());
                            sharedPreferences.putBoolean(Constant.REMEMBER_ME, true);
                            sharedPreferences.apply();
                        }

                        if (Boolean.valueOf(item.child(Constant.TRAINER).getValue().toString())) {
                            sUserRole = Constant.TRAINER;
                            bTrainer = true;
                        } else {
                            sUserRole = Constant.TRAINEE;
                            bTrainee = true;
                        }

                        User user = new User(sId, sName, sDBUsername, sDBPassword, bTrainer, bTrainee);
                        Constant.CURRENT_USER = user;
                        //if user is Trainer
                        if (sUserRole.equals(Constant.TRAINER)) {
                            FragmentNavigation.getInstance(getContext()).replaceFragment(new TrainerOptionsFragment(), R.id.content_fragment);
                        } else {
                            /*FirebaseDatabase p = FirebaseDatabase.getInstance();
                            DatabaseReference ref = p.getReference(Constant.EXERCISE_VIDEO);
                            String key = ref.push().getKey();
                            ExerciseVideo v = new ExerciseVideo(key, "trainer_id", "https://firebasestorage.googleapis.com/v0/b/fitnessapplication-ee505.appspot.com/o/video%2FArms%2Fvideo-1575819762.mp4?alt=media&token=899445dd-e6cf-4399-b391-e7824b34aa65", "Maci", "Arms", "bla bla bla");
                            ref.child(key).setValue(v);*/
                            FragmentNavigation.getInstance(getContext()).replaceFragment(new TraineeHomeFragment(), R.id.content_fragment);
                        }
                        break;
                    }
                }

                if (bUserFound == false) {
                    Toast.makeText(getActivity(), R.string.user_not_found, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //log
            }
        });
    }

    private void onRegisterClicked() {
        FragmentNavigation.getInstance(getContext()).replaceFragment(new RegistrationFragment(), R.id.content_fragment);
    }
}
