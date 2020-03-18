package com.cops.bloodbankclone.view.fragment.homeCycle.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.NotificationSettingAdapter;
import com.cops.bloodbankclone.data.model.generalResponce.GeneralResponce;
import com.cops.bloodbankclone.data.model.notificationSetting.NotificationSetting;
import com.cops.bloodbankclone.data.model.notificationSetting.NotificationSettingData;
import com.cops.bloodbankclone.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;

public class NotificationSettingFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.notification_setting_fragment_rl_blood_type_rv)
    RelativeLayout notificationSettingFragmentRlBloodTypeRv;
    @BindView(R.id.notification_setting_fragment_rl_blood_type_iv)
    ImageView notificationSettingFragmentRlBloodTypeIv;
    @BindView(R.id.notification_setting_fragment_rv_government)
    RecyclerView notificationSettingFragmentRvGovernment;
    @BindView(R.id.notification_setting_fragment_rl_government_iv)
    ImageView notificationSettingFragmentRlGovernmentIv;
    @BindView(R.id.notification_setting_fragment_rl_blood_type_tv_iv)
    RelativeLayout notificationSettingFragmentRlBloodTypeTvIv;
    @BindView(R.id.notification_setting_fragment_rl_government_rv)
    RelativeLayout notificationSettingFragmentRlGovernmentRv;
    @BindView(R.id.notification_setting_fragment_rl_government_tv_iv)
    RelativeLayout notificationSettingFragmentRlGovernmentTvIv;
    @BindView(R.id.notification_setting_fragment_rv_blood_type)
    RecyclerView notificationSettingFragmentRvBloodType;
    private List<String> bloodTypes = new ArrayList<>(), governorates = new ArrayList<>();
    private NotificationSettingAdapter bloodAdapter,governmentAdapter;
    NotificationSettingData notificationSettingData =new NotificationSettingData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_notification_setting, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.notification_setting_fragment_toolbar);
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



        unbinder = ButterKnife.bind(this, view);
        initRv();
        getNotificationSetting();
        return view;
    }

    private void initRv() {
        notificationSettingFragmentRvGovernment.setLayoutManager(new GridLayoutManager(getContext(), 3));
        notificationSettingFragmentRvBloodType.setLayoutManager(new GridLayoutManager(getContext(),3));

    }

    private void getNotificationSetting() {
        getClient().getNotificationsSetting(LoadData(getActivity(),"apiToken")).enqueue(new Callback<NotificationSetting>() {
            @Override
            public void onResponse(Call<NotificationSetting> call, Response<NotificationSetting> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        bloodTypes = response.body().getData().getBloodTypes();
                        governorates = response.body().getData().getGovernorates();
                        //Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_LONG).show();
                        getBloodType();
                        getGovernorate();
                    }


                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<NotificationSetting> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_LONG).show();
               // Log.i("xxxx",t.getMessage());

            }
        });
    }

    private void getBloodType() {
        getClient().getBloodType().enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {
                try {
                    bloodAdapter=new NotificationSettingAdapter(getActivity(),getActivity(),response.body().getData(),bloodTypes);
                    notificationSettingFragmentRvBloodType.setAdapter(bloodAdapter);
                   // Toast.makeText(baseActivity, "blood data", Toast.LENGTH_LONG).show();

                }catch (Exception e){}

            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void getGovernorate() {
        getClient().getGovernment().enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {

                try {
                    governmentAdapter=new NotificationSettingAdapter(getActivity(),getActivity(),response.body().getData(),governorates);
                    notificationSettingFragmentRvGovernment.setAdapter(governmentAdapter);

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

            }
        });
    }

    @OnClick({R.id.notification_setting_fragment_rl_blood_type_iv, R.id.notification_setting_fragment_rl_government_iv, R.id.notification_setting_fragment_btn_save, R.id.notification_setting_fragment_rl_sub_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.notification_setting_fragment_rl_blood_type_iv:
                visable(notificationSettingFragmentRlBloodTypeRv, notificationSettingFragmentRlBloodTypeIv);
                break;
            case R.id.notification_setting_fragment_rl_government_iv:
                visable(notificationSettingFragmentRlGovernmentRv, notificationSettingFragmentRlGovernmentIv);
                break;
            case R.id.notification_setting_fragment_btn_save:
                onCall(true);
                break;
            case R.id.notification_setting_fragment_rl_sub_view:
                break;
        }
    }


    private void visable(View view, ImageView imageView) {

        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.ic_remove_black_24dp);
        } else {
            view.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.ic_add_white_24dp);

        }
    }

    private void onCall(final boolean state){


        getClient().setNotificationsSetting(LoadData(getActivity(),"apiToken"),
                governmentAdapter.newID,bloodAdapter.newID).enqueue(new Callback<NotificationSetting>() {
            @Override
            public void onResponse(Call<NotificationSetting> call, Response<NotificationSetting> response) {
                try {

                    if (response.body().getStatus() == 1) {

                     // response.body().getData().notify();
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }else {

                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<NotificationSetting> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_LONG).show();
                //Log.i("xxxx",t.getMessage());

            }
        });


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}
