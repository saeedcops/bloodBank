package com.cops.bloodbankclone.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.notification.NotificationData;
import com.cops.bloodbankclone.view.activity.BaseActivity;
import com.cops.bloodbankclone.view.fragment.homeCycle.donation.DonationRequestFromFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {



    private Context context;
    private BaseActivity activity;
    private List<NotificationData> notificationData = new ArrayList<>();

    public NotificationAdapter(Activity activity, List<NotificationData> donationDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.notificationData = donationDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        if (notificationData.get(position).getPivot().getIsRead().equals("0")) {
            holder.notificationFragmentItemIvNotify.setImageResource(R.drawable.ic_notifications_black_24dp);
        }else {
            holder.notificationFragmentItemIvNotify.setImageResource(R.drawable.ic_notifications_none_black_24dp);

        }
        holder.notificationFragmentItemTvNotify.setText(notificationData.get(position).getTitle());
        holder.notificationFragmentItemTvTime.setText(notificationData.get(position).getCreatedAt());

    }

    private void setAction(ViewHolder holder,int pos) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("donationId", notificationData.get(pos).getId());
                DonationRequestFromFragment don=new DonationRequestFromFragment();
                don.setArguments(bundle);
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_activity_fl_frame, don)
                        .addToBackStack(null).commit();

            }
        });
    }



    @Override
    public int getItemCount() {
        return notificationData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.notification_fragment_item_iv_notify)
        ImageView notificationFragmentItemIvNotify;
        @BindView(R.id.notification_fragment_item_tv_notify)
        TextView notificationFragmentItemTvNotify;
        @BindView(R.id.notification_fragment_item_tv_time)
        TextView notificationFragmentItemTvTime;
        private View view;
        private int position;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }

    }
}
