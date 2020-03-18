package com.cops.bloodbankclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.data.model.generalResponce.GeneralResponceData;

import java.util.ArrayList;
import java.util.List;

public class GeneralSpinnerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    public int selectedId = 0;
    public List<GeneralResponceData> generalResponceData = new ArrayList();

    public GeneralSpinnerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<GeneralResponceData> generalResponseDataList, String hint) {
        generalResponseDataList.add(0,new GeneralResponceData(0, hint));
        this.generalResponceData = generalResponseDataList;
    }


    @Override
    public int getCount() {
        return generalResponceData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_spinner, null);

        TextView names = (TextView) convertView.findViewById(R.id.item_spinner_tv);

        names.setText(generalResponceData.get(position).getName());
        selectedId = generalResponceData.get(position).getId();

        return convertView;
    }
}
