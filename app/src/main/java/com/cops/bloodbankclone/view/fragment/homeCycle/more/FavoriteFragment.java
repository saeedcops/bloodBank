package com.cops.bloodbankclone.view.fragment.homeCycle.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.adapter.PostAdapter;
import com.cops.bloodbankclone.data.model.post.Post;
import com.cops.bloodbankclone.data.model.post.PostData;
import com.cops.bloodbankclone.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;

public class FavoriteFragment extends BaseFragment {

    @BindView(R.id.favorite_fragment_rv)
    RecyclerView favoriteFragmentRv;
    private PostAdapter postAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<PostData> favouritePostData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        ButterKnife.bind(this, view);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.favorite_fragment_toolbar);
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


        layoutManager = new LinearLayoutManager(getActivity());
        favoriteFragmentRv.setLayoutManager(layoutManager);


        postAdapter = new PostAdapter(getActivity(), favouritePostData);
        favoriteFragmentRv.setAdapter(postAdapter);

        getClient().getFavourite(LoadData(getActivity(),"apiToken")).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                try {
                    if (response.body().getStatus()==1) {
                        favouritePostData.addAll(response.body().getData().getData());
                        postAdapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){}

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
        return view;
    }

    @Override
    public void onBack() {

        super.onBack();
    }
}
