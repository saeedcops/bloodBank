package com.cops.bloodbankclone.view.fragment.homeCycle.donation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.donationDetails.DonationDetails;
import com.cops.bloodbankclone.utility.HelperMethod;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;

public class DonationRequestFromFragment extends BaseFragment {

    @BindView(R.id.donation_request_from_fragment_tv_name)
    TextView donationRequestFromFragmentTvName;
    @BindView(R.id.donation_request_from_fragment_tv_age)
    TextView donationRequestFromFragmentTvAge;
    @BindView(R.id.donation_request_from_fragment_tv_blood_type)
    TextView donationRequestFromFragmentTvBloodType;
    @BindView(R.id.donation_request_from_fragment_tv_bag_num)
    TextView donationRequestFromFragmentTvBagNum;
    @BindView(R.id.donation_request_from_fragment_tv_hospital)
    TextView donationRequestFromFragmentTvHospital;
    @BindView(R.id.donation_request_from_fragment_tv_address)
    TextView donationRequestFromFragmentTvAddress;
    @BindView(R.id.donation_request_from_fragment_tv_phone)
    TextView donationRequestFromFragmentTvPhone;
    @BindView(R.id.donation_request_from_fragment_tv_note)
    TextView donationRequestFromFragmentTvNote;
    private String phone;
    private GoogleMap map;
    private double longitude=1, latitude=1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_donation_request_from, container, false);

        ButterKnife.bind(this, view);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.donation_request_from_fragment_toolbar);

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

        int donationId = getArguments().getInt("donationId");

        getData(getClient().getDonationDetails(LoadData(getActivity(), "apiToken"), donationId));


        return view;
    }


    private void getData(Call<DonationDetails> call) {
        call.enqueue(new Callback<DonationDetails>() {
            @Override
            public void onResponse(Call<DonationDetails> call, Response<DonationDetails> response) {

                try {

                    if (response.body().getStatus() == 1) {
                        donationRequestFromFragmentTvName.setText(getString(R.string.name) + " : " + response.body().getData().getPatientName());
                        donationRequestFromFragmentTvAge.setText(getString(R.string.age) + " : " + response.body().getData().getPatientAge());
                        donationRequestFromFragmentTvBloodType.setText(getString(R.string.blood_type) + " : " + response.body().getData().getBloodType().getName());
                        donationRequestFromFragmentTvBagNum.setText(getString(R.string.bag_num) + " : " + response.body().getData().getBagsNum());
                        donationRequestFromFragmentTvHospital.setText(getString(R.string.hospital_name) + " : " + response.body().getData().getHospitalName());
                        donationRequestFromFragmentTvAddress.setText(getString(R.string.address) + " : " + response.body().getData().getHospitalAddress());
                        donationRequestFromFragmentTvPhone.setText(getString(R.string.phone) + " : " + response.body().getData().getPhone());
                        donationRequestFromFragmentTvNote.setText(getString(R.string.note) + " : " + response.body().getData().getNotes());
                        phone = response.body().getData().getPhone();
                        latitude = Double.parseDouble(response.body().getData().getLatitude());
                        longitude = Double.parseDouble(response.body().getData().getLongitude());

                        if (latitude != 1 && longitude != 1) {
                            setMapView(latitude, longitude);
                        }
                    }else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<DonationDetails> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setMapView(double latitude, double longitude) {
      //  GoogleMapOptions options = new GoogleMapOptions().liteMode(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.donation_request_from_fragment_fr_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                map=googleMap;
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


                map.clear(); //clear old markers

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(latitude,longitude))
                        .zoom(15)
                        .bearing(0)
                        .tilt(45)
                        .build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 2500, null);

                map.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude,longitude))
                        .title("Hospital location")

                );

            }
        });


    }


    @OnClick({R.id.donation_request_from_fragment_fr_map, R.id.donation_request_from_fragment_btn_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.donation_request_from_fragment_fr_map:
                break;
            case R.id.donation_request_from_fragment_btn_call:
                HelperMethod.onPermission(getActivity());

                getActivity().startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, "")));

                break;
        }
    }

    @Override
    public void onBack() {


        super.onBack();
    }
}
