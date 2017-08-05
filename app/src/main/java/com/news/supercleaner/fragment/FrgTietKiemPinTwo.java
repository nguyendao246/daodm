package com.news.supercleaner.fragment;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.adapter.AdapterBattery;
import com.news.supercleaner.model.ItemBattery;
import com.news.supercleaner.model.UnistallAppItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * Created by asus on 31/07/2017.
 */

public class FrgTietKiemPinTwo extends Fragment implements View.OnClickListener {
    private View rootView;
    private ImageView imgBattery;
    private TextView batteryTxt;
    private TextView tvTestThu;
    private ArrayList<String> listName = new ArrayList<>();
    private ArrayList<String> listPackageName = new ArrayList<>();
    private ArrayList<String> listPackageNameProcessInfo = new ArrayList<>();
    private ArrayList<String> listNameProcessInfo = new ArrayList<>();
    private ArrayList<UnistallAppItem> unistallAppItems = new ArrayList<>();

    private AdapterBattery adapterBattery;
    private ListView listView;
    private ArrayList<ItemBattery> list = new ArrayList<>();
    private Button btTietKiem;


    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryTxt.setText(String.valueOf(level) + "%");
            if (level <= 12) {
                imgBattery.setImageResource(R.drawable.ic_battery_6);
            } else if (level < 25 && level > 12) {
                imgBattery.setImageResource(R.drawable.ic_battery_12);
            } else if (level == 25) {
                imgBattery.setImageResource(R.drawable.ic_battery_25);
            } else if (level > 25 && level < 50) {
                imgBattery.setImageResource(R.drawable.ic_battery_37);
            } else if (level == 50) {
                imgBattery.setImageResource(R.drawable.ic_battery_50);
            } else if (level > 50 && level < 75) {
                imgBattery.setImageResource(R.drawable.ic_battery_63);
            } else if (level == 75) {
                imgBattery.setImageResource(R.drawable.ic_battery_75);
            } else if (level > 75 && level < 100) {
                imgBattery.setImageResource(R.drawable.ic_battery_87);
            } else if (level == 100) {
                imgBattery.setImageResource(R.drawable.ic_battery_100);
            }
        }

    };


    public static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager
                = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frg_tiet_kiem_pin_two, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        listView = (ListView) rootView.findViewById(R.id.listView);
        btTietKiem = (Button) rootView.findViewById(R.id.bt_tiet_kiem);


        imgBattery = (ImageView) rootView.findViewById(R.id.img_battery);
        batteryTxt = (TextView) rootView.findViewById(R.id.tv_phan_tram);
        // hiện phần trăm pin
        this.getContext().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//        tvTestThu.setText(getTopAppName(getContext()));


        ActivityManager am = (ActivityManager) getContext().getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

//        for (ActivityManager.RunningAppProcessInfo recentTask : runningAppProcessInfo) {
//            try {
//                String packageName = recentTask.;
////                String packageName = recentTask..getComponent().getPackageName();
//                ApplicationInfo appInfo = getContext().getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
//                String label = getContext().getPackageManager().getApplicationLabel(appInfo).toString();
//                Log.d("FKJKJFKAJFK", label);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

        getApp();
//        initArr();

        Log.d("KJKKJKJKJKQU", "()()" + listNameProcessInfo.size());
        btTietKiem.setOnClickListener(this);
    }

    private void getApp() {
        ActivityManager am = (ActivityManager) getContext().getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = getContext().getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                Drawable a = pm.getApplicationIcon(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                Log.w("LABEL", c.toString());
                listNameProcessInfo.add(c.toString());
                ItemBattery item = new ItemBattery(a, c.toString(), "20%");
                list.add(item);
            } catch (Exception e) {
                //Name Not FOund Exception
            }
        }

        adapterBattery = new AdapterBattery(getContext(), list);
        listView.setAdapter(adapterBattery);
    }

    private void initArr() {
        List<ApplicationInfo> installedApps = new ArrayList<>();
        PackageManager pm = getActivity().getPackageManager();
        unistallAppItems.clear();
        listName.clear();
        listPackageName.clear();
        installedApps.addAll(getApplicationInfoInstalled(pm));
        for (int i = 0; i < installedApps.size(); i++) {
            ApplicationInfo app = installedApps.get(i);
            String name = pm.getApplicationLabel(app).toString();
            listName.add(name);
            String packagename = app.packageName;
            listPackageName.add(packagename);
        }

        for (int i = 0; i < listPackageName.size(); i++) {
//            Log.d("KJKKJKJKJKQU", "*_*: "+listPackageName.get(i));
            for (int j = 0; j < listPackageNameProcessInfo.size(); j++) {
                if (listPackageNameProcessInfo.get(j).equals(listPackageName.get(i))) {
                    listPackageName.add(listName.get(i));
                }
            }
        }
//        for (int i = 0; i < listNameProcessInfo.size(); i++) {
//            Log.d("KJKKJKJKJKQU", "*_*: "+listNameProcessInfo.get(i));
//        }


        Log.d("KJKKJKJKJKQU", "initArr: " + listName.size());
        Log.d("KJKKJKJKJKQU", "initArr: " + listPackageName.size());

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_tiet_kiem:
                Bundle bundle = new Bundle();
                ((FrgTietKiemPin) getParentFragment()).showScreen(new FrgBattery(), bundle, R.id.rl_tiet_kiem);
                break;
        }
    }
}
