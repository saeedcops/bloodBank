package com.cops.bloodbankclone.view.fragment.homeCycle.donation;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.DonationAdapter;
import com.cops.bloodbankclone.adapter.GeneralSpinnerAdapter;
import com.cops.bloodbankclone.data.model.donation.Donation;
import com.cops.bloodbankclone.data.model.donation.DonationData;
import com.cops.bloodbankclone.utility.OnEndLess;
import com.cops.bloodbankclone.view.fragment.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;
import static com.cops.bloodbankclone.utility.GeneralRequest.getSpinnerItems;

public class DonationFragment extends BaseFragment{

    @BindView(R.id.donation_fragment_sp_government)
    Spinner donationFragmentSpGovernment;
    @BindView(R.id.donation_fragment_sp_blood_type)
    Spinner donationFragmentSpBloodType;
    @BindView(R.id.donation_fragment_rv)
    RecyclerView donationFragmentRv;
    Unbinder unbinder;
    @BindView(R.id.donation_fragment_swipe)
    SwipeRefreshLayout donationFragmentSwipe;
    private GeneralSpinnerAdapter bloodTypeAdapter, governmentAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<DonationData> donationDataList = new ArrayList<>();
    DonationAdapter donationAdapter;
    private Integer maxPage = 0;
    OnEndLess onEndLess;
    private boolean doubleBackToExistNotOnce;

    public DonationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_donation, container, false);

        unbinder = ButterKnife.bind(this, view);
        governmentAdapter = new GeneralSpinnerAdapter(getActivity());
        bloodTypeAdapter = new GeneralSpinnerAdapter(getActivity());

        getSpinnerItems(getClient().getProfile(LoadData(getActivity(), "apiToken")), getActivity(), false, governmentAdapter, bloodTypeAdapter,
                donationFragmentSpBloodType, donationFragmentSpGovernment);


        donationAdapter = new DonationAdapter(getActivity(), donationDataList);
        linearLayoutManager = new LinearLayoutManager(getActivity());


        donationFragmentRv.setLayoutManager(linearLayoutManager);


        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {

                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        getDonations(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };

        donationFragmentRv.addOnScrollListener(onEndLess);
        donationFragmentRv.setAdapter(donationAdapter);

        getDonations(1);

        donationFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDonations(1);
            }
        });


        return view;
    }

    private void getDonations(int page) {
        getClient().donationRequest(LoadData(getActivity(), "apiToken"), page).enqueue(new Callback<Donation>() {
            @Override
            public void onResponse(Call<Donation> call, Response<Donation> response) {
                try {
                    donationFragmentSwipe.setRefreshing(false);

                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        donationDataList.addAll(response.body().getData().getData());

                        if (page == 1) {
                            onEndLess.current_page = 1;
                            onEndLess.previous_page = 1;
                            onEndLess.previousTotal = 0;

                            donationAdapter = new DonationAdapter(getActivity(), donationDataList);
                            donationDataList = new ArrayList<>();
                            donationFragmentRv.setAdapter(donationAdapter);
                        }

                        donationAdapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<Donation> call, Throwable t) {

            }
        });

    }

    private void getSearchedDonations(int page) {
        getClient().donationRequest(LoadData(getActivity(),"apiToken"),String.valueOf(donationFragmentSpBloodType.getSelectedItemPosition()),
                String.valueOf(donationFragmentSpGovernment.getSelectedItemPosition()),page).enqueue(new Callback<Donation>() {
            @Override
            public void onResponse(Call<Donation> call, Response<Donation> response) {
                if (response.body().getStatus()==1) {
                    Toast.makeText(baseActivity, response.message(), Toast.LENGTH_SHORT).show();
                    maxPage = response.body().getData().getLastPage();
                    donationDataList.clear();
                    donationDataList.addAll(response.body().getData().getData());
                    donationAdapter.notifyDataSetChanged();
                    if (page == 1) {
                        onEndLess.current_page = 1;
                        onEndLess.previous_page = 1;
                        onEndLess.previousTotal = 0;

                        donationAdapter = new DonationAdapter(getActivity(), donationDataList);
                        donationDataList = new ArrayList<>();
                        donationFragmentRv.setAdapter(donationAdapter);
                    }
                }


            }

            @Override
            public void onFailure(Call<Donation> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.donation_fragment_ib)
    public void onViewClicked() {
        getSearchedDonations(1);

    }

    @Override
    public void onBack() {

        if(doubleBackToExistNotOnce) {
            baseActivity.finishAffinity();
        }
        this.doubleBackToExistNotOnce=true;
        Toast.makeText(getActivity(), "Press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExistNotOnce=false;
            }
        },2000);

    }
}
