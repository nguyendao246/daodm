package com.news.supercleaner.model;

import android.graphics.drawable.Drawable;

/**
 * Created by asus on 04/08/2017.
 */

public class ItemBattery {
    private Drawable img;
    private String name;
    private String phanTram;

    public ItemBattery(Drawable img, String name, String phanTram) {
        this.img = img;
        this.name = name;
        this.phanTram = phanTram;
    }

    public Drawable getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getPhanTram() {
        return phanTram;
    }
}
