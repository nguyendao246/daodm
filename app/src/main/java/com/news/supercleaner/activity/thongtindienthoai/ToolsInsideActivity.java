package com.news.supercleaner.activity.thongtindienthoai;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.news.supercleaner.R;
import com.news.supercleaner.activity.thongtindienthoai.unistall.UnistallApp;
import com.news.supercleaner.fragment.FrgDonDepThongBao;
import com.news.supercleaner.fragment.FrgThongTinThietBi;
import com.news.supercleaner.fragment.FrgTietKiemPin;


public class ToolsInsideActivity extends AppCompatActivity {
    private static final String DATA_NAME = "DATA_NAME";
    private static final String DATA_NAME_ONE = "DATA_NAME_ONE";
    private String name = "";
    public static String nameOne = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_inside);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString(DATA_NAME);
        Log.d("AIHYB", "onCreate: " + name);
        initView();
    }

    private void initView() {
        nameOne = name + "";
        if (name.equals("Thông tin Thiết bị")) {
            Bundle bundle = new Bundle();
            showScreen(new FrgThongTinThietBi(), bundle, R.id.ln);
        } else if (name.equals("Trình Dọn dẹp Thông báo")) {
            Bundle bundle = new Bundle();
            showScreen(new FrgDonDepThongBao(), bundle, R.id.ln);
        } else if (name.equals("Khóa ứng dụng")) {

        } else if (name.equals("Trình Tiết kiệm Pin")) {
            Bundle bundle = new Bundle();
            showScreen(new FrgTietKiemPin(), bundle, R.id.ln);
        } else if (name.equals("Quản lý ứng dụng")) {
            Intent intent = new Intent(this, UnistallApp.class);

        } else if (name.equals("Dọn Tập tin Lớn")) {

        }

    }

    public void showScreen(Fragment fragment, Bundle bundle, int layoutId) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(layoutId, fragment)
//                .addToBackStack(fragment.getClass().getName())
                .commitAllowingStateLoss();
    }

    public void finishAct(){
        finish();
    }
}
