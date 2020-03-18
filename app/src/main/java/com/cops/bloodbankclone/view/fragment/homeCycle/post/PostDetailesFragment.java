package com.cops.bloodbankclone.view.fragment.homeCycle.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.postDetails.PostDetails;
import com.cops.bloodbankclone.view.fragment.BaseFragment;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;
import static com.cops.bloodbankclone.view.fragment.homeCycle.post.PostFragment.cur_page;

public class PostDetailesFragment extends BaseFragment {


    @BindView(R.id.fragment_post_details_iv)
    ImageView fragmentPostDetailsIv;
    @BindView(R.id.fragment_post_details_ib_like)
    ImageButton fragmentPostDetailsIbLike;
    @BindView(R.id.fragment_post_details_tv_title)
    TextView fragmentPostDetailsTvTitle;
    @BindView(R.id.fragment_post_details_tv_post)
    TextView fragmentPostDetailsTvPost;
    @BindView(R.id.fragment_post_details_iv_back)
    ImageView fragmentPostDetailsIvBack;

    public PostDetailesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initFragment();
        View view = inflater.inflate(R.layout.fragment_post_detailes, container, false);
        ButterKnife.bind(this, view);

        if(Locale.getDefault().getLanguage()=="ar"){
            fragmentPostDetailsIvBack.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
        }

        int postId = getArguments().getInt("postId");
        getPost(postId, cur_page);


        return view;
    }

    private void getPost(int postId, int page) {
        getClient().getPostDetails(LoadData(getActivity(), "apiToken"), postId, page).enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                try {

                    if (response.body().getStatus() == 1) {
                        Glide.with(getActivity()).load(response.body().getData().getThumbnailFullPath()).into(fragmentPostDetailsIv);
                        if (response.body().getData().getIsFavourite() == true) {
                            fragmentPostDetailsIbLike.setImageResource(R.drawable.like);
                        } else {
                            fragmentPostDetailsIbLike.setImageResource(R.drawable.deslike);
                        }
                        fragmentPostDetailsTvTitle.setText(response.body().getData().getCategory().getName());
                        // fragmentPostDetailsTvPost.setText(response.body().getData().getContent());

                    } else {
                        Toast.makeText(baseActivity, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
                Toast.makeText(baseActivity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }


    @OnClick(R.id.fragment_post_details_iv_back)
    public void onViewClicked() {
        onBack();
    }
}
