package com.cops.bloodbankclone.view.fragment.homeCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.ViewPagerWithFragmentAdapter;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.donation.DonationFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.post.PostFragment;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends BaseFragment {
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home,container,false);
        initFragment();
        //       initializing TabLayout and setting it up with ViewPager
        viewPager=(ViewPager)view.findViewById(R.id.home_fragment_vp_tab);

       if (viewPager != null){
            setUpViewPager(viewPager);
        }
        if (getArguments()!=null) {
            viewPager.setCurrentItem(1);

        }
            TabLayout tabLayout=(TabLayout)view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //     this is a tab selected listener to move the user to the selected tab
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //here we get the selected tab and set it to a current view
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onBack() {

        super.onBack();
    }

    private void setUpViewPager(ViewPager viewPager){

        ViewPagerWithFragmentAdapter adapter=new ViewPagerWithFragmentAdapter(getChildFragmentManager());
        adapter.addPager(new PostFragment(),getString(R.string.post));
        adapter.addPager(new DonationFragment(),getString(R.string.donation_request));
        viewPager.setAdapter(adapter);
    }
}
