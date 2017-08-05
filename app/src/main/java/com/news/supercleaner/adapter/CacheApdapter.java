package com.news.supercleaner.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.model.CacheChild;
import com.news.supercleaner.model.CacheGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CacheApdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<CacheGroup> cacheGroups;
    private HashMap<CacheGroup, List<CacheChild>> listListDictionary;

    public CacheApdapter(Context context, ArrayList<CacheGroup> cacheGroups, HashMap<CacheGroup, List<CacheChild>> listListDictionary) {
        this.context = context;
        this.cacheGroups = cacheGroups;
        this.listListDictionary = listListDictionary;
    }

    public static double formatSize(double size) {
        if (size > 1024) {
            size = size / (1024);
        }
        return size;
    }

    @Override

    public int getGroupCount() {
        return cacheGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listListDictionary.get(cacheGroups.get(groupPosition)).size();
    }

    @Override
    public CacheGroup getGroup(int groupPosition) {
        return cacheGroups.get(groupPosition);
    }

    @Override
    public CacheChild getChild(int groupPosition, int childPosition) {
        return listListDictionary.get(cacheGroups.get(groupPosition)).get(childPosition);

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getTitle();
        double sizeTitle = getGroup(groupPosition).getSize();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cache_group, null);
        }
        TextView t = (TextView) convertView.findViewById(R.id.namecgroupacheapp);
        TextView s = (TextView) convertView.findViewById(R.id.sizegroupcache);
        t.setText(headerTitle);
        if (sizeTitle > 1024) {
            sizeTitle = formatSize(sizeTitle);
            sizeTitle = tinhToan(sizeTitle);
            s.setText(sizeTitle + "MB");
        } else {
            sizeTitle = tinhToan(sizeTitle);
            s.setText(sizeTitle + "KB");
        }
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String headerTitle = getChild(groupPosition, childPosition).getNameAppCache();
        double sizeTitle = getChild(groupPosition, childPosition).getSizeCache();
        Drawable iconTitle = getChild(groupPosition, childPosition).getIconCache();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cache_child_item, null);
        }
        TextView t = (TextView) convertView.findViewById(R.id.namecacheapp);
        TextView s = (TextView) convertView.findViewById(R.id.sizecache);
        ImageView i = (ImageView) convertView.findViewById(R.id.iconcache);
        t.setText(headerTitle);
        if (sizeTitle > 1024) {
            sizeTitle = formatSize(sizeTitle);
            sizeTitle = tinhToan(sizeTitle);
            s.setText(sizeTitle + "MB");
        } else {
            sizeTitle = tinhToan(sizeTitle);
            s.setText(sizeTitle + "KB");
        }
        i.setImageDrawable(iconTitle);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private double tinhToan(double size) {
        long size1 = (long) (size * 10);
        double size2 = (size1 * 1.0 / 10);
        return size2;
    }
}
