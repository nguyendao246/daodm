package com.news.supercleaner.fragment;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.news.supercleaner.R;
import com.news.supercleaner.activity.thongtindienthoai.ToolsInsideActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by asus on 21/07/2017.
 */

public class FrgThongTinThietBi extends Fragment implements View.OnClickListener {
    private static final String DATA_NAME_ONE = "DATA_NAME_ONE";
    private static final String DATA_NAME = "DATA_NAME";
    private static final String ERROR = "ERROR";
    double initial_memory = 0;
    private View rootView;
    private LayoutInflater mInflater;
    private ImageView imgBack;
    private TextView tvName;
    private String name = "";
    private TextView tvNhanHieu, tvMau, tvCPU, tvRam, tvOLuuTru, tvPhienBan, tvDoPhanGiai;
    private TextView tvDungLuongO, tvDungLuongRam, tvDungLuongCPU;
    private double tongDungLuong;
    private double dungLuongDaDung;
    private double phanTramDungLuongO;

    private double tongDungLuongRAM;
    private double dungLuongDaDungRAM;
    private double phanTramDungLuongRAM;
    private double boNhoTrong, boNhoNgoai;

    private DonutProgress donutProgress;
    private DonutProgress donutProgressCPU;
    private DonutProgress donutProgressRAM;
    private float temp;
    private long songDownloadSize;

    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    // bộ nhớ trong còn lại
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long size = blockSize * availableBlocks;
        return size;
    }

