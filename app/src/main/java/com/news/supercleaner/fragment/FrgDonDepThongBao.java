package com.news.supercleaner.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.activity.thongtindienthoai.ToolsInsideActivity;

/**
 * Created by asus on 01/08/2017.
 */

public class FrgDonDepThongBao extends Fragment implements View.OnClickListener {
    private View rootView;
    private ImageView imgBack;
    private TextView tvName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frg_don_dep_thong_bao, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvName.setText("Notification Cleaner");

        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                ((ToolsInsideActivity) getContext()).finishAct();
                break;
        }
    }
}
