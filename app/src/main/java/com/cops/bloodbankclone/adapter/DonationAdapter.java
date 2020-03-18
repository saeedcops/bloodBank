package com.cops.bloodbankclone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.donation.DonationData;
import com.cops.bloodbankclone.utility.HelperMethod;
import com.cops.bloodbankclone.view.activity.BaseActivity;
import com.cops.bloodbankclone.view.fragment.homeCycle.donation.DonationFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.donation.DonationRequestFromFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.more.AboutFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {


    private Context context;
    private BaseActivity activity;
    private List<DonationData> donationDataList = new ArrayList<>();

    public DonationAdapter(Activity activity, List<DonationData> donationDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.donationDataList = donationDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donation,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        setData(holder, position);
       // setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        holder.position=position;
        holder.donationAdapterTvName.setText("Patient"+" : "+ donationDataList.get(position).getClient().getName());
        holder.donationAdapterTvBloodHospital.setText("Hospital"+" : "+ donationDataList.get(position).getHospitalAddress());
        holder.donationAdapterTvBloodCity.setText("City"+" : "+ donationDataList.get(position).getCity().getName());
        holder.donationAdapterTvBloodType.setText( donationDataList.get(position).getBloodType().getName());

    }

//    private void setAction(ViewHolder holder, int position) {
//
//
//    }

    @Override
    public int getItemCount() {
        return donationDataList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.donation_adapter_tv_name)
        TextView donationAdapterTvName;
        @BindView(R.id.donation_adapter_tv_blood_hospital)
        TextView donationAdapterTvBloodHospital;
        @BindView(R.id.donation_adapter_tv_blood_city)
        TextView donationAdapterTvBloodCity;
        @BindView(R.id.donation_adapter_tv_blood_type)
        TextView donationAdapterTvBloodType;
        private View view;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            ButterKnife.bind(this,view);

        }

        @OnClick({R.id.donation_adapter_iv_info, R.id.donation_adapter_iv_call})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.donation_adapter_iv_info:
                    Bundle bundle = new Bundle();
                    bundle.putInt("donationId", donationDataList.get(position).getId());
                    DonationRequestFromFragment don=new DonationRequestFromFragment();
                    don.setArguments(bundle);
                   activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.home_activity_fl_frame, don)
                            .addToBackStack(null).commit();
                    break;
                case R.id.donation_adapter_iv_call:
                    HelperMethod.onPermission(activity);
                    activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",donationDataList.get(position).getPhone(),"")));
                    break;
            }
        }
    }
}
