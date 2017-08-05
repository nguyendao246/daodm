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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.model.UnistallAppItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



public class UninstallAdapter extends ArrayAdapter<UnistallAppItem> {
    private LayoutInflater layoutInflater;
    private ArrayList<UnistallAppItem> unistallAppItems;

    public UninstallAdapter(@NonNull Context context, @NonNull ArrayList<UnistallAppItem> unistallAppItems) {
        super(context, android.R.layout.simple_list_item_1, unistallAppItems);
        this.unistallAppItems = unistallAppItems;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final ViewHoler viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHoler();
            convertView = layoutInflater.inflate(R.layout.uninstallitem, parent, false);
            viewHolder.iconApp = (ImageView) convertView.findViewById(R.id.iconunistall);
            viewHolder.dateApp = (TextView) convertView.findViewById(R.id.dateunistall);
            viewHolder.sizeApp = (TextView) convertView.findViewById(R.id.sizeunitall);
            viewHolder.nameApp = (TextView) convertView.findViewById(R.id.nameuniapp);
            viewHolder.cbApp = (CheckBox) convertView.findViewById(R.id.checkboxunistall);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHoler) convertView.getTag();
        }

        UnistallAppItem boostBackground = unistallAppItems.get(position);
        viewHolder.iconApp.setImageDrawable(boostBackground.getIconApp());
        viewHolder.nameApp.setText(boostBackground.getNameApp());
        viewHolder.sizeApp.setText(sizeApp(boostBackground.getSizeApp()) + "");
        viewHolder.dateApp.setText("Install date " + getDate(boostBackground.getDateInstall()));
        viewHolder.cbApp.setChecked(boostBackground.isPick());
        viewHolder.cbApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean isChecked = viewHolder.cbApp.isChecked();
                if (!isChecked) {
                    unistallAppItems.get(position).setPick(false);
                    // viewHolder.getCheckBox().setChecked(false);
                } else {
                    unistallAppItems.get(position).setPick(true);
//                    Button b = (Button) parent.findViewById(R.id.btnuninstall);
//                    b.setVisibility(View.VISIBLE);
                }

            }
        });

        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        animation.setDuration(500);

        convertView.startAnimation(animation);
        return convertView;
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

    class ViewHoler {
        ImageView iconApp;
        TextView nameApp;
        TextView dateApp;
        TextView sizeApp;
        CheckBox cbApp;
    }


    public static String getDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
