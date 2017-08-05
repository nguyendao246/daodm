package com.news.supercleaner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.news.supercleaner.R;
import com.news.supercleaner.activity.thongtindienthoai.ToolsInsideActivity;
import com.news.supercleaner.activity.thongtindienthoai.unistall.UnistallApp;
import com.news.supercleaner.adapter.ResultVirusAdapter;
import com.news.supercleaner.model.ResutlVirus;

import java.util.ArrayList;

/**
 * Created by asus on 28/07/2017.
 */

public class FrgSafe extends Fragment implements AdapterView.OnItemClickListener {
    private static final String DATA_NAME = "DATA_NAME";
    private View rootView;
    private ArrayList<ResutlVirus> resutlViruses = new ArrayList<>();
    private ResultVirusAdapter resultVirusAdapter;
    private ListView listViewVirus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.safe_frg, container, false);
        initArray();

        return rootView;
    }

    private void initArray() {
        resutlViruses.add(new ResutlVirus(R.drawable.notification, "Too Many Notification?", "Try Notification Manager to get rid of trash notifications", "MANAGER"));
        resutlViruses.add(new ResutlVirus(R.drawable.memory, "App Manager", "Not use apps for a long time? Use App Manager to manager them!", "MANAGER"));
        listViewVirus = (ListView) rootView.findViewById(R.id.lvresultvirus);
        resultVirusAdapter = new ResultVirusAdapter(getContext(), resutlViruses);
        listViewVirus.setAdapter(resultVirusAdapter);

        listViewVirus.setOnItemClickListener(this);
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
}
