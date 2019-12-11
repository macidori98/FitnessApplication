package com.example.fitnessapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.fitnessapplication.Fragment.LoginFragment;
import com.example.fitnessapplication.Utils.FragmentNavigation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentNavigation.getInstance(this).replaceFragment(new LoginFragment(), R.id.content_fragment);
    }

    @Override
    public void onBackPressed() {
        FragmentNavigation.getInstance(getApplicationContext()).onBackPressed(this);
    }
}
