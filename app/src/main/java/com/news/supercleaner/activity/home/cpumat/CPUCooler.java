package com.news.supercleaner.activity.home.cpumat;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.fragment.FrgFan;


public class CPUCooler extends ActionBarActivity implements View.OnClickListener {
    private static final java.lang.String NAME = "NAME";
    private static final String TEMP = "TEMP";
    private ImageView imgBack;
    private TextView tvName;


    private String name;


    private float temp;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.cpucooler_activity);
        initView();

        Intent intent = this.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        temp = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;

        Bundle bundle = new Bundle();
        bundle.putInt(TEMP, (int) temp);
        showScreen(new FrgFan(), bundle, R.id.rl_cpu);
    }


    private void initView() {
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString(NAME);
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(name);
        imgBack.setOnClickListener(this);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }

    public void showScreen(Fragment fragment, Bundle bundle, int layoutId) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(layoutId, fragment)
                .commitAllowingStateLoss();
    }
}
