package com.news.supercleaner.fragment;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.news.supercleaner.R;
import com.news.supercleaner.activity.home.virus.VirusActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 27/07/2017.
 */

public class FrgSweep extends Fragment implements Runnable {
    private static final String TAG = FrgSweep.class.getSimpleName();
    private static final java.lang.String NAME = "NAME";
    int currentLoad = 0;
    int k = 0;
    private View rootView;
    private ArrayList<String> appNameInstalled = new ArrayList<>();
    private TextView tvScannApp;
    private ArcProgress arcProgress;
    private ImageView imgMalware, imgPrivacy, ovalMalware, ovalPrivacy;
    private TextView numberMalware, numberPrivacy;
    private Animation animOval;
    private Thread thread;
    public static boolean IS_RUNNING = true;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                arcProgress.setProgress(currentLoad);
                if (currentLoad % 2 == 0 && k < appNameInstalled.size()) {
                    tvScannApp.setText("Scanning: " + appNameInstalled.get(k));
                    k++;
                }
                currentLoad++;
                if (currentLoad == 1) {
                    ovalMalware.startAnimation(animOval);
                }
                if (currentLoad == 77) {
                    numberMalware.setVisibility(View.VISIBLE);
                    imgMalware.setVisibility(View.INVISIBLE);
                    ovalMalware.clearAnimation();
                    ovalPrivacy.startAnimation(animOval);
                }
                if (currentLoad == 100) {
                    ovalPrivacy.clearAnimation();
                    numberPrivacy.setVisibility(View.VISIBLE);
                    imgPrivacy.setVisibility(View.INVISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        Bundle bundle = new Bundle();
                        bundle.putString(NAME, "Results");
                        ((VirusActivity) getContext()).showScreen(new FrgResultVirus(), bundle, R.id.rl_virut);

                    }
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sweep_frg, container, false);
        getInstalledApps(false);
        initAnim();
        initView();
        return rootView;
    }

    private void initAnim() {
        animOval = AnimationUtils.loadAnimation(getContext(), R.anim.ovalanimatrion);
    }

    private void initView() {
        tvScannApp = (TextView) rootView.findViewById(R.id.tvscanapp);
        arcProgress = (ArcProgress) rootView.findViewById(R.id.arc_progress);
        imgMalware = (ImageView) rootView.findViewById(R.id.imgmalware);
        imgPrivacy = (ImageView) rootView.findViewById(R.id.imgprivacy);
        ovalMalware = (ImageView) rootView.findViewById(R.id.ovalmalware);
        ovalPrivacy = (ImageView) rootView.findViewById(R.id.ovalprivacy);
        numberMalware = (TextView) rootView.findViewById(R.id.numbermalware);
        numberPrivacy = (TextView) rootView.findViewById(R.id.numberprivacy);
        ovalMalware.startAnimation(animOval);
        thread = new Thread(this);

        startThread();
    }




    private void getInstalledApps(boolean getSysPackages) {

        List<PackageInfo> packs = getContext().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (p.versionName == null)) {
                continue;
            }
            appNameInstalled.add(p.applicationInfo.loadLabel(getContext().getPackageManager()).toString());
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
        stopThread();

    }

    public void startThread() {
        if (thread != null && thread.isAlive()) {
            return;
        }
        if (thread == null) {
            thread = new Thread(this);
        }
        IS_RUNNING = true;
        if (!thread.isAlive()) {
            thread.start();
        }
    }

    public void stopThread() {
        IS_RUNNING = false;
        thread = null;
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
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
