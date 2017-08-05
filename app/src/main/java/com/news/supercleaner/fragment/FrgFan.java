package com.news.supercleaner.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.activity.home.cpumat.CPUCooler;
import com.news.supercleaner.adapter.ResultVirusAdapter;
import com.news.supercleaner.model.ResutlVirus;

import java.util.ArrayList;

/**
 * Created by asus on 28/07/2017.
 */

public class FrgFan extends Fragment implements Runnable {
    private static final String NAME = "NAME";
    private static final java.lang.String TEMP = "TEMP";
    private View rootView;
    private ResultVirusAdapter resultVirusAdapter;
    private ArrayList<ResutlVirus> resutlViruses = new ArrayList<>();
    private ListView listViewCooler;
    private Toolbar toolbar;
    private ImageView imgBack;
    private TextView tvName;

    private ImageView imageViewTick;
    private ImageView imageViewFan;
    private ImageView imgFan;
    private RelativeLayout layouticon;
    private RelativeLayout relativeLayout;


    private TextView tvInforCooler;
    private TextView tvXong;
    private ImageView imgTicksmall;


    // nhiet do nay t fix cung
    // đúng ra phải lấy nhiệt độ từ activity ban đầu, dùng itent để chuyển nhiệt độ qua acivity này
    private int temperature;

    private Animation animationFanScale;
    private Animation animationApearTick;
    private Animation animationFan;
    private Animation animationTranslateTick;
    private Animation animOval;
    private Animation translateAnimation;
    private Handler handler1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (temperature == 33) {
                setAnimationFanScale();
            }
            if (msg.what == 1) {
                temperature -= 1;
            }

        }
    };
    private float x1, y1;
    private float x, y;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fan_frg, container, false);
        if (getArguments() != null) {
            temperature = getArguments().getInt(TEMP);
        }
        Log.d("AINMKIW", "onCreateView: " + temperature);
        intView();
        initAni();
        initrTemp();
        return rootView;
    }

    // animation quay cánh quạt
    private void setAnimationFanScale() {
        imageViewFan.startAnimation(animationFanScale);
        animationFanScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setAnimationApearTick();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    // animation xuất hiện vòng tròn và vòng tích
    private void setAnimationApearTick() {
        relativeLayout.setVisibility(View.VISIBLE);
        imageViewTick.startAnimation(animationApearTick);
        layouticon.setVisibility(View.GONE);
//        imgFan.setVisibility(View.VISIBLE);
        tvInforCooler.setText("");
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {

                imgFan.setVisibility(View.VISIBLE);
                imgFan.startAnimation(animOval);
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvInforCooler.setVisibility(View.GONE);
                        imgFan.setVisibility(View.GONE);
                        tvXong.setVisibility(View.GONE);
                        setTranslateAnimation();
                    }
                }, 1000);

            }
        }, 2000);
    }

    // animation di chuyển lên trên
    private void setTranslateAnimation() {
        translateAnimation = new TranslateAnimation(0, x1-x, 0, y1- y);
        translateAnimation.setDuration(2000);
        imageViewTick.startAnimation(translateAnimation);
        translateAnimation.setFillAfter(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageViewTick.setVisibility(View.GONE);
                Bundle bundle = new Bundle();
                ((CPUCooler) getContext()).showScreen(new FrgSafe(), bundle, R.id.rl_cpu);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }



    private void intView() {
        imgTicksmall = (ImageView) rootView.findViewById(R.id.imgticksmall);
        layouticon = (RelativeLayout) rootView.findViewById(R.id.layouticon);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeLayout);
        tvXong = (TextView) rootView.findViewById(R.id.xong);


    }

    private void initrTemp() {
        tvInforCooler = (TextView) rootView.findViewById(R.id.textViewCooler);
        handler1 = new Handler();
//        toolbar = (Toolbar) findViewById(R.id.coolertoolbar);
//        setSupportActionBar(toolbar);

//        toolbar.setTitleTextColor(Color.WHITE);
//        getSupportActionBar().setTitle("CPU Cooler");
//        final Drawable upArrow = getResources().getDrawable(R.drawable.back);
//        upArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // registerReceiver(this.broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Thread thread = new Thread(this);
        thread.start();
//        animationApearTick.setAnimationListener(new Animation.AnimationListener() {
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                imageViewTick.startAnimation(animationTranslateTick);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }

    private void initAni() {

        animationFan = AnimationUtils.loadAnimation(getContext(), R.anim.rotatefananimation);
        animationFanScale = AnimationUtils.loadAnimation(getContext(), R.anim.fanfaded);
        imageViewTick = (ImageView) rootView.findViewById(R.id.tickcooler);
        imgFan = (ImageView) rootView.findViewById(R.id.fan);
        animationTranslateTick = AnimationUtils.loadAnimation(getContext(), R.anim.tickcooleranimation);
        animationApearTick = AnimationUtils.loadAnimation(getContext(), R.anim.tickapear);
        imageViewFan = (ImageView) rootView.findViewById(R.id.imgfan);
        imageViewFan.startAnimation(animationFan);
        animOval = AnimationUtils.loadAnimation(getContext(), R.anim.ovalanimatrion_one);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int[] toado1 = new int[2];
                imgTicksmall.getLocationOnScreen(toado1);
                x1 = toado1[0];
                y1 = toado1[1];

                int[] toado = new int[2];
                imageViewTick.getLocationOnScreen(toado);
                x = toado[0];
                y = toado[1];

            }
        },1000);
    }

    @Override
    public void run() {
        if (temperature < 32) {
            initFan();
        }
        while (temperature >= 32) {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initFan() {
        imageViewFan.startAnimation(animationFan);
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                setAnimationApearTick();
            }
        }, 3000);
    }
}
