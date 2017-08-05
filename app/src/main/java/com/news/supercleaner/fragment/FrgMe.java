package com.news.supercleaner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.news.supercleaner.R;
import com.news.supercleaner.adapter.AdapterCongCu;
import com.news.supercleaner.model.ItemCongCu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 21/07/2017.
 */

public class FrgMe extends Fragment {
    private View rootView;
    private ListView lvMeOne, lvMeTwo, lvMeThree;
    private AdapterCongCu adapterOne, adapterTwo, adapterThree;
    private List<ItemCongCu> listOne = new ArrayList<>();
    private List<ItemCongCu> listTwo = new ArrayList<>();
    private List<ItemCongCu> listThree = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.me_frg, container, false);
        initList();
        initView();
        return rootView;
    }

    private void initView() {
        listOne.add(new ItemCongCu(R.drawable.ic_battery_charging, "Super Battery"));
        listTwo.add(new ItemCongCu(R.drawable.ic_dd, "Clean Data"));
        listThree.add(new ItemCongCu(R.drawable.ic_settings, "Settings"));
        listThree.add(new ItemCongCu(R.drawable.ic_arrow_up, "Update"));
        listThree.add(new ItemCongCu(R.drawable.ic_message_processing, "Feedback"));
        listThree.add(new ItemCongCu(R.drawable.ic_help, "About"));
    }

    private void initList() {
        lvMeOne = (ListView) rootView.findViewById(R.id.lv_me_one);
        lvMeTwo = (ListView) rootView.findViewById(R.id.lv_me_two);
        lvMeThree = (ListView) rootView.findViewById(R.id.lv_me_three);

        adapterOne = new AdapterCongCu(getContext(), listOne);
        adapterTwo = new AdapterCongCu(getContext(), listTwo);
        adapterThree = new AdapterCongCu(getContext(), listThree);

        lvMeOne.setAdapter(adapterOne);
        lvMeTwo.setAdapter(adapterTwo);
        lvMeThree.setAdapter(adapterThree);
    }
}
