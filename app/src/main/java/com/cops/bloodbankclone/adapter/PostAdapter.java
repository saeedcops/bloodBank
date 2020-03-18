package com.cops.bloodbankclone.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.post.Post;
import com.cops.bloodbankclone.data.model.post.PostData;
import com.cops.bloodbankclone.view.activity.BaseActivity;
import com.cops.bloodbankclone.view.fragment.homeCycle.donation.DonationRequestFromFragment;
import com.cops.bloodbankclone.view.fragment.homeCycle.post.PostDetailesFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.cops.bloodbankclone.data.api.RetroritClient.getClient;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.LoadData;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {


    private Context context;
    private BaseActivity activity;
    private ArrayList<PostData> postData = new ArrayList<>();


    public PostAdapter(Activity activity, ArrayList<PostData> postData) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.postData = postData;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,
                parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        setData(holder, position);

    }

    private void setData(ViewHolder holder, int position) {

        holder.position = position;
        holder.itemPostBtn.setText(postData.get(position).getCategory().getName());
        Glide.with(context).load(postData.get(position).getThumbnailFullPath()).into(holder.itemPostIv);

        if (postData.get(position).getIsFavourite() == true) {

            holder.itemPostIbLike.setImageResource(R.drawable.like);
        } else {
            holder.itemPostIbLike.setImageResource(R.drawable.deslike);
        }

    }


    @Override
    public int getItemCount() {
        return postData.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_post_iv)
        ImageView itemPostIv;
        @BindView(R.id.item_post_ib_like)
        ImageButton itemPostIbLike;
        @BindView(R.id.item_post_btn)
        Button itemPostBtn;
        private View view;
        private int position;


        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }

        @OnClick({R.id.item_post_ib_like, R.id.item_post_rl_parent})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.item_post_ib_like:
                    if (postData.get(position).getIsFavourite() == true) {
                        postData.get(position).setIsFavourite(false);
                        itemPostIbLike.setImageResource(R.drawable.deslike);
                    } else {
                        postData.get(position).setIsFavourite(true);
                        itemPostIbLike.setImageResource(R.drawable.like);
                        //favouritePostData.add(postData.get(position));

                    }
                    getClient().setFavourite(postData.get(position).getId(),LoadData(activity,"apiToken")).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {

                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });


                    break;
                case R.id.item_post_rl_parent:
                    Bundle bundle = new Bundle();
                    bundle.putInt("postId", postData.get(position).getId());
                    PostDetailesFragment post=new PostDetailesFragment();
                    post.setArguments(bundle);

                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.home_activity_fl_frame, post)
                            .addToBackStack(null).commit();
                    break;
            }
        }
    }
}
