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
import com.news.supercleaner.activity.home.virus.VirusActivity;
import com.news.supercleaner.activity.thongtindienthoai.ToolsInsideActivity;
import com.news.supercleaner.customradar.RadarView;
import com.news.supercleaner.model.UnistallAppItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by asus on 31/07/2017.
 */

public class FrgTietKiemPinOne extends Fragment implements Runnable {
    public static boolean IS_RUNNING = true;
    int currentLoad = 0;
    RadarView mRadarView = null;
    private View rootView;
    private ArrayList<UnistallAppItem> unistallAppItems = new ArrayList<>();
    private ImageView img_one, img_two, img_three, img_four, img_five, img_six;
    private ArrayList<Drawable> icon = new ArrayList<>();
    private Thread thread;
    private int k = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (currentLoad % 2 == 0 && k < icon.size()) {
                    if (k == 0 || (k - 0) % 6 == 0) {
                        img_one.setImageDrawable(icon.get(k));
                        img_one.setVisibility(View.VISIBLE);
                        img_two.setVisibility(View.INVISIBLE);
                        img_three.setVisibility(View.INVISIBLE);
                        img_four.setVisibility(View.INVISIBLE);
                        img_five.setVisibility(View.INVISIBLE);
                        img_six.setVisibility(View.INVISIBLE);
                    } else if (k == 1 || (k - 1) % 6 == 0) {
                        img_two.setImageDrawable(icon.get(k));

                        img_one.setVisibility(View.INVISIBLE);
                        img_two.setVisibility(View.VISIBLE);
                        img_three.setVisibility(View.INVISIBLE);
                        img_four.setVisibility(View.INVISIBLE);
                        img_five.setVisibility(View.INVISIBLE);
                        img_six.setVisibility(View.INVISIBLE);

                    } else if (k == 2 || (k - 2) % 6 == 0) {
                        img_three.setImageDrawable(icon.get(k));
                        img_one.setVisibility(View.INVISIBLE);
                        img_two.setVisibility(View.INVISIBLE);
                        img_three.setVisibility(View.VISIBLE);
                        img_four.setVisibility(View.INVISIBLE);
                        img_five.setVisibility(View.INVISIBLE);
                        img_six.setVisibility(View.INVISIBLE);
                    } else if (k == 3 || (k - 3) % 6 == 0) {
                        img_four.setImageDrawable(icon.get(k));
                        img_one.setVisibility(View.INVISIBLE);
                        img_two.setVisibility(View.INVISIBLE);
                        img_three.setVisibility(View.INVISIBLE);
                        img_four.setVisibility(View.VISIBLE);
                        img_five.setVisibility(View.INVISIBLE);
                        img_six.setVisibility(View.INVISIBLE);
                    } else if ((k == 4) || (k - 4) % 6 == 0) {
                        img_five.setImageDrawable(icon.get(k));
                        img_one.setVisibility(View.INVISIBLE);
                        img_two.setVisibility(View.INVISIBLE);
                        img_three.setVisibility(View.INVISIBLE);
                        img_four.setVisibility(View.INVISIBLE);
                        img_five.setVisibility(View.VISIBLE);
                        img_six.setVisibility(View.INVISIBLE);
                    } else if (k == 5 || (k - 5) % 6 == 0) {
                        img_six.setImageDrawable(icon.get(k));
                        img_one.setVisibility(View.INVISIBLE);
                        img_two.setVisibility(View.INVISIBLE);
                        img_three.setVisibility(View.INVISIBLE);
                        img_four.setVisibility(View.INVISIBLE);
                        img_five.setVisibility(View.INVISIBLE);
                        img_six.setVisibility(View.VISIBLE);
                    }
                    k++;
                }
                currentLoad++;
                if (currentLoad == 1) {
                    if (mRadarView != null) mRadarView.startAnimation();
                }
                if (currentLoad == 40) {
                    Bundle bundle = new Bundle();
                    ((FrgTietKiemPin) getParentFragment()).showScreen(new FrgTietKiemPinTwo(), bundle, R.id.rl_tiet_kiem);
                    if (mRadarView != null) mRadarView.stopAnimation();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frg_tiet_kiem_pin_one, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        mRadarView = (RadarView) rootView.findViewById(R.id.radarView);
        mRadarView.setShowCircles(true);
//        if (mRadarView != null) mRadarView.startAnimation();


        img_one = (ImageView) rootView.findViewById(R.id.image_one);
        img_two = (ImageView) rootView.findViewById(R.id.image_two);
        img_three = (ImageView) rootView.findViewById(R.id.image_three);
        img_four = (ImageView) rootView.findViewById(R.id.image_four);
        img_five = (ImageView) rootView.findViewById(R.id.image_five);
        img_six = (ImageView) rootView.findViewById(R.id.image_six);

        initArr();
        thread = new Thread(this);
        thread.start();


    }


    // tìm ảnh các app cài đặt trong máy
    private void initArr() {
        List<ApplicationInfo> installedApps = new ArrayList<>();
        PackageManager pm = getActivity().getPackageManager();
        unistallAppItems.clear();
        icon.clear();
        installedApps.addAll(getApplicationInfoInstalled(pm));
        for (int i = 0; i < installedApps.size(); i++) {
            ApplicationInfo app = installedApps.get(i);
            if (app.packageName.equals(getContext().getPackageName()) == false) {
                Drawable a = app.loadIcon(pm);
                icon.add(a);
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

    public ArrayList<Drawable> getIcon() {
        return icon;
    }

    @Override
    public void run() {
        while (currentLoad <= 100) {
            if (IS_RUNNING == false) {
                return;
            }
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
