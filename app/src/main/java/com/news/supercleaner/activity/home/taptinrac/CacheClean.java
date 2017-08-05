package com.news.supercleaner.activity.home.taptinrac;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.news.supercleaner.R;
import com.news.supercleaner.adapter.CacheApdapter;
import com.news.supercleaner.model.CacheChild;
import com.news.supercleaner.model.CacheGroup;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;




public class CacheClean extends ActionBarActivity implements View.OnClickListener {
    private Button button;
    private ExpandableListView expandableListView;
    private CacheApdapter cacheApdapter;
    private ArrayList<CacheGroup> cacheGroups = new ArrayList<>();
    private HashMap<CacheGroup, List<CacheChild>> cacheGroupListHashMap = new HashMap<>();
    private int totalClean = 0;
    private ImageView imgBack;
    private TextView tvName;

    private HashMap<String, ApplicationInfo> mAppInfo;

    ArrayList<CacheChild> arrayList = new ArrayList<>();
    private ProgressDialog dialog;


    private ArrayList<File> fileArrayList = new ArrayList<>();// file can xoa

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cacheacivity);
        initViewActionBar();

        new getCacheTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //initArr();
    }

    public class getCacheTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(CacheClean.this, "", "Wait a moment...", true, false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PackageManager packageManager = getPackageManager();
            mAppInfo = new HashMap<>();
            List<ApplicationInfo> packs = packageManager.getInstalledApplications(PackageManager.GET_GIDS);

            for (int i = 0; i < packs.size(); i++) {
                ApplicationInfo p = packs.get(i);
                mAppInfo.put(p.packageName, p);
                try {
                    IPackageStatsObserver.Stub observer = new PackageStatsObserver();
                    getPackageInfo(p.packageName, observer);
                    //Log.e("!!!!!!!","111111111");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            //Log.e("BG Done!!!!!!!","etueryierieyi");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // chua tim duoc loi, dung tam tro meo nay vay
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    initArr();
                    dialog.cancel();
                }
            }, 3000);
            //Log.e("BOn post!!!!!!!","dyjrtyuetueru");
        }
    }

    public void initArr() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandableList);

        //ArrayList<CacheChild> arrayList = (ArrayList<CacheChild>) getChildCacheArr();
        cacheGroups.add(new CacheGroup("Cache", totalClean));
        //Log.e("*********************", ""+arrayList.size());
        Collections.sort(arrayList, new Comparator<CacheChild>() {
            @Override
            public int compare(CacheChild t0, CacheChild t1) {
                    if(t0.getSizeCache() > t1.getSizeCache()){
                        return -1;
                    }else if(t0.getSizeCache() < t1.getSizeCache()){
                        return 1;
                    }else return  0;
            }
        });
        cacheGroupListHashMap.put(cacheGroups.get(0), arrayList);
        cacheApdapter = new CacheApdapter(this, cacheGroups, cacheGroupListHashMap);

        expandableListView.setAdapter(cacheApdapter);
    }


    private void initViewActionBar() {
        button = (Button) findViewById(R.id.boostcache);
        button.setOnClickListener(this);

        imgBack = (ImageView) findViewById(R.id.img_back);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvName.setText("Junk Files");

        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.boostcache:
                // xoa filie , ma chua xoa dc
                for (int i = 0; i < fileArrayList.size(); i++) {
                    File f = fileArrayList.get(i);
                    Log.e("File cache " + i, f.toString());

                    String[] children = f.list();
                    if(children != null){
                        //Log.e("XOAS!!!!!!", i+"");
                        for (int j = 0; j < children.length; j++)
                        {
                            new File(f, children[j]).delete();
                        }
                    }

                }
                fileArrayList.clear();
                expandableListView.deferNotifyDataSetChanged();

                Intent i = new Intent(CacheClean.this, BoostComplete.class);
                i.putExtra("key", totalClean);
                startActivity(i);
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public long getDirSize(File dir){
        long size = 0;
        try {
            for (File file : dir.listFiles()) {
                if (file != null && file.isDirectory()) {
                    size += getDirSize(file);
                } else if (file != null && file.isFile()) {
                    size += file.length();
                }
            }
        }catch (Exception e){ //Log.e(""+dir, e.toString());
            }
        return size;
    }

    private class PackageStatsObserver extends IPackageStatsObserver.Stub {
        @Override
        public void onGetStatsCompleted(PackageStats packageStats, boolean succeeded)
                throws RemoteException {
            //mScanCount++;
            PackageManager pm = getPackageManager();
            if (packageStats == null || !succeeded) {}
            else {


                    long file_size = (packageStats.cacheSize + packageStats.externalCacheSize)/1024;
                    Log.d("KJFKAJFK", "DQT: "+file_size);
                    totalClean += file_size;
                    //Log.e("App ****", totalClean+"  " + arrayList.size());
                    if(file_size>0){
                        CacheChild cacheChild = new CacheChild(pm.getApplicationIcon(mAppInfo.get(packageStats.packageName)),
                                pm.getApplicationLabel(mAppInfo.get(packageStats.packageName)).toString(),
                                file_size);
                        arrayList.add(cacheChild);

                        try {
                            Context context = createPackageContext(packageStats.packageName, CONTEXT_IGNORE_SECURITY);
                            File cacheDirectory = context.getCacheDir();
                            if (cacheDirectory != null) {
                                fileArrayList.add(cacheDirectory);
                            }
                            else {
                                cacheDirectory = context.getExternalCacheDir();
                                if (cacheDirectory != null) {
                                    fileArrayList.add(cacheDirectory);
                                }
                            }
                        }catch (Exception e){}
                    }

            }
        }

    }

    public void getPackageInfo(String packageName, IPackageStatsObserver.Stub observer) {
        try {
            PackageManager pm = getPackageManager();
            Method getPackageSizeInfo = pm.getClass()
                    .getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);

            getPackageSizeInfo.invoke(pm, packageName, observer);
        } catch (NoSuchMethodException e ) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
