package com.news.supercleaner.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Kopiteshot on 7/26/2017.
 */

public class UnistallAppItem {
    private String nameApp;
    private Drawable iconApp;
    private boolean isPick;
    private String packagename;
    private long dateInstall;
    private double sizeApp;

    public UnistallAppItem(String nameApp, Drawable iconApp, boolean isPick, String packagename, long dateInstall, double sizeApp) {
        this.nameApp = nameApp;
        this.iconApp = iconApp;
        this.isPick = isPick;
        this.packagename = packagename;
        this.dateInstall = dateInstall;
        this.sizeApp = sizeApp;
    }

    public String getNameApp() {
        return nameApp;
    }

    public double getSizeApp() {
        return sizeApp;
    }

    public void setSizeApp(double sizeApp) {
        this.sizeApp = sizeApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    public Drawable getIconApp() {
        return iconApp;
    }

    public void setIconApp(Drawable iconApp) {
        this.iconApp = iconApp;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        isPick = pick;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public long getDateInstall() {
        return dateInstall;
    }

    public void setDateInstall(long dateInstall) {
        this.dateInstall = dateInstall;
    }
}
