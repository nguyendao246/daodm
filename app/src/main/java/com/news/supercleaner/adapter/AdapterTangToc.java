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
import com.news.supercleaner.model.ItemTangToc;

import java.util.ArrayList;


public class AdapterTangToc extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<ItemTangToc> list;

    public AdapterTangToc(Context context, ArrayList<ItemTangToc> list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ItemTangToc getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = mInflater.inflate(R.layout.item_tang_toc, null, false);
        ViewHolder holder = new ViewHolder(view);
        holder.img.setImageDrawable(list.get(position).getImg());
        holder.tvName.setText(list.get(position).getName());
        holder.tvPhanTram.setText(sizeApp(list.get(position).getDungLuong()) + "");
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
            this.tvPhanTram = (TextView) rootView.findViewById(R.id.tv_dung_luong);
        }

    }

    private String sizeApp(double size) {
        double mb = size / 1024 / 1024;
        double gb = mb / 1024 / 1024;
        if (gb > 1) {
            int x = (int) (gb * 100);
            return (double) x * 1.0 / 100 + " GB";
        } else if (mb > 1) {
            int x = (int) (mb * 100);
            return (double) x * 1.0 / 100 + "MB";
        } else {
            int x = (int) (mb * 100 * 1024);
            return (double) x * 1.0 / 100 + " KB";
        }

    }


}
