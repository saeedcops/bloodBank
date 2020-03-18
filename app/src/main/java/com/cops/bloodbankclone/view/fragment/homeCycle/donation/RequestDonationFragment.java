package com.cops.bloodbankclone.view.fragment.homeCycle.donation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.GeneralSpinnerAdapter;
import com.cops.bloodbankclone.data.model.requestDonation.RequestDonation;
import com.cops.bloodbankclone.utility.HelperMethod;
import com.cops.bloodbankclone.view.activity.HomeActivity;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.HomeFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.MapFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.more.FavoriteFragment;
import com.google.android.material.textfield.TextInputEditText;
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
import static com.cops.bloodbankclone.utility.GeneralRequest.getSpinnerItems;

public class RequestDonationFragment extends BaseFragment {

    @BindView(R.id.request_donation_fragment_til_name)
    TextInputLayout requestDonationFragmentTilName;
    @BindView(R.id.request_donation_fragment_til_age)
    TextInputLayout requestDonationFragmentTilAge;
    @BindView(R.id.request_donation_fragment_sp_blood_type)
    Spinner requestDonationFragmentSpBloodType;
    @BindView(R.id.request_donation_fragment_til_blood_type)
    TextInputLayout requestDonationFragmentTilBloodType;
    @BindView(R.id.request_donation_fragment_til_number)
    TextInputLayout requestDonationFragmentTilNumber;
    @BindView(R.id.request_donation_fragment_ib_map)
    ImageButton requestDonationFragmentIbMap;

    @BindView(R.id.request_donation_fragment_sp_choose_government)
    Spinner requestDonationFragmentSpChooseGovernment;
    @BindView(R.id.request_donation_fragment_til_choose_government)
    TextInputLayout requestDonationFragmentTilChooseGovernment;
    @BindView(R.id.request_donation_fragment_sp_choose_city)
    Spinner requestDonationFragmentSpChooseCity;
    @BindView(R.id.request_donation_fragment_til_choose_city)
    TextInputLayout requestDonationFragmentTilChooseCity;
    @BindView(R.id.request_donation_fragment_til_phone)
    TextInputLayout requestDonationFragmentTilPhone;
    @BindView(R.id.request_donation_fragment_til_note)
    TextInputLayout requestDonationFragmentTilNote;
    @BindView(R.id.request_donation_fragment_til_hospital_name)
    TextInputLayout requestDonationFragmentTilHospitalName;
    GeneralSpinnerAdapter spinnerGovernmentAdapter, spinnerBloodTypeAdapter, spinnerCityAdapter;
    @BindView(R.id.fragment_register_btn)
    Button fragmentRegisterBtn;
    @BindView(R.id.request_donation_layout)
    LinearLayout requestDonationLayout;

   // Bundle outState;

    @BindView(R.id.request_donation_fragment_et_hos_loc)
    TextInputEditText requestDonationFragmentEtHosLoc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState!=null) {
//            Toast.makeText(baseActivity, savedInstanceState.getString("name"), Toast.LENGTH_SHORT).show();
//            requestDonationFragmentTilName.getEditText().setText(savedInstanceState.getString("name"));
//        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_request_donation, container, false);

        ButterKnife.bind(this, view);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.request_donation_fragment_toolbar);
        if(Locale.getDefault().getLanguage().equals("en"))

            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        else

            toolbar.setNavigationIcon(R.drawable.ic_arrow_forward_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                HomeActivity.floatingActionButton.setVisibility(View.VISIBLE);
            }

        });

        spinnerGovernmentAdapter = new GeneralSpinnerAdapter(getActivity());
        spinnerBloodTypeAdapter = new GeneralSpinnerAdapter(getActivity());
        spinnerCityAdapter = new GeneralSpinnerAdapter(getActivity());

        getSpinnerItems(getClient().getProfile(LoadData(getActivity(), "apiToken")), getActivity(), false, spinnerGovernmentAdapter,
                spinnerBloodTypeAdapter, spinnerCityAdapter, requestDonationFragmentSpBloodType, requestDonationFragmentSpChooseCity, requestDonationFragmentSpChooseGovernment);


        return view;
    }


    @OnClick({R.id.fragment_register_btn, R.id.request_donation_layout, R.id.request_donation_fragment_ib_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_btn:

                if (isEditTextSet(requestDonationFragmentTilName, requestDonationFragmentTilAge,
                        requestDonationFragmentTilHospitalName, requestDonationFragmentTilNumber,
                        requestDonationFragmentTilPhone, requestDonationFragmentTilNote)
                        && isPhoneSet(requestDonationFragmentTilPhone)) {

                    if (isEditTextSet(requestDonationFragmentEtHosLoc)) {
                        startDonation();
                    }

                }
                break;
            case R.id.request_donation_layout:
                HelperMethod.disappearKeypad(getActivity(), view);
                break;

            case R.id.request_donation_fragment_ib_map:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_activity_fl_frame, new MapFragment())
                        .addToBackStack(null).commit();
                break;
        }
    }

    private void startDonation() {

        getClient().createDonation(LoadData(getActivity(), "apiToken"), requestDonationFragmentTilName.getEditText().getText().toString(),
                requestDonationFragmentTilAge.getEditText().getText().toString(), String.valueOf(requestDonationFragmentSpBloodType.getSelectedItemPosition()),
                requestDonationFragmentTilNumber.getEditText().getText().toString(), requestDonationFragmentTilHospitalName.getEditText().getText().toString(),
                requestDonationFragmentEtHosLoc.getText().toString(), String.valueOf(requestDonationFragmentSpChooseGovernment.getSelectedItemPosition()),
                requestDonationFragmentTilPhone.getEditText().getText().toString(), requestDonationFragmentTilNote.getEditText().getText().toString(),
                String.valueOf(MapFragment.latitude), String.valueOf(MapFragment.latitude)).enqueue(new Callback<RequestDonation>() {
            @Override
            public void onResponse(Call<RequestDonation> call, Response<RequestDonation> response) {

                try {

                    if (response.body().getStatus() == 1) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        //to navigate to donation fragment to show user his creation
                        Bundle bundle=new Bundle();
                        bundle.putString("key","coming from add donation");
                        HomeFragment homeFragment=new HomeFragment();
                        homeFragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.home_activity_fl_frame, homeFragment)
                                .addToBackStack(null).commit();


                    } else {

                        Toast.makeText(getActivity(),response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RequestDonation> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("name",requestDonationFragmentTilName.getEditText().getText().toString());
//
//    }



    @Override
    public void onBack() {
        HomeActivity.floatingActionButton.setVisibility(View.VISIBLE);

        super.onBack();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MapFragment.longitude != null) {

            requestDonationFragmentEtHosLoc.setText(MapFragment.addresses.get(0).getAddressLine(0));
        }
        if (MapFragment.addresses!=null) {

            requestDonationFragmentEtHosLoc.setText(MapFragment.addresses.get(0).getAddressLine(0));
        }

    }
}