//    public static String getSDSize() {
//        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
//        long blockSize = statFs.getBlockSize();
//        long totalSize = statFs.getBlockCount();
//        long availableSize = statFs.getAvailableBlocks();
//        long freeSize = statFs.getFreeBlocks();
//        return formatSize(availableSize * blockSize);
//    }

    // bộ nhớ ngoài còn lại
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            long size = availableBlocks * blockSize;
            return size;
        } else {
            return -1;
        }
    }

    //tổng bộ nhớ trong
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long size = blockSize * totalBlocks;
        return size;
    }

    // bộ nhớ ngoài máy
    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = (long) stat.getBlockSize();
            long totalBlocks = (long) stat.getBlockCount();
            long size = totalBlocks * blockSize;
            return size;
        } else {
            return -1;
        }
    }

    public static double formatSize(double size) {
        size = size / (1024 * 1024 * 1024);
        return size;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        rootView = inflater.inflate(R.layout.frg_thong_tin_thiet_bi, container, false);
        name = ToolsInsideActivity.nameOne;
        Log.d("AIHYB2", "onCreate: " + name);

        initData();
        initView();
        return rootView;
    }

    private void initData() {
        boNhoTrong = formatSize(getTotalInternalMemorySize());
        boNhoNgoai = formatSize(getTotalSDMemory());

        dungLuongDaDung = formatSize(getTotalInternalMemorySize() - getAvailableInternalMemorySize()) + tinhToan(formatSize(getBusySDMemory()));
        tongDungLuong = formatSize(getTotalInternalMemorySize() + getTotalSDMemory());


        phanTramDungLuongO = (dungLuongDaDung * 1.0 / tongDungLuong) * 100;

        Intent intent = getContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        temp = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;

        tongDungLuongRAM = getTotalMemory();
        dungLuongDaDungRAM = tinhToan(formatSize(getRestMemory()));


        phanTramDungLuongRAM = (dungLuongDaDungRAM * 1.0 / tongDungLuongRAM) * 100;


    }

    private double tinhToan(double size) {
        long size1 = (long) (size * 10);
        double size2 = (size1 * 1.0 / 10);
        return size2;
    }

    private void initView() {
        donutProgress = (DonutProgress) rootView.findViewById(R.id.donut_progress);
        donutProgressRAM = (DonutProgress) rootView.findViewById(R.id.donut_progress_ram);
        donutProgressCPU = (DonutProgress) rootView.findViewById(R.id.donut_progress_cpu);
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        imgBack = (ImageView) rootView.findViewById(R.id.img_back);
        tvName.setText(name + "");

        tvNhanHieu = (TextView) rootView.findViewById(R.id.tv_nhan_hieu);
        tvMau = (TextView) rootView.findViewById(R.id.tv_mau);
        tvCPU = (TextView) rootView.findViewById(R.id.tv_cpu);
        tvRam = (TextView) rootView.findViewById(R.id.tv_ram);
        tvOLuuTru = (TextView) rootView.findViewById(R.id.tv_o_luu_tru);
        tvPhienBan = (TextView) rootView.findViewById(R.id.tv_phien_ban);
        tvDoPhanGiai = (TextView) rootView.findViewById(R.id.tv_do_phan_giai);

        imgBack.setOnClickListener(this);

        tvNhanHieu.setText(Build.BRAND);
        tvMau.setText(Build.MODEL);

        tvCPU.setText(Build.HARDWARE);
        tvRam.setText(getTotalMemory() + "GB");


        tvPhienBan.setText(Build.VERSION.RELEASE);


        tvOLuuTru.setText("Trong " + tinhToan(boNhoTrong) + "GB" + "\n" + "Ngoài " + tinhToan(boNhoNgoai) + "GB");

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        tvDoPhanGiai.setText(metrics.heightPixels + "x" + metrics.widthPixels);

        tvDungLuongO = (TextView) rootView.findViewById(R.id.tv_dung_luong_o);
        tvDungLuongRam = (TextView) rootView.findViewById(R.id.tv_dung_luong_ram);
        tvDungLuongCPU = (TextView) rootView.findViewById(R.id.tv_dung_luong_cpu);

        tvDungLuongO.setText(tinhToan(dungLuongDaDung) + "GB/" + tinhToan(tongDungLuong) + "GB");
        tvDungLuongRam.setText(dungLuongDaDungRAM + "GB/" + tongDungLuongRAM + "GB");
        donutProgress.setProgress((int) phanTramDungLuongO);
        donutProgressCPU.setProgress((int) temp);
        donutProgressCPU.setSuffixText("°C");

        donutProgressRAM.setProgress((int) phanTramDungLuongRAM);

        Log.d("HFJHJF", "initView: " + tinhToan(formatSize(getTotalSDMemory())));

        Log.d("KJAKJFKA", "initView: " + getAvailableSDMemory());

        Log.d("JHJHJHJ", "initView: " + tinhToan(formatSize(getTest())));


    }

    private long getTest() {
        StatFs stat = new StatFs("/mnt/sdcard");
        long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        return (long) bytesAvailable;
    }

    // đọc dung lượng thẻ nhớ
    private long getTotalSDMemory() {
//        if (externalMemoryAvailable()) {
//            StatFs stat = new StatFs(System.getenv("SECONDARY_STORAGE"));
//            long totalBlocks = (long) stat.getBlockSize() * (long) stat.getBlockCount();
//            return totalBlocks;
//        } else {
//            return 0;
//        }
        return 0;

    }


    // đọc dung lượng thẻ nhớ còn trống
    public long getAvailableSDMemory() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long sdAvailSize = (long) stat.getAvailableBlocks()
                * (long) stat.getBlockSize();
        //One binary gigabyte equals 1,073,741,824 bytes.
        long gigaAvailable = sdAvailSize / 1073741824;

//        if (externalMemoryAvailable()) {
//            StatFs stat = new StatFs(System.getenv("SECONDARY_STORAGE"));
//            long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
//            return bytesAvailable;
//        } else {
//            return 0;
//        }

        return gigaAvailable;

    }

    // đọc dung lượng thẻ nhớ còn
    public long getBusySDMemory() {
        long total = getTotalSDMemory();
        long free = getAvailableSDMemory();
        long busy = total - free;
        return busy;
    }

    // đọc RAM đã dùng
    public double getRestMemory() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getContext().getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long tongbonho = mi.totalMem;
        long bonhofree = mi.availMem;
        return ((tongbonho - bonhofree) * 1.0);
    }

    // doc RAM
    public double getTotalMemory() {

        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();//meminfo
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            //total Memory
            initial_memory = Double.valueOf(arrayOfString[1]).doubleValue()
                    / 1024 / 1024;
            localBufferedReader.close();
            int memory = (int) initial_memory;
            if ((initial_memory - memory) > 0.5) {
                return memory + 1;
            } else {
                return memory;
            }
        } catch (IOException e) {
            return -1;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                ((ToolsInsideActivity) getContext()).finishAct();
                break;
        }
    }


}
