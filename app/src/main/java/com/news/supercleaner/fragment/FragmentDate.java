package com.news.supercleaner.fragment;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.news.supercleaner.R;
import com.news.supercleaner.activity.thongtindienthoai.unistall.UnistallApp;
import com.news.supercleaner.adapter.UninstallAdapter;
import com.news.supercleaner.model.UnistallAppItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class FragmentDate extends Fragment {
    private static final long KILOBYTE = 1024;
    private Toolbar toolbar;
    private ListView listView;
    private Button button;
    private ArrayList<UnistallAppItem> unistallAppItems = new ArrayList<>();
    private UninstallAdapter uninstallAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.uninstapp1, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
//        toolbar = (Toolbar) findViewById(R.id.uninstalltoolbar);
        listView = (ListView) getActivity().findViewById(R.id.unistalllisview1);
        button = (Button) getActivity().findViewById(R.id.bt_uninstall_date);
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(Color.WHITE);
//        getSupportActionBar().setTitle("Unistall App");
//        final Drawable upArrow = getResources().getDrawable(R.drawable.back);
//        upArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);



        initArr();
        uninstallAdapter = new UninstallAdapter(getActivity(), unistallAppItems);
        listView.setAdapter(uninstallAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < unistallAppItems.size(); i++) {
                    UnistallAppItem unistallAppItem = unistallAppItems.get(i);
                    if (unistallAppItem.isPick() == true) {
                        Uri packageURI = Uri.parse("package:" + unistallAppItem.getPackagename());
                        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                        // forresultde kiem tra -i

                        startActivity(uninstallIntent);

                    }
                }
                initArr();
                uninstallAdapter.notifyDataSetChanged();
            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.bt_uninstall_date: {
//                        for (int i = 0; i < unistallAppItems.size(); i++) {
//                            UnistallAppItem unistallAppItem = unistallAppItems.get(i);
//                            if (unistallAppItem.isPick() == true) {
//                                Uri packageURI = Uri.parse("package:" + unistallAppItem.getPackagename());
//                                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
//                                // forresultde kiem tra -i
//
//                                startActivity(uninstallIntent);
//
//                            }
//                        }
//                        initArr();
//                        uninstallAdapter.notifyDataSetChanged();
//                        break;
//                    }
//                }
//            }
//        });
    }

    private void initArr() {
        List<ApplicationInfo> installedApps = new ArrayList<>();
        PackageManager pm = getActivity().getPackageManager();
        unistallAppItems.clear();
        installedApps.addAll(getApplicationInfoInstalled(pm));
        for (int i = 0; i < installedApps.size(); i++) {
            ApplicationInfo app = installedApps.get(i);
            if (app.packageName.equals(getContext().getPackageName()) == false)
                try {
                    unistallAppItems.add(new UnistallAppItem(pm.getApplicationLabel(app).toString(), pm.getApplicationIcon(app),
                            false, app.packageName, pm.getPackageInfo(app.packageName, 0).firstInstallTime, getSize(app.packageName)));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
        }
        Log.d("KJKKJKJKJKQU", "initArr1: "+unistallAppItems.size());

        for (int i = 0; i < unistallAppItems.size() - 1; i++) {
            for (int j = i + 1; j < unistallAppItems.size(); j++) {
                if (unistallAppItems.get(i).getDateInstall() < unistallAppItems.get(j).getDateInstall()) {
                    Collections.swap(unistallAppItems, i, j);
                }
            }
        }
    }

    private List<ApplicationInfo> getApplicationInfoInstalled(PackageManager pm) {
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        List<ApplicationInfo> installedApps = new ArrayList<>();

        for (ApplicationInfo app : apps) {
            if (checkAppInfo(app)) installedApps.add(app);
        }
        return installedApps;
    }

    private boolean checkAppInfo(ApplicationInfo app) {
        if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
            //it's a system app, not interested
        } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            //Discard this one
            //in this case, it should be a user-installed app
            // installedApps.add(app);
            return false;
        }
        return true;
    }

    private long getSize(String packageName) {
        try {
            return new File(getContext().getPackageManager().getApplicationInfo(
                    packageName, 0).publicSourceDir).length();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnuninstall: {
//                for (int i = 0; i < boostBackgrounds.size(); i++) {
//                    BoostBackground boostBackground = boostBackgrounds.get(i);
//                    if (boostBackground.isPick() == true) {
//                        Uri packageURI = Uri.parse("package:" + boostBackground.getPackagename());
//                        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
//                        startActivity(uninstallIntent);
//                        boostBackgrounds.remove(i);
//                        i--;
//                    }
//                }
//                boostBackgroundAdapter.notifyDataSetChanged();
//                break;
//            }
//        }
//    }
}
