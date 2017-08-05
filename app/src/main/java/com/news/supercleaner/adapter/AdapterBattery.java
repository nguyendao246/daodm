package com.news.supercleaner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.model.ItemBattery;

import java.util.ArrayList;

/**
 * Created by asus on 03/08/2017.
 */

public class AdapterBattery extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<ItemBattery> list;

    public AdapterBattery(Context context, ArrayList<ItemBattery> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ItemBattery getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = mInflater.inflate(R.layout.item_battery, null, false);
        ViewHolder holder = new ViewHolder(view);
        holder.img.setImageDrawable(list.get(position).getImg());
        holder.tvName.setText(list.get(position).getName());
        holder.tvPhanTram.setText(list.get(position).getPhanTram());
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView img;
        public TextView tvName;
        private TextView tvPhanTram;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.img = (ImageView) rootView.findViewById(R.id.img);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
            this.tvPhanTram = (TextView) rootView.findViewById(R.id.tv_phan_tram);
        }

    }
}
