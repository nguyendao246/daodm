package com.news.supercleaner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.news.supercleaner.R;
import com.news.supercleaner.activity.home.cpumat.CPUCooler;
import com.news.supercleaner.activity.home.tangtoc.TangTocActivity;
import com.news.supercleaner.activity.home.taptinrac.CacheClean;
import com.news.supercleaner.activity.home.virus.VirusActivity;

/**
 * Created by asus on 21/07/2017.
 */

public class FrgHome extends Fragment implements View.OnClickListener {
    private static final String NAME = "NAME";
    private View rootView;
    private LinearLayout lnTinRac, lnTangToc, lnVirut, lnCPU;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_frg, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        lnTinRac = (LinearLayout) rootView.findViewById(R.id.ln_tin_rac);
        lnTangToc = (LinearLayout) rootView.findViewById(R.id.ln_tang_toc);
        lnVirut = (LinearLayout) rootView.findViewById(R.id.ln_virus);
        lnCPU = (LinearLayout) rootView.findViewById(R.id.ln_cpu);

        lnTinRac.setOnClickListener(this);
        lnTangToc.setOnClickListener(this);
        lnVirut.setOnClickListener(this);
        lnCPU.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ln_tin_rac:
                Intent intent = new Intent(getContext(), CacheClean.class);
                startActivity(intent);
                break;

            case R.id.ln_tang_toc:
                Intent intent1 = new Intent(getContext(), TangTocActivity.class);
                startActivity(intent1);
                break;

            case R.id.ln_virus:
                Intent intent2 = new Intent(getContext(), VirusActivity.class);
                intent2.putExtra(NAME,"Security Scanning");
                startActivity(intent2);
                break;

            case R.id.ln_cpu:
                Intent intent3 = new Intent(getContext(), CPUCooler.class);
                intent3.putExtra(NAME,"CPU Cooler");
                startActivity(intent3);
                break;
        }
    }
}
