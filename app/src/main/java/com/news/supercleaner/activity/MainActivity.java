package com.news.supercleaner.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.fragment.FrgHome;
import com.news.supercleaner.fragment.FrgMe;
import com.news.supercleaner.fragment.FrgTools;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    initHome();
                    return true;
                case R.id.navigation_tools:
                    initTools();
                    return true;
                case R.id.navigation_me:
                    initMe();
                    return true;
            }
            return false;
        }

    };

    private void initMe() {
        Bundle bundle = new Bundle();
        showScreen(new FrgMe(),bundle,R.id.content);
    }

    private void initTools() {
        Bundle bundle = new Bundle();
        showScreen(new FrgTools(),bundle,R.id.content);
    }

    private void initHome() {
        Bundle bundle = new Bundle();
        showScreen(new FrgHome(),bundle,R.id.content);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


    }


    public void showScreen(Fragment fragment, Bundle bundle, int layoutId) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(layoutId, fragment)
//                .addToBackStack(fragment.getClass().getName())
                .commitAllowingStateLoss();
    }
}
