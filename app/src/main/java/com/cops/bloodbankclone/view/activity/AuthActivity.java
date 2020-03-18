package com.cops.bloodbankclone.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.ViewPagerWithFragmentAdapter;
import com.cops.bloodbankclone.view.fragment.authCycle.ForgetPasswordFragment;
import com.cops.bloodbankclone.view.fragment.authCycle.LoginFragment;
import com.cops.bloodbankclone.view.fragment.authCycle.RegesterFragment;
import com.cops.bloodbankclone.view.fragment.authCycle.ValidationFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.HomeFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;

public class AuthActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.auth_activity_fl_frame, new LoginFragment()).commit();

    }

}
