package com.news.supercleaner.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.activity.thongtindienthoai.ToolsInsideActivity;
import com.news.supercleaner.customradar.RadarView;
import com.news.supercleaner.model.UnistallAppItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by asus on 31/07/2017.
 */

public class FrgTietKiemPin extends Fragment implements View.OnClickListener {
    private View rootView;
    private ImageView imgBack;
    private TextView tvName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frg_tiet_kiem_pin, container, false);
        initView();
        return rootView;
    }

    private void initView() {

        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);

        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvName.setText("Battery Saver");

        Bundle bundle = new Bundle();
        showScreen(new FrgTietKiemPinOne(), bundle, R.id.rl_tiet_kiem);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                ((ToolsInsideActivity) getContext()).finishAct();
                break;
        }
    }

    public void showScreen(Fragment fragment, Bundle bundle, int layoutId) {
        fragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().replace(layoutId, fragment)
                .commitAllowingStateLoss();
    }

}
