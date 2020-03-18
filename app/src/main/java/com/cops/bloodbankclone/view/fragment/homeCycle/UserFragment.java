package com.cops.bloodbankclone.view.fragment.homeCycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.GeneralSpinnerAdapter;
import com.cops.bloodbankclone.data.model.login.Client;
import com.cops.bloodbankclone.data.model.profile.Profile;
import com.cops.bloodbankclone.utility.DateTxt;
import com.cops.bloodbankclone.utility.HelperMethod;
import com.cops.bloodbankclone.view.activity.HomeActivity;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.cops.bloodbankclone.view.fragment.authCycle.LoginFragment;
import com.google.android.material.textfield.TextInputLayout;

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
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.SaveData;
import static com.cops.bloodbankclone.utility.CheckInput.isEditTextSet;
import static com.cops.bloodbankclone.utility.CheckInput.isEmailValid;
import static com.cops.bloodbankclone.utility.CheckInput.isPasswordMatched;
import static com.cops.bloodbankclone.utility.CheckInput.isPhoneSet;
import static com.cops.bloodbankclone.utility.GeneralRequest.getSpinnerData;

public class UserFragment extends BaseFragment {

    @BindView(R.id.register_fragment_til_name)
    TextInputLayout registerFragmentTilName;
    @BindView(R.id.register_fragment_til_email)
    TextInputLayout registerFragmentTilEmail;
    @BindView(R.id.register_fragment_til_last_donation)
    TextInputLayout registerFragmentTilLastDonation;
    //@BindView(R.id.register_fragment_til_birth_date)
    // TextInputLayout registerFragmentTilBirthDate;
    @BindView(R.id.register_fragment_til_choose_blood_type)
    TextInputLayout registerFragmentTilChooseBloodType;
    @BindView(R.id.register_fragment_til_choose_government)
    TextInputLayout registerFragmentTilChooseGovernment;
    @BindView(R.id.register_fragment_til_choose_city)
    TextInputLayout registerFragmentTilChooseCity;
    @BindView(R.id.register_fragment_til_phone)
    TextInputLayout registerFragmentTilPhone;
    @BindView(R.id.register_fragment_til_password)
    TextInputLayout registerFragmentTilPassword;
    @BindView(R.id.register_fragment_til_confirm_password)
    TextInputLayout registerFragmentTilConfirmPassword;
    @BindView(R.id.register_fragment_sp_choose_city)
    Spinner registerFragmentSpChooseCity;
    @BindView(R.id.register_fragment_sp_choose_government)
    Spinner registerFragmentSpChooseGovernment;
    @BindView(R.id.register_fragment_sp_choose_blood_type)
    Spinner registerFragmentSpChooseBloodType;
    DateTxt birthDay = new DateTxt("01", "11", "1990", "ss"), lastDonation = new DateTxt("11", "22", "22", "22");
    Unbinder unbinder;

