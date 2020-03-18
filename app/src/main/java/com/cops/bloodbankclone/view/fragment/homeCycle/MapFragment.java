package com.cops.bloodbankclone.view.fragment.homeCycle;

import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.utility.GPSTracker;
import com.cops.bloodbankclone.view.activity.HomeActivity;
import com.cops.bloodbankclone.view.fragment.BaseFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.donation.RequestDonationFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

//implements GoogleMap.OnMapLongClickListener
public class MapFragment extends BaseFragment {


    private GPSTracker gps;
    private GoogleMap map;
    public static String longitude, latitude;
    public static List<Address> addresses;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gps = new GPSTracker(getActivity(), getActivity());
        addresses = gps.getGeocoderAddress(getActivity());
        latitude = String.valueOf(gps.getLatitude());
        longitude = String.valueOf(gps.getLongitude());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        ButterKnife.bind(this, view);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                map = googleMap;
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


                map.clear(); //clear old markers

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)))
                        .zoom(12)
                        .bearing(0)
                        .tilt(45)
                        .build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);

                map.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)))
                        .title("my location")

                );

                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        latitude = String.valueOf(latLng.latitude);
                        longitude = String.valueOf(latLng.longitude);
                        addresses = gps.getClickedGeocoder(getActivity(), latLng);
                        map.clear();
                        CameraPosition googlePlex = CameraPosition.builder()
                                .target(new LatLng(Double.valueOf(latitude), Double.valueOf(longitude)))
                                .zoom(10)
                                .bearing(0)
                                .tilt(45)
                                .build();
                        map.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
                        map.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Hospital location")

                        );
                    }
                });

            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.fragment_map_btn_location_set)
    public void onViewClicked() {
        onBack();

//        Bundle bundle=new Bundle();
//
//        bundle.putString("lat",String.valueOf(latitude));
//        bundle.putString("long",String.valueOf(longitude));
//        bundle.putString("city",addresses.get(0).getAddressLine(0));
//        RequestDonationFragment rdf=new RequestDonationFragment();
//        rdf.setArguments(bundle);
//
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.home_activity_fl_frame, rdf)
//                .addToBackStack(null).commit();

    }

    @Override
    public void onBack() {
        super.onBack();
    }

}
