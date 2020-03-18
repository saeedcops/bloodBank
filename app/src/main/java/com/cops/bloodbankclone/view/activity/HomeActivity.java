package com.cops.bloodbankclone.view.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.view.fragment.homeCycle.HomeFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.more.MoreItemFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.notification.NotificationFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.donation.RequestDonationFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends BaseActivity {
    ViewPager viewPager;
    public static FloatingActionButton floatingActionButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_home);

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }


        bottomNavigationView=(BottomNavigationView)findViewById(R.id.home_activity_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_fl_frame, new HomeFragment()).commit();

        floatingActionButton=(FloatingActionButton)findViewById(R.id.request_donation);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionButton.setVisibility(View.INVISIBLE);
               getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_activity_fl_frame, new RequestDonationFragment()).commit();
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {


                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selected=null;

                    switch (item.getItemId()){

                        case R.id.home_item:
                            selected=new HomeFragment();
                            break;

                        case R.id.user_item:
                            selected=new UserFragment();
//                            floatingActionButton.setVisibility(View.INVISIBLE);
                            break;

                        case R.id.notification_item:
                            selected=new NotificationFragment();
//                            floatingActionButton.setVisibility(View.INVISIBLE);
                            break;

                        case R.id.more_item:
                            //MoreItemFragment()
                            selected=new MoreItemFragment();
//                            floatingActionButton.setVisibility(View.INVISIBLE);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.home_activity_fl_frame,selected).commit();

                    return true;
                }
            };

}
