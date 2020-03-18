package com.cops.bloodbankclone.view.fragment.homeCycle.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.contactUs.ContactUs;
import com.cops.bloodbankclone.view.activity.HomeActivity;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;
import static com.cops.bloodbankclone.utility.CheckInput.isEditTextSet;
import static com.cops.bloodbankclone.utility.CheckInput.isPhoneSet;

public class ContactUsFragment extends BaseFragment {

    @BindView(R.id.fragment_contact_us__et_title)
    EditText fragmentContactUsEtTitle;
    @BindView(R.id.fragment_contact_us__et_message)
    EditText fragmentContactUsEtMessage;
    @BindView(R.id.contact_us_fragment_btn_send)
    Button contactUsBtnSend;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initFragment();
        View view =inflater.inflate(R.layout.fragment_contact_us,container,false);
        ButterKnife.bind(this,view);

        Toolbar toolbar=(Toolbar)view.findViewById(R.id.contact_us_fragment_toolbar);
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

        return view;
    }

    @OnClick(R.id.contact_us_fragment_btn_send)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.contact_us_fragment_btn_send:
                if (isEditTextSet(fragmentContactUsEtTitle,fragmentContactUsEtMessage)) {
                    sendMessage(getClient().contactUs(LoadData(getActivity(),"apiToken"),fragmentContactUsEtTitle.getText().toString(),fragmentContactUsEtMessage.getText().toString()));
                }

        }
    }

    private void sendMessage(Call<ContactUs> call) {
        call.enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {
                try {
                    if (response.body().getStatus()==1) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ContactUs> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBack() {
        super.onBack();
    }
}
