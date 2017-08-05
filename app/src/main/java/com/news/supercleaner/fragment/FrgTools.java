package com.news.supercleaner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.news.supercleaner.R;
import com.news.supercleaner.activity.thongtindienthoai.ToolsInsideActivity;
import com.news.supercleaner.activity.thongtindienthoai.unistall.UnistallApp;

/**
 * Created by asus on 21/07/2017.
 */

public class FrgTools extends Fragment implements View.OnClickListener {
    private static final String DATA_NAME = "DATA_NAME";
    private View rootView;
    private LinearLayout lnThongTin, lnTrinhDonDep, lnKhoaUD, lnTietKiemPin;
    private LinearLayout lnQuanLy, lnDonTapTin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tools_frg, container, false);
        initView();
        return rootView;
    }


    private void initView() {
        lnThongTin = (LinearLayout) rootView.findViewById(R.id.ln_thong_tin);
        lnTrinhDonDep = (LinearLayout) rootView.findViewById(R.id.ln_trinh_don_dep);
        lnTietKiemPin = (LinearLayout) rootView.findViewById(R.id.ln_tiet_kiem_pin);
        lnQuanLy = (LinearLayout) rootView.findViewById(R.id.ln_quan_ly);
        lnDonTapTin = (LinearLayout) rootView.findViewById(R.id.ln_don_tap_tin);

        lnThongTin.setOnClickListener(this);
        lnTrinhDonDep.setOnClickListener(this);
        lnTietKiemPin.setOnClickListener(this);
        lnQuanLy.setOnClickListener(this);
        lnDonTapTin.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ln_thong_tin:
                Intent intent = new Intent(getContext(), ToolsInsideActivity.class);
                intent.putExtra(DATA_NAME, "Thông tin Thiết bị");
                startActivity(intent);
                break;

            case R.id.ln_trinh_don_dep:
                Intent intent1 = new Intent(getContext(), ToolsInsideActivity.class);
                intent1.putExtra(DATA_NAME, "Trình Dọn dẹp Thông báo");
                startActivity(intent1);
                break;


            case R.id.ln_tiet_kiem_pin:
                Intent intent3 = new Intent(getContext(), ToolsInsideActivity.class);
                intent3.putExtra(DATA_NAME, "Trình Tiết kiệm Pin");
                startActivity(intent3);
                break;

            case R.id.ln_quan_ly:
                Intent intent4 = new Intent(getContext(), UnistallApp.class);;
                startActivity(intent4);
                break;

            case R.id.ln_don_tap_tin:
                break;

            default:
                break;
        }
    }
}
