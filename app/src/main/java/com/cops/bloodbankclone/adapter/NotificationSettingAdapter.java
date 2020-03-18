package com.cops.bloodbankclone.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.generalResponce.GeneralResponceData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationSettingAdapter extends RecyclerView.Adapter<NotificationSettingAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<GeneralResponceData> generalDataList = new ArrayList<>();
    private List<String> oldID = new ArrayList<>();
    public List<Integer> newID = new ArrayList<>();

    public NotificationSettingAdapter(Context context, Activity activity, List<GeneralResponceData> generalDataList, List<String> oldID) {
        this.context = context;
        this.activity = activity;
        this.generalDataList = generalDataList;
        this.oldID = oldID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_check_box,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);

    }

    private void setData(ViewHolder holder, int position) {
        holder.checkBoxItemRv.setText(generalDataList.get(position).getName());

        if (oldID.contains(String.valueOf(generalDataList.get(position).getId()))) {

            holder.checkBoxItemRv.setChecked(true);
            newID.add(generalDataList.get(position).getId());
        }else {
            holder.checkBoxItemRv.setChecked(false);
        }

    }


    @Override
    public int getItemCount() {
        return generalDataList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.check_box_item_rv)
        CheckBox checkBoxItemRv;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.check_box_item_rv)
        public void onViewClicked() {

            if (!checkBoxItemRv.isChecked()) {

                for (int i = 0; i <newID.size() ; i++) {
                    if (newID.get(i).equals(generalDataList.get(getAdapterPosition()).getId())) {
                        newID.remove(i);
                       // Toast.makeText(context, "removed", Toast.LENGTH_SHORT).show();
                        break;

                    }

                }

            }else {

                newID.add(generalDataList.get(getAdapterPosition()).getId());
            }
        }
    }
}
