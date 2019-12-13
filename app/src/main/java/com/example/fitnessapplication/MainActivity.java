package com.example.fitnessapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessapplication.fragment.LoginFragment;
import com.example.fitnessapplication.utils.FragmentNavigation;

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
