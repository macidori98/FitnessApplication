package com.example.fitnessapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.List;

public class RegistrationFragment extends Fragment {

    public static final String TAG = RegistrationFragment.class.getSimpleName();

    private View view;
    FirebaseStorage storage;
    StorageReference storageReference;
    private EditText et_name, et_username, et_password, et_confirm_password;
    private Button btn_registration;
    private RadioGroup rg_trainer_trainee;
    private String selectedUserType;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_registration, container, false);
        //storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(Constant.USERS);
        initializeViewElements(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserToDatabase();
            }
        });

    }

    private void addUserToDatabase(){
        if (!isUserAlreadyExistsInDatabase()){
            boolean passwordsMatch = checkEnteredPasswordMatch(et_password.getText().toString(), et_confirm_password.getText().toString());
            boolean usernamePasswordLength = checkUsernameAndPasswordLength(et_username.getText().toString(),et_password.getText().toString());

            if (passwordsMatch && usernamePasswordLength){
                if(selectedUserType.equals("Trainer")) {
                    User user = new User(et_name.getText().toString(), et_username.getText().toString(), et_password.getText().toString(), true, false);
                    mRef.child(mRef.push().getKey()).setValue(user);//
                    Toast.makeText(getActivity(),R.string.reg_user_created, Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(et_name.getText().toString(), et_username.getText().toString(), et_password.getText().toString(), false, true);
                    mRef.child(mRef.push().getKey()).setValue(user);

                    Toast.makeText(getActivity(),R.string.reg_user_created, Toast.LENGTH_SHORT).show();
                }

            }

        } else {
            //otherwise we make a toast that he already exists
            Toast.makeText(getActivity(),R.string.reg_user_already_exists, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isUserAlreadyExistsInDatabase(){
        return false;
    }


    //username should contain 6 characters
    //password should contain 6 characters
    private boolean checkUsernameAndPasswordLength(String username, String password){
        return username.length() >= 6 && password.length() >= 6;
    }

    private boolean checkEnteredPasswordMatch(String password, String confirm_password){
        return password.equals(confirm_password);
    }

    private void initializeViewElements(View view){
        et_name = view.findViewById(R.id.editText_registration_name);
        et_username = view.findViewById(R.id.editText_registration_username);
        et_password = view.findViewById(R.id.editText_registration_password);
        et_confirm_password = view.findViewById(R.id.editText_registration_confirm_password);
        btn_registration = view.findViewById(R.id.button_registration);
        rg_trainer_trainee = view.findViewById(R.id.radioGroup_registration_user_type);

        //Create radio buttons into radiogroup
        final List<String> userTypes = Arrays.asList(getResources().getStringArray(R.array.registration_user_types));
        for (int i = 0; i < userTypes.size(); ++i){
            RadioButton rb = new RadioButton(getActivity());
            rb.setText(userTypes.get(i));
            rb.setId(i);

            //What happens when we choose one of them
            rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()){
                        MainActivity ma =(MainActivity) getActivity();
                        selectedUserType = compoundButton.getText().toString();
                    } else {
                        selectedUserType = "";
                    }
                }
            });
            rg_trainer_trainee.addView(rb);
        }

    }

}
