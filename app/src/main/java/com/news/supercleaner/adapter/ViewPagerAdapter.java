package com.news.supercleaner.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.news.supercleaner.fragment.FragmentDate;
import com.news.supercleaner.fragment.FragmentSize;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0: {
                fragment = new FragmentSize();
                break;
            }
            case 1: {
                fragment = new FragmentDate();
                break;
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:{
                title = "Size";
                break;
            }
            case 1 :{
                title = "Date";
                break;
            }
        }
        return title;
    }
}
