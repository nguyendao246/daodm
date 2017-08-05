package com.news.supercleaner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.model.ItemCongCu;

import java.util.List;

/**
 * Created by asus on 21/07/2017.
 */

public class AdapterCongCu extends BaseAdapter {
    private List<ItemCongCu> list;
    private LayoutInflater mInflater;

    public AdapterCongCu(Context context, List<ItemCongCu> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ItemCongCu getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = mInflater.inflate(R.layout.item_cong_cu, null, false);
        ViewHolder holder = new ViewHolder(view);
        holder.img.setImageResource(list.get(position).getId());
        holder.tvTitle.setText(list.get(position).getName());
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public ImageView img;
        public TextView tvTitle;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.img = (ImageView) rootView.findViewById(R.id.img);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tv_content);
        }

    }
}
