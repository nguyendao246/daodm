package com.news.supercleaner.activity.home.tangtoc;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.adapter.AdapterCongCu;
import com.news.supercleaner.adapter.AdapterTangToc;
import com.news.supercleaner.model.ItemTangToc;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by asus on 21/07/2017.
 */

public class TangTocActivity extends Activity implements View.OnClickListener {
    private ListView listView;
    private AdapterCongCu adapter;
    private ArrayList<ItemTangToc> list = new ArrayList<>();
    private ImageView imgBack;
    private TextView tvName;
    private AdapterTangToc adapterTangToc;
    private TextView tvNumber;
    private TextView tvSoLan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.ThemeTangToc);
        setContentView(R.layout.activity_tang_toc);
        initList();
        initView();
    }

    private void initList() {

    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_tang_toc);
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText("Phone Boost");
        tvNumber = (TextView) findViewById(R.id.tv_number);
//        tvSoLan = (TextView) findViewById(R.id.tv_so_lan);

        imgBack.setOnClickListener(this);
        getApp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void getApp() {
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                Drawable a = pm.getApplicationIcon(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                Log.w("LABEL", c.toString());
//                long file_size = (pm.cacheSize + packageStats.externalCacheSize)/1024;
                ItemTangToc item = new ItemTangToc(a, c.toString(), getSize(info.processName));
                list.add(item);
            } catch (Exception e) {
                //Name Not FOund Exception
            }
        }

        adapterTangToc = new AdapterTangToc(this, list);
        listView.setAdapter(adapterTangToc);
        tvNumber.setText(list.size()+ " apps " + getString(R.string.content_tang_toc));

//        tvSoLan.setText(" times faster");

    }


    private long getSize(String packageName) {
        try {
            return new File(this.getPackageManager().getApplicationInfo(
                    packageName, 0).publicSourceDir).length();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
