package com.example.fitnessapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.fitnessapplication.MainActivity;
import com.example.fitnessapplication.R;
import com.example.fitnessapplication.fragment.LoginFragment;
import com.example.fitnessapplication.fragment.RegistrationFragment;
import com.example.fitnessapplication.fragment.trainee.TraineeHomeFragment;
import com.example.fitnessapplication.fragment.trainee.TraineeMuscleGroupExercisesFragment;
import com.example.fitnessapplication.fragment.trainee.TraineeMuscleGroupsFragment;
import com.example.fitnessapplication.fragment.trainee.TraineeSettingsFragment;
import com.example.fitnessapplication.fragment.trainee.TraineeTrainerMyContentFragment;
import com.example.fitnessapplication.fragment.trainee.TraineeTrainersFragment;
import com.example.fitnessapplication.fragment.trainer.TrainerMyContentFragment;
import com.example.fitnessapplication.fragment.trainer.TrainerOptionsFragment;

public class FragmentNavigation extends Fragment {
    public final static String TAG = FragmentNavigation.class.getSimpleName();

    private static FragmentNavigation sInstance;
    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransaction;
    private static Handler mHandler = new Handler();
    private static int mMainActivityFragmentContainer;
    private static boolean mDoubleBackToExitPressedOnce = false;
    private Activity act;

    public static FragmentNavigation getInstance(Context context) {

        if (sInstance == null) {
            mMainActivityFragmentContainer = R.id.content_fragment;
            sInstance = new FragmentNavigation();
            mFragmentManager = ((MainActivity) context).getSupportFragmentManager();
        }
        return sInstance;
    }

    private void addFragment(Fragment fragment, int container) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(container, fragment, fragment.getTag());
        mFragmentTransaction.addToBackStack(null);
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(Fragment fragment, int container) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment topFragment = mFragmentManager.findFragmentById(container);
        if (topFragment == null) {
            // if there is nothing to replace, then add a new one:
            addFragment(fragment, container);
        } else {
            // if there is fragment to replace, then replace it:
            mFragmentTransaction.replace(container, fragment, fragment.getTag());
            mFragmentTransaction.addToBackStack(null);
            try {
                mFragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Fragment getCurrentFragment(int container) {
        return mFragmentManager.findFragmentById(container);
    }

    public void onBackPressed(MainActivity activity) {

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof LoginFragment) {
            doublePressExit(activity);
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof TraineeMuscleGroupExercisesFragment) {
            popBackstack();
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof RegistrationFragment) {
            popBackstack();
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof TraineeHomeFragment) {
            popBackstack();
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof TraineeMuscleGroupsFragment) {
            popBackstack();
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof TraineeSettingsFragment) {
            popBackstack();
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof TraineeTrainerMyContentFragment) {
            popBackstack();
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof TraineeTrainersFragment) {
            popBackstack();
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof TrainerMyContentFragment) {
            popBackstack();
            return;
        }

        if (getCurrentFragment(mMainActivityFragmentContainer) instanceof TrainerOptionsFragment) {
            popBackstack();
            return;
        }

        // Other cases:
        activity.moveTaskToBack(true);
    }

    private void doublePressExit(MainActivity activity) {

        if (mDoubleBackToExitPressedOnce) {
            mDoubleBackToExitPressedOnce = false;
            activity.moveTaskToBack(true);
            return;
        }

        mDoubleBackToExitPressedOnce = true;
        Toast.makeText(activity, R.string.back_button_press, Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDoubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void popBackstack() {
        mFragmentManager.popBackStack();
    }
}
