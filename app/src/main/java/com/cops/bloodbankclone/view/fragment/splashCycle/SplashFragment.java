package com.cops.bloodbankclone.view.fragment.splashCycle;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.local.SharedPreferencesManger;
import com.cops.bloodbankclone.view.activity.AuthActivity;
import com.cops.bloodbankclone.view.fragment.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends BaseFragment {

    Handler handler = new Handler();
    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initFragment();
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onBack() {
        baseActivity.finishAffinity();
    }

}
