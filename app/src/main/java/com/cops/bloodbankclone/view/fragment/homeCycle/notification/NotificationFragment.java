package com.cops.bloodbankclone.view.fragment.homeCycle.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.NotificationAdapter;
import com.cops.bloodbankclone.data.model.notification.Notification;
import com.cops.bloodbankclone.data.model.notification.NotificationData;
import com.cops.bloodbankclone.utility.OnEndLess;
import com.cops.bloodbankclone.view.activity.HomeActivity;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.HomeFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.donation.DonationFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;

public class NotificationFragment extends BaseFragment {


    @BindView(R.id.notification_fragment_rv)
    RecyclerView notificationFragmentRv;
    @BindView(R.id.notification_fragment_btn_donate)
    Button notificationFragmentBtnDonate;
    @BindView(R.id.notification_fragment_ll_no_notification)
    LinearLayout notificationFragmentLlNoNotification;
    private LinearLayoutManager layoutManager;
    private NotificationAdapter notificationAdapter;
    private List<NotificationData> notificationData = new ArrayList<>();
    private OnEndLess onEndLess;
    private int maxPage = 0;

    public NotificationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.notification_fragment_toolbar);
        if(Locale.getDefault().getLanguage()=="en")

            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        else

            toolbar.setNavigationIcon(R.drawable.ic_arrow_forward_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBack();

            }
        });

        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        layoutManager = new LinearLayoutManager(getActivity());
        notificationFragmentRv.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {

                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        getNotification(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        notificationFragmentRv.addOnScrollListener(onEndLess);
        notificationAdapter = new NotificationAdapter(getActivity(), notificationData);
        notificationFragmentRv.setAdapter(notificationAdapter);

        getNotification(1);
    }

    private void getNotification(int page) {

        getClient().getNotification(LoadData(getActivity(), "apiToken"), page).enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {

                try {

                    if (response.body().getStatus() == 1 && response.body().getData().getFrom() != null) {
                        notificationFragmentLlNoNotification.setVisibility(View.GONE);
                        notificationFragmentRv.setVisibility(View.VISIBLE);
                        maxPage = response.body().getData().getLastPage();
                        notificationData.addAll(response.body().getData().getData());
                        notificationAdapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBack() {

        super.onBack();

    }

    @OnClick(R.id.notification_fragment_btn_donate)
    public void onViewClicked() {

        Bundle bundle=new Bundle();
        bundle.putString("key","coming from notification");
        HomeFragment homeFragment=new HomeFragment();
        homeFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_activity_fl_frame, homeFragment)
                .addToBackStack(null).commit();
    }
}
