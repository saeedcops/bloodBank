package com.cops.bloodbankclone.view.fragment.authCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.resetPassword.ResetPassword;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.utility.CheckInput.isPhoneSet;

public class ForgetPasswordFragment extends BaseFragment {

    @BindView(R.id.fragment_forget_password_til)
     TextInputLayout fragmentForgetPasswordTil;
    public static String phone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        ButterKnife.bind(this,view);




        return view;
    }



    @OnClick(R.id.fragment_forget_password_send_btn)
    public void onViewClicked() {

        if (isPhoneSet(fragmentForgetPasswordTil)) {
            getClient().resetPassword(fragmentForgetPasswordTil.getEditText().getText().toString()).enqueue(new Callback<ResetPassword>() {
                @Override
                public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                    try {

                        if (response.body().getStatus()==1) {
                            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.auth_activity_fl_frame, new ValidationFragment()).commit();
                            //((AuthActivity) getActivity()).setViewPager(3);
                            phone=fragmentForgetPasswordTil.getEditText().getText().toString();
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(baseActivity, response.message(), Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<ResetPassword> call, Throwable t) {

                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    @Override
    public void onBack() {

        super.onBack();
    }
}
