package com.cops.bloodbankclone.view.fragment.authCycle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.local.SharedPreferencesManger;
import com.cops.bloodbankclone.data.model.login.Login;
import com.cops.bloodbankclone.utility.HelperMethod;
import com.cops.bloodbankclone.view.activity.HomeActivity;
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
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadBoolean;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.SaveData;
import static com.cops.bloodbankclone.utility.CheckInput.isEditTextSet;
import static com.cops.bloodbankclone.utility.CheckInput.isPhoneSet;
import static com.cops.bloodbankclone.utility.service.MyFirebaseMessagingService.getToken;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.forgetPasswordTV)
    TextView forgetPasswordTV;
    @BindView(R.id.frag_login_create_accountTV)
    TextView fragLoginCreateAccountTV;
    @BindView(R.id.login_fragment_checkbox_rem)
    CheckBox loginFragmentCheckboxRem;
    @BindView(R.id.login_fragment_til_phone)
    TextInputLayout loginFragmentTilPhone;
    @BindView(R.id.login_fragment_til_password)
    TextInputLayout loginFragmentTilPassword;
    String token;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_login, container, false);


        ButterKnife.bind(this, view);
        if (LoadData(getActivity(), "phone") != null && LoadData(getActivity(), "password") != null) {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            // startActivity(intent);
            loginFragmentTilPhone.getEditText().setText(LoadData(getActivity(), "phone"));
            loginFragmentTilPassword.getEditText().setText(LoadData(getActivity(), "password"));
        }


        return view;
    }



    @OnClick({R.id.forgetPasswordTV, R.id.login_btn, R.id.frag_login_create_accountTV, R.id.login_fragment_ll_parent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_fragment_ll_parent:
                HelperMethod.disappearKeypad(getActivity(), view);
                break;
            case R.id.forgetPasswordTV:
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.auth_activity_fl_frame, new ForgetPasswordFragment()).commit();
                // ((AuthActivity) getActivity()).setViewPager(2);

                break;
            case R.id.login_btn:

                if (isEditTextSet(loginFragmentTilPhone, loginFragmentTilPassword) && isPhoneSet(loginFragmentTilPhone)) {
                    //  Toast.makeText(baseActivity, loginFragmentTilPhone.getEditText().getText().toString(), Toast.LENGTH_SHORT).show();

                    startLogin(getClient().login(loginFragmentTilPhone.getEditText().getText().toString(), loginFragmentTilPassword.getEditText().getText().toString()));


                }
                break;
            case R.id.frag_login_create_accountTV:
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.auth_activity_fl_frame, new RegesterFragment()).commit();
                // ((AuthActivity) getActivity()).setViewPager(1);
                break;
        }
    }


    private void startLogin(Call<Login> call) {

        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                try {
                    //
                    if (response.body().getStatus() == 1) {
                        // Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        SaveData(getActivity(), "apiToken", response.body().getData().getApiToken());

                        if (!SharedPreferencesManger.LoadBoolean(getActivity(), "tokenSent")) {
                            SharedPreferencesManger.SaveData(getActivity(), "tokenSent", true);
                            String tok = getToken();

                            SaveData(getActivity(), "token", tok);
                            //Toast.makeText(getActivity(), "sent" +LoadData(getActivity(),"token"), Toast.LENGTH_SHORT).show();
                            pushToken(getClient().pushToken( LoadData(getActivity(), "token"),LoadData(getActivity(), "apiToken"), "android"));
                        }

                       Log.i("token",LoadData(getActivity(),"token"));
                       Log.i("apiToken",LoadData(getActivity(),"apiToken"));


                        if (loginFragmentCheckboxRem.isChecked()) {
                            SaveData(getActivity(), "phone", loginFragmentTilPhone.getEditText().getText().toString());
                            SaveData(getActivity(), "password", loginFragmentTilPassword.getEditText().getText().toString());
                        }


                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("xxxx", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void pushToken(Call<Login> call) {
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                try {


                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("xxxx",e.getMessage());

                }


            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBack() {

        baseActivity.finishAffinity();
    }
}
