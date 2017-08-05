package com.news.supercleaner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.news.supercleaner.R;
import com.news.supercleaner.activity.home.virus.VirusActivity;
import com.news.supercleaner.activity.thongtindienthoai.ToolsInsideActivity;
import com.news.supercleaner.activity.thongtindienthoai.unistall.UnistallApp;
import com.news.supercleaner.adapter.ResultVirusAdapter;
import com.news.supercleaner.model.ResutlVirus;

import java.util.ArrayList;

/**
 * Created by asus on 27/07/2017.
 */

public class FrgResultVirus extends Fragment implements AdapterView.OnItemClickListener, Runnable {
    private static final java.lang.String NAME = "NAME";
    private static final String DATA_NAME = "DATA_NAME";
    public static boolean IS_RUNNING = true;
    int currentLoad = 0;
    int k = 0;
    private View rootView;
    private ImageView imageViewSafe, iconSmall;
    private Animation rotateSafeAnimation, translateSafeAnimation;
    private LinearLayout relativeLayout;
    private LinearLayout linearLayout;
    private ResultVirusAdapter resultVirusAdapter;
    private ArrayList<ResutlVirus> resutlViruses = new ArrayList<>();
    private ListView listViewVirus;
    private TextView tv, tvPhatHien;
    private float x;
    private float y;
    private float x1, y1;
    private TranslateAnimation translateAnimation;
    private DonutProgress donutProgress;
    private Thread thread;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                donutProgress.setProgress(currentLoad);
                currentLoad++;
                if (currentLoad == 100) {
                    stopThread();
                }
            }
        }
    };
    private Handler handler1 = new Handler();
    private String name = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.resultvirus_frg, container, false);
        name = getArguments().getString(NAME);


        ((VirusActivity) getContext()).setTvName(name + "");
        initView();

        initAnim();
        return rootView;
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

    private void initAnim() {
        thread = new Thread(this);

        startThread();
        x1 = imageViewSafe.getX();
        y1 = imageViewSafe.getY();


        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                donutProgress.setVisibility(View.GONE);
                tv.setVisibility(View.GONE);
                tvPhatHien.setVisibility(View.GONE);
                translateAnimation = new TranslateAnimation(0, x1-x, 0, y1- y);//
                translateAnimation.setDuration(500);
                imageViewSafe.startAnimation(translateAnimation);
                translateAnimation.setFillAfter(true);
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resultVirusAdapter = new ResultVirusAdapter(getContext(), resutlViruses);
                        relativeLayout.setVisibility(View.GONE);
//                        iconSmall.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        listViewVirus.setAdapter(resultVirusAdapter);
                    }
                }, 500);
            }
        }, 100 * 10 + 100);


    }


    private void initView() {
        tv = (TextView) rootView.findViewById(R.id.textView);
        tvPhatHien = (TextView) rootView.findViewById(R.id.tv_phat_hien);
        donutProgress = (DonutProgress) rootView.findViewById(R.id.donut_progress);
        imageViewSafe = (ImageView) rootView.findViewById(R.id.imgsafe);
        relativeLayout = (LinearLayout) rootView.findViewById(R.id.layouticon);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.layoutsafe);
        listViewVirus = (ListView) rootView.findViewById(R.id.lvresultvirus);
        iconSmall = (ImageView) rootView.findViewById(R.id.imgticksmall);
        //di chuyen anh imageViewSafe dến iconSmall
        listViewVirus.setOnItemClickListener(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int[] toado1 = new int[2];
                iconSmall.getLocationOnScreen(toado1);
                x1 = toado1[0];
                y1 = toado1[1];

                int[] toado = new int[2];
                imageViewSafe.getLocationOnScreen(toado);
                x = toado[0];
                y = toado[1];


                Log.e("TUAN X = ", "" + toado[0]);
                Log.e("TUAN Y = ", "" + toado[1]);

                Log.e("TUAN X1 = ", "" + toado1[0]);
                Log.e("TUAN Y1 = ", "" + toado1[1]);
            }
        },1000);


        Log.d("KAKJFKA", "initView: " + x + " " + y);



        imageViewSafe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] values = new int[2];
                v.getLocationOnScreen(values);
                Log.d("KAKJFKA", "initView: " + values[0] + " " + values[1]);
                return true;
            }
        });

        initArray();


    }


    private void initArray() {
        resutlViruses.add(new ResutlVirus(R.drawable.notification, "Too Many Notification?", "Try Notification Manager to get rid of trash notifications", "MANAGER"));
        resutlViruses.add(new ResutlVirus(R.drawable.memory, "App Manager", "Not use apps for a long time? Use App Manager to manager them!", "MANAGER"));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
            Intent intent = new Intent(getContext(), ToolsInsideActivity.class);
            intent.putExtra(DATA_NAME, "Trình Dọn dẹp Thông báo");
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), UnistallApp.class);
            startActivity(intent);
        }
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
