package com.cops.bloodbankclone.view.fragment.homeCycle.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.login.Login;
import com.cops.bloodbankclone.view.activity.AuthActivity;
import com.cops.bloodbankclone.view.activity.HomeActivity;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.cops.bloodbankclone.view.fragment.authCycle.LoginFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.notification.NotificationSettingFragment;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.clean;

public class MoreItemFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_more_item, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.more_fragment_toolbar);
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


        return view;
    }


    @Override
    public void onBack() {
        HomeActivity.floatingActionButton.setVisibility(View.VISIBLE);

       super.onBack();
    }

    @OnClick({R.id.more_fragment_favorite, R.id.more_fragment_contact_us, R.id.more_fragment_about, R.id.more_fragment_rate, R.id.more_fragment_notification_setting, R.id.more_fragment_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_fragment_favorite:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_activity_fl_frame, new FavoriteFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.more_fragment_contact_us:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_activity_fl_frame, new ContactUsFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.more_fragment_about:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_activity_fl_frame, new AboutFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.more_fragment_rate:
                break;
            case R.id.more_fragment_notification_setting:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_activity_fl_frame, new NotificationSettingFragment())
                        .addToBackStack(null).commit();
                break;
            case R.id.more_fragment_logout:
                removeToken(getClient().removeToken(LoadData(getActivity(),"token"),LoadData(getActivity(),"apiToken")));

                clean(getActivity());

                startActivity(new Intent(getActivity(), AuthActivity.class));

                break;
        }
    }

    private void removeToken(Call<Login> call) {
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                try {
                    if (response.body().getStatus()==1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }else {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }


                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