    GeneralSpinnerAdapter spinnerGovernmentAdapter, spinnerBloodTypeAdapter, spinnerCityAdapter;
    @BindView(R.id.register_fragment_til_birth_date)
    TextInputLayout registerFragmentTilBirthDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.register_fragment_toolbar);
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

        getProfile();
        return view;
    }

    private void getProfile() {
        startCall(getClient().getProfile(LoadData(getActivity(), "apiToken")), false);
    }

    private void startCall(Call<Profile> call, boolean edit) {

        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

                try {
                    if (response.body().getStatus() == 1) {

                        if (!edit) {

                            setData(response.body().getData().getClient());
                            spinnerGovernmentAdapter = new GeneralSpinnerAdapter(getActivity());
                            spinnerBloodTypeAdapter = new GeneralSpinnerAdapter(getActivity());
                            spinnerCityAdapter = new GeneralSpinnerAdapter(getActivity());

                            getSpinnerData(getClient().getBloodType(), registerFragmentSpChooseBloodType, spinnerBloodTypeAdapter,
                                    response.body().getData().getClient().getBloodType().getId(), getString(R.string.blood_types));
                            AdapterView.OnItemSelectedListener governmentItemSelectedListener = new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (position != 0) {
                                        getSpinnerData(getClient().getCity(position), registerFragmentSpChooseCity, spinnerCityAdapter,
                                                response.body().getData().getClient().getBloodType().getId(), getString(R.string.city));
                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            };

                            getSpinnerData(getClient().getGovernment(), registerFragmentSpChooseGovernment, spinnerGovernmentAdapter,
                                    response.body().getData().getClient().getCity().getGovernorate().getId(), getString(R.string.governments),governmentItemSelectedListener);


                        }
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    private void setData(Client client) {

        registerFragmentTilName.getEditText().setText(client.getName());
        registerFragmentTilEmail.getEditText().setText(client.getEmail());
        registerFragmentTilLastDonation.getEditText().setText(client.getDonationLastDate());
         registerFragmentTilBirthDate.getEditText().setText(client.getBirthDate());
        registerFragmentTilPhone.getEditText().setText(client.getPhone());
        registerFragmentTilPassword.getEditText().setText(LoadData(getActivity(),"password"));
        registerFragmentTilConfirmPassword.getEditText().setText(LoadData(getActivity(),"password"));
    }



    @OnClick({R.id.register_fragment_til_last_donation, R.id.register_fragment_til_birth_date, R.id.fragment_register_btn_register, R.id.register_fragment_ll_sub_view})
    public void onViewClicked(View view) {
        HelperMethod.disappearKeypad(getActivity(), view);
        switch (view.getId()) {
            case R.id.register_fragment_til_last_donation:
                HelperMethod.showCalender(getActivity(), "Select Date", registerFragmentTilLastDonation, lastDonation);
                break;
            case R.id.register_fragment_til_birth_date:
                HelperMethod.showCalender(getActivity(), "Select Date", registerFragmentTilBirthDate, birthDay);
                break;
            case R.id.fragment_register_btn_register:
                if (isEditTextSet(registerFragmentTilName,registerFragmentTilEmail,registerFragmentTilLastDonation,registerFragmentTilBirthDate,
                                  registerFragmentTilPhone,registerFragmentTilPassword,registerFragmentTilConfirmPassword)
                        && registerFragmentSpChooseBloodType.getSelectedItemPosition()!=0 && registerFragmentSpChooseGovernment.getSelectedItemPosition()!=0
                        && registerFragmentSpChooseCity.getSelectedItemPosition()!=0 && isPhoneSet(registerFragmentTilPhone)
                        && isEmailValid(registerFragmentTilEmail) && isPasswordMatched(registerFragmentTilPassword,registerFragmentTilConfirmPassword)) {


                    setUserProfile(getClient().setProfile(registerFragmentTilName.getEditText().getText().toString(),registerFragmentTilEmail.getEditText().getText().toString(),
                            registerFragmentTilBirthDate.getEditText().getText().toString(),String.valueOf(registerFragmentSpChooseCity.getSelectedItemPosition()),
                            registerFragmentTilPhone.getEditText().getText().toString(),registerFragmentTilLastDonation.getEditText().getText().toString(),
                            registerFragmentTilPassword.getEditText().getText().toString(),registerFragmentTilConfirmPassword.getEditText().getText().toString(),
                            String.valueOf(registerFragmentSpChooseBloodType.getSelectedItemPosition()),LoadData(getActivity(),"apiToken")));

//                    Toast.makeText(baseActivity, "ok", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(baseActivity, "Please fill all field", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.register_fragment_ll_sub_view:
                break;
        }
    }

    private void setUserProfile(Call<Profile> call) {
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

                if (response.body().getStatus()==1) {

                    Toast.makeText(getActivity(), "Data set :)", Toast.LENGTH_SHORT).show();

                    SaveData(getActivity(), "phone", registerFragmentTilPhone.getEditText().getText().toString());
                    SaveData(getActivity(), "password", registerFragmentTilPassword.getEditText().getText().toString());

                 startActivity(new Intent(getActivity(),HomeActivity.class));

                }else
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    @OnClick(R.id.register_fragment_til_birth_date)
    public void onViewClicked() {
    }

    @Override
    public void onBack() {

        super.onBack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
