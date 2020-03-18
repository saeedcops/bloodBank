package com.cops.bloodbankclone.view.fragment.homeCycle.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.DonationAdapter;
import com.cops.bloodbankclone.adapter.GeneralSpinnerAdapter;
import com.cops.bloodbankclone.adapter.PostAdapter;
import com.cops.bloodbankclone.data.model.donation.Donation;
import com.cops.bloodbankclone.data.model.generalResponce.GeneralResponce;
import com.cops.bloodbankclone.data.model.post.Category;
import com.cops.bloodbankclone.data.model.post.Post;
import com.cops.bloodbankclone.data.model.post.PostData;
import com.cops.bloodbankclone.data.model.postDetails.PostDetails;
import com.cops.bloodbankclone.utility.OnEndLess;
import com.cops.bloodbankclone.view.fragment.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;
import static com.cops.bloodbankclone.utility.GeneralRequest.getSpinnerData;

public class PostFragment extends BaseFragment {

    @BindView(R.id.post_fragment_search)
    SearchView postFragmentSearch;
    @BindView(R.id.post_fragment_rv)
    RecyclerView postFragmentRv;
    @BindView(R.id.post_fragment_swipe)
    SwipeRefreshLayout postFragmentSwipe;
    @BindView(R.id.post_fragment_sp)
    Spinner postFragmentSp;

    private PostAdapter postAdapter;
    private GeneralSpinnerAdapter categoryAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<PostData> postData = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    public static int cur_page = 1;

    private Integer maxPage = 0;
    OnEndLess onEndLess;

    public PostFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        categoryAdapter = new GeneralSpinnerAdapter(getActivity());

       getSpinnerCategory(getClient().getCategories());


        ButterKnife.bind(this, view);



        layoutManager = new LinearLayoutManager(getActivity());
        postFragmentRv.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {

                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        cur_page = current_page;

                        getPost(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                        cur_page = onEndLess.previous_page;
                    }

                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                    cur_page = onEndLess.previous_page;
                }

            }
        };
        postFragmentRv.addOnScrollListener(onEndLess);
        postAdapter = new PostAdapter(getActivity(), postData);
        postFragmentRv.setAdapter(postAdapter);

        getPost(1);

        postFragmentSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPost(1);
            }
        });

        postFragmentSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position!=0) {
                    getSearchedPost(1,"",String.valueOf(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void getSpinnerCategory(Call<GeneralResponce> call) {
        call.enqueue(new Callback<GeneralResponce>() {
            @Override
            public void onResponse(Call<GeneralResponce> call, Response<GeneralResponce> response) {
                try {

                    if (response.body().getStatus()==1) {
                        getSpinnerData(getClient().getCategories(), postFragmentSp, categoryAdapter,
                                response.body().getData().get(0).getId(), "filter");

                    }
                }catch (Exception e){}

            }

            @Override
            public void onFailure(Call<GeneralResponce> call, Throwable t) {

            }
        });
    }

    private void getPost(int page) {
        getClient().getPost(LoadData(getActivity(), "apiToken"), page).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                try {
                    postFragmentSwipe.setRefreshing(false);

                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        postData.addAll(response.body().getData().getData());

                        if (page == 1) {
                            onEndLess.current_page = 1;
                            onEndLess.previous_page = 1;
                            onEndLess.previousTotal = 0;

                            postAdapter = new PostAdapter(getActivity(), postData);
                            postData = new ArrayList<>();
                            postFragmentRv.setAdapter(postAdapter);
                        }
                        postAdapter.notifyDataSetChanged();

                    }else {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                postFragmentSwipe.setRefreshing(false);
            }
        });
    }

    private void getSearchedPost(int page,String keyWord,String categoryId) {
        getClient().getPostFilter(LoadData(getActivity(),"apiToken"),page,
                keyWord,categoryId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
             try {
                // postFragmentSwipe.setRefreshing(false);

                 if (response.body().getStatus() == 1) {
                     maxPage = response.body().getData().getLastPage();
                     postData.addAll(response.body().getData().getData());

                     if (page == 1) {
                         onEndLess.current_page = 1;
                         onEndLess.previous_page = 1;
                         onEndLess.previousTotal = 0;

                         postAdapter = new PostAdapter(getActivity(), postData);
                         postData = new ArrayList<>();
                         postFragmentRv.setAdapter(postAdapter);
                     }
                     postAdapter.notifyDataSetChanged();

                 }

             }catch (Exception e){}


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public void onBack() {
        super.onBack();
    }


}
