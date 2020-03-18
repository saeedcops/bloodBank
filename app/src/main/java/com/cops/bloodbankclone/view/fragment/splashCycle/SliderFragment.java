package com.cops.bloodbankclone.view.fragment.splashCycle;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.SlideAdapter;
import com.cops.bloodbankclone.view.activity.AuthActivity;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.google.android.material.tabs.TabLayout;

public class SliderFragment extends BaseFragment {

    ViewPager viewPager;
    SlideAdapter adapter;
    TabLayout dot;
    ImageButton btnNext;
    int position = 0 ;
    Handler handler = new Handler();


    public SliderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initFragment();
        View view=inflater.inflate(R.layout.fragment_slider, container, false);
        btnNext=view.findViewById(R.id.slider_fragment_btn_next);
        dot=view.findViewById(R.id.slider_fragment_tab_indicator);




        viewPager=view.findViewById(R.id.fragment_slider_viewPager);
        adapter=new SlideAdapter(getActivity());
        viewPager.setAdapter(adapter);

        dot.setupWithViewPager(viewPager);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = viewPager.getCurrentItem();
                if (position < 2) {
                    position++;
                    viewPager.setCurrentItem(position);

                }else{
                    Intent intent=new Intent(getActivity(), AuthActivity.class);
                    startActivity(intent);
                }
            }
        });

        dot.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition()==2){
                    btnNext.setImageResource(R.drawable.ic_check_black_24dp);
                }else {
                    btnNext.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
                }

                if(tab.getPosition()>2){
                    Intent intent=new Intent(getActivity(), AuthActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab.setIcon(R.drawable.shape_circle_white);



            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }
    @Override
    public void onBack() {
        baseActivity.finishAffinity();
    }

}
