package com.cops.bloodbankclone.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.local.SharedPreferencesManger;
import com.cops.bloodbankclone.view.fragment.homeCycle.notification.NotificationSettingFragment;
import com.cops.bloodbankclone.view.fragment.splashCycle.SliderFragment;
import com.cops.bloodbankclone.view.fragment.splashCycle.SplashFragment;

public class SplashActivity extends BaseActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, new SplashFragment())
                .addToBackStack(null)
                .commit();

        if(SharedPreferencesManger.LoadBoolean(this,"opend")){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(getApplicationContext(), AuthActivity.class);
                    startActivity(intent);
                }
            }, 1000);

        }else {
            SharedPreferencesManger.SaveData(this,"opend",true);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, new SliderFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }, 1000);


        }
    }

}