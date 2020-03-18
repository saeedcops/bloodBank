package com.cops.bloodbankclone.view.fragment.authCycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.newPassword.NewPassword;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.utility.CheckInput.isEditTextSet;

public class ValidationFragment extends BaseFragment {

    @BindView(R.id.fragment_validation_til_code)
    TextInputLayout fragmentValidationTilCode;
    @BindView(R.id.fragment_validation_til_password)
    TextInputLayout fragmentValidationTilPassword;
    @BindView(R.id.fragment_validation_til_retype_password)
    TextInputLayout fragmentValidationTilRetypePassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_validation, container, false);

        ButterKnife.bind(this,view);

        return view;
    }


    @OnClick(R.id.fragment_validation_change_btn)
    public void onViewClicked() {
        if (isEditTextSet(fragmentValidationTilCode,fragmentValidationTilPassword,fragmentValidationTilRetypePassword)) {
            reNewPassword(getClient().newPassword(
                    fragmentValidationTilPassword.getEditText().getText().toString(),
                    fragmentValidationTilRetypePassword.getEditText().getText().toString(),
                    fragmentValidationTilCode.getEditText().getText().toString(),
                    ForgetPasswordFragment.phone));
        }
    }

    private void reNewPassword(Call<NewPassword> call) {
        call.enqueue(new Callback<NewPassword>() {
            @Override
            public void onResponse(Call<NewPassword> call, Response<NewPassword> response) {
                if (response.body().getStatus()==1) {

                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    getChildFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.auth_activity_fl_frame, new LoginFragment()).commit();
                    //((AuthActivity) getActivity()).setViewPager(0);
                }
                else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewPassword> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onBack() {

        super.onBack();
    }

}
