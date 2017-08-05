package com.news.supercleaner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.model.ResutlVirus;
import com.news.supercleaner.R;

import java.util.ArrayList;

/**
 * Created by Kopiteshot on 7/21/2017.
 */

public class ResultVirusAdapter extends ArrayAdapter<ResutlVirus> {
    private LayoutInflater layoutInflater;
    private ArrayList<ResutlVirus> resutlViruses;

    public ResultVirusAdapter(@NonNull Context context, @NonNull ArrayList<ResutlVirus> resutlViruses) {
        super(context, android.R.layout.simple_list_item_1, resutlViruses);
        this.resutlViruses = resutlViruses;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.resultvirus_item, parent, false);
            viewHolder.imgVirus = (ImageView) convertView.findViewById(R.id.imgnotificationvirus);
            viewHolder.tvNotiVirus = (TextView) convertView.findViewById(R.id.tvnotificationvirus);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvcontentvirus);
            viewHolder.tvManager = (TextView) convertView.findViewById(R.id.btoptionvirus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ResutlVirus resutlVirus = resutlViruses.get(position);
        viewHolder.imgVirus.setImageResource(resutlVirus.getImageVirus());
        viewHolder.tvContent.setText(resutlVirus.getContent());
        viewHolder.tvNotiVirus.setText(resutlVirus.getNotification());
        viewHolder.tvManager.setText(resutlVirus.getManager());
        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);
        convertView.startAnimation(animation);
        return convertView;
    }

    class ViewHolder {// leu trang thai cua view trong viewitem
        ImageView imgVirus;
        TextView tvNotiVirus;
        TextView tvContent;
        TextView tvManager;
    }
}
