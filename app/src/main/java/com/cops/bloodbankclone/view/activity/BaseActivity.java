package com.cops.bloodbankclone.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cops.bloodbankclone.view.fragment.BaseFragment;

import java.util.List;

public class BaseActivity extends AppCompatActivity {


    public BaseFragment baseFragment;

    public void superBackPressed() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        if(fragmentManager.getBackStackEntryCount() != 0) {
//            fragmentManager.popBackStack();
//
//        } else {
//            super.onBackPressed();
//        }
//
       super.onBackPressed();
    }

    @Override
    public void onBackPressed() {

        baseFragment.onBack();
    }
}
