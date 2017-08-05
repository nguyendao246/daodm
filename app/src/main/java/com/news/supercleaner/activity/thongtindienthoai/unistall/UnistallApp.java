package com.news.supercleaner.activity.thongtindienthoai.unistall;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.adapter.UninstallAdapter;
import com.news.supercleaner.adapter.ViewPagerAdapter;
import com.news.supercleaner.model.UnistallAppItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class UnistallApp extends FragmentActivity implements View.OnClickListener {
    private TextView totalNumberApp;
    private TextView totalSize;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private Button mButton;
    private ImageView imgBack;
    private TextView tvName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filemanager);
        initView();
    }

    private void initView() {
        totalNumberApp = (TextView) findViewById(R.id.appmanagernumberofapp);
        mButton = (Button) findViewById(R.id.bt_uninstall);
        totalSize = (TextView) findViewById(R.id.filemanagersize);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPagerAdapter = new ViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(viewPagerAdapter);
        totalNumberApp.setText(getTotalApp() + "");
        totalSize.setText(getSize());

        imgBack = (ImageView) findViewById(R.id.img_back);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText("APP Manager");

        imgBack.setOnClickListener(this);


    }


    private int getTotalApp() {
        PackageManager pm = this.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        List<ApplicationInfo> installedApps = new ArrayList<>();

        for (ApplicationInfo app : apps) {
            if (checkAppInfo(app) && app.packageName.equals(getPackageName()) == false)
                installedApps.add(app);
        }
        return installedApps.size();
    }

    private boolean checkAppInfo(ApplicationInfo app) {
        if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            return false;
        }
        return true;
    }

    private String getSize() {
        PackageManager pm = this.getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        List<ApplicationInfo> installedApps = new ArrayList<>();
        long size = 0;
        for (ApplicationInfo app : apps) {
            if (checkAppInfo(app) && app.packageName.equals(getPackageName()) == false) {
                try {
                    long sizetmp = new File(this.getPackageManager().getApplicationInfo(
                            app.packageName, 0).publicSourceDir).length();
                    size += sizetmp;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        double lastValue = (double) size * 1.0;
        return sizeApp(lastValue);

    }

    private String sizeApp(double size) {
        double mb = size / 1024 / 1024;
        double gb = mb / 1024;
        if (gb > 1) {
            int x = (int) (gb * 100);
            return (double) x * 1.0 / 100 + " GB";
        } else if (mb > 1) {
            int x = (int) (mb * 100);
            return (double) x * 1.0 / 100 + " MB";
        } else {
            int x = (int) (mb * 100 * 1024);
            return (double) x * 1.0 / 100 + " KB";
        }

    }

    public Button getmButton() {
        return mButton;
    }




    private List<ApplicationInfo> getApplicationInfoInstalled(PackageManager pm) {
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        List<ApplicationInfo> installedApps = new ArrayList<>();

        for (ApplicationInfo app : apps) {
            if (checkAppInfo(app)) installedApps.add(app);
        }
        return installedApps;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
}
