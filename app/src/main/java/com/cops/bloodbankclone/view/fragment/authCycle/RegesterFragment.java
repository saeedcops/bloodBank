package com.cops.bloodbankclone.view.fragment.authCycle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.GeneralSpinnerAdapter;
import com.cops.bloodbankclone.data.local.SharedPreferencesManger;
import com.cops.bloodbankclone.data.model.login.Client;
import com.cops.bloodbankclone.data.model.login.Login;
import com.cops.bloodbankclone.data.model.register.RegisterData;
import com.cops.bloodbankclone.utility.DateTxt;
import com.cops.bloodbankclone.utility.HelperMethod;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
import static com.cops.bloodbankclone.utility.GeneralRequest.getBloodTypeSpinnerItems;
import static com.cops.bloodbankclone.utility.GeneralRequest.getCitySpinnerItems;
import static com.cops.bloodbankclone.utility.service.MyFirebaseMessagingService.getToken;

public class RegesterFragment extends BaseFragment {

    @BindView(R.id.register_fragment_sp_choose_blood_type)
    Spinner registerFragmentSpChooseBloodType;
    @BindView(R.id.register_fragment_sp_choose_government)
    Spinner registerFragmentSpChooseGovernment;
    @BindView(R.id.register_fragment_sp_choose_city)
    Spinner registerFragmentSpChooseCity;
    @BindView(R.id.register_fragment_til_choose_blood_type)
    TextInputLayout registerFragmentTilChooseBloodType;
    @BindView(R.id.register_fragment_til_choose_government)
    TextInputLayout registerFragmentTilChooseGovernment;
    @BindView(R.id.register_fragment_til_choose_city)
    TextInputLayout registerFragmentTilChooseCity;
    @BindView(R.id.register_fragment_til_birth_date)
    TextInputLayout registerFragmentTilBirthDate;
    @BindView(R.id.register_fragment_til_last_donation)
    TextInputLayout registerFragmentTilLastDonation;
    @BindView(R.id.register_fragment_til_name)
    TextInputLayout registerFragmentTilName;
    @BindView(R.id.register_fragment_til_email)
    TextInputLayout registerFragmentTilEmail;
    @BindView(R.id.register_fragment_til_phone)
    TextInputLayout registerFragmentTilPhone;
    @BindView(R.id.register_fragment_til_password)
    TextInputLayout registerFragmentTilPassword;
    @BindView(R.id.register_fragment_til_confirm_password)
    TextInputLayout registerFragmentTilConfirmPassword;
    DateTxt birthDay = new DateTxt("01", "11", "1990", "ss"), lastDonation = new DateTxt("11", "2", "2020", "ss");


    private RegisterData register = new RegisterData();
    private Client client = new Client();

    GeneralSpinnerAdapter spinnerGovernmentAdapter, spinnerBloodTypeAdapter, spinnerCityAdapter;
    private String token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_regester, container, false);
        ButterKnife.bind(this, view);


        spinnerGovernmentAdapter = new GeneralSpinnerAdapter(getActivity());
        spinnerBloodTypeAdapter = new GeneralSpinnerAdapter(getActivity());
        spinnerCityAdapter = new GeneralSpinnerAdapter(getActivity());

        getSpinner();

        return view;
    }

    private void getSpinner() {
        getCitySpinnerItems(getClient().getGovernment(), getActivity(), false, spinnerGovernmentAdapter, spinnerCityAdapter
                , registerFragmentSpChooseCity, registerFragmentSpChooseGovernment);
        getBloodTypeSpinnerItems(getClient().getBloodType(), getActivity(), false, spinnerBloodTypeAdapter, registerFragmentSpChooseBloodType);
        // startCall(getClient().getProfile("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl"), false);
    }




    @OnClick({R.id.fragment_register_btn_register, R.id.register_fragment_ll_sub_view, R.id.register_fragment_til_birth_date, R.id.register_fragment_til_last_donation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_btn_register:

                if (isEditTextSet(registerFragmentTilName, registerFragmentTilEmail, registerFragmentTilBirthDate,
                        registerFragmentTilLastDonation, registerFragmentTilPhone, registerFragmentTilPassword,
                        registerFragmentTilConfirmPassword) && isPhoneSet(registerFragmentTilPhone)
                        && isEmailValid(registerFragmentTilEmail) && isPasswordMatched(registerFragmentTilPassword, registerFragmentTilConfirmPassword)) {

                    getSignup();

                }

                break;
            case R.id.register_fragment_ll_sub_view:

                HelperMethod.disappearKeypad(getActivity(), view);
                break;

            case R.id.register_fragment_til_birth_date:
                HelperMethod.showCalender(getActivity(), "Select Date", registerFragmentTilBirthDate, birthDay);

                break;
            case R.id.register_fragment_til_last_donation:
                HelperMethod.showCalender(getActivity(), "Select Date", registerFragmentTilLastDonation, lastDonation);

                break;
        }
    }

    private void getSignup() {
        getClient().signup(registerFragmentTilName.getEditText().getText().toString(), registerFragmentTilEmail.getEditText().getText().toString(), registerFragmentTilBirthDate.getEditText().getText().toString(),
                String.valueOf(registerFragmentSpChooseCity.getSelectedItemPosition() + 1), registerFragmentTilPhone.getEditText().getText().toString(), registerFragmentTilLastDonation.getEditText().getText().toString(),
                registerFragmentTilPassword.getEditText().getText().toString(), registerFragmentTilConfirmPassword.getEditText().getText().toString(), String.valueOf(registerFragmentSpChooseBloodType.getSelectedItemPosition() + 1)).enqueue(
                new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        try {

                            if (response.body().getStatus() == 1) {

                                SaveData(getActivity(), "apiToken", response.body().getData().getApiToken());

                                SaveData(getActivity(), "phone", registerFragmentTilPhone.getEditText().getText().toString());
                                SaveData(getActivity(), "password", registerFragmentTilPassword.getEditText().getText().toString());


                                SaveData(getActivity(), "token", getToken());
                                Toast.makeText(getActivity(), "sent" + LoadData(getActivity(), "token"), Toast.LENGTH_SHORT).show();
                                 pushToken(getClient().pushToken(LoadData(getActivity(),"token"),LoadData(getActivity(),"apiToken"),"android"));
                                SaveData(getActivity(), "tokenSent", true);


                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.auth_activity_fl_frame, new LoginFragment())
                                        .addToBackStack(null).commit();


                            } else {

                                Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_LONG).show();
                                Log.i("XXX", response.body().getMsg());
                                Log.i("zzz", response.message());
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(baseActivity, "field", Toast.LENGTH_LONG).show();

                    }
                }
        );

    }

    private void pushToken(Call<Login> call) {
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                // Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    @Override
    public void onBack() {
        super.onBack();
    }

}
