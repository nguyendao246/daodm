package com.news.supercleaner.activity.home.taptinrac;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;


// sau khi liet ke cache xong hien ra acti đã boost xong
public class BoostComplete extends ActionBarActivity implements View.OnClickListener {
    private ImageView imageView, cleanDone;
    private Toolbar toolbar;
    private Animation roll, animTick;
    private int totalClean;
    private TextView sizeClean;
    private Button btok;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cachecomplete);
        init();
        intiAnim();
    }

    private void intiAnim() {
        roll.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setVisibility(View.INVISIBLE);
                cleanDone.setVisibility(View.VISIBLE);
                cleanDone.startAnimation(animTick);
                btok.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void init() {
        totalClean = getIntent().getIntExtra("key", 0);
        imageView = (ImageView) findViewById(R.id.loading);
        sizeClean = (TextView) findViewById(R.id.sizecleaned);
        btok = (Button) findViewById(R.id.btcleanok);
        roll = AnimationUtils.loadAnimation(this, R.anim.rotatecleananimation);
        cleanDone = (ImageView) findViewById(R.id.cleandone);
        animTick = AnimationUtils.loadAnimation(this, R.anim.tickapear);
        toolbar = (Toolbar) findViewById(R.id.completetoolbar);
        imageView.startAnimation(roll);


        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Junk files");
        final Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        sizeClean.setText(totalClean + "MB");
        sizeClean.setTextColor(Color.BLACK);

        btok.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btcleanok:{
                finish();
                break;
            }
        }
    }
}
