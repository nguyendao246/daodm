package com.news.supercleaner.activity.home.virus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.fragment.FrgSweep;

public class VirusActivity extends AppCompatActivity implements View.OnClickListener {
    private static final java.lang.String NAME = "NAME";
    private ImageView imgBack;
    private TextView tvName;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.antivirus_activity);
        name = getIntent().getExtras().getString(NAME);
        Bundle bundle = new Bundle();
        showScreen(new FrgSweep(),bundle, R.id.rl_virut);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText(name);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
    }




    public void showScreen(Fragment fragment, Bundle bundle, int layoutId) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(layoutId, fragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    public void setTvName(String name){
        tvName.setText(name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                finish();
                break;
            default:
                break;
        }
    }
}
