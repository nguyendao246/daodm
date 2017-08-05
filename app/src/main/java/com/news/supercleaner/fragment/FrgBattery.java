package com.news.supercleaner.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.news.supercleaner.R;

/**
 * Created by asus on 04/08/2017.
 */

public class FrgBattery extends Fragment implements Runnable {
    public static boolean IS_RUNNING = true;
    int currentLoad = 0;
    private View rootView;
    private DonutProgress donutProgress;
    private Thread thread;
    private ImageView imgBattery;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                donutProgress.setProgress(currentLoad);
                if (currentLoad <= 12) {
                    imgBattery.setImageResource(R.drawable.ic_battery_6);
                } else if (currentLoad < 25 && currentLoad > 12) {
                    imgBattery.setImageResource(R.drawable.ic_battery_12);
                } else if (currentLoad == 25) {
                    imgBattery.setImageResource(R.drawable.ic_battery_25);
                } else if (currentLoad > 25 && currentLoad < 50) {
                    imgBattery.setImageResource(R.drawable.ic_battery_37);
                } else if (currentLoad == 50) {
                    imgBattery.setImageResource(R.drawable.ic_battery_50);
                } else if (currentLoad > 50 && currentLoad < 75) {
                    imgBattery.setImageResource(R.drawable.ic_battery_63);
                } else if (currentLoad == 75) {
                    imgBattery.setImageResource(R.drawable.ic_battery_75);
                } else if (currentLoad > 75 && currentLoad < 100) {
                    imgBattery.setImageResource(R.drawable.ic_battery_87);
                } else if (currentLoad == 100) {
                    imgBattery.setImageResource(R.drawable.ic_battery_100);
                }
                currentLoad++;
                if (currentLoad == 101) {
                    stopThread();
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frg_battery, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        donutProgress = (DonutProgress) rootView.findViewById(R.id.donut_progress);
        imgBattery = (ImageView) rootView.findViewById(R.id.img_battery);
        donutProgress.setProgress(currentLoad);
        donutProgress.setMax(100);
        thread = new Thread(this);
        startThread();
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
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
