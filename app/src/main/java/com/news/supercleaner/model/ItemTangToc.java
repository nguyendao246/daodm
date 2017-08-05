package com.news.supercleaner.model;

import android.graphics.drawable.Drawable;

/**
 * Created by asus on 04/08/2017.
 */

public class ItemTangToc {
    private Drawable img;
    private String name;
    private double dungLuong;

    public ItemTangToc(Drawable img, String name, double dungLuong) {
        this.img = img;
        this.name = name;
        this.dungLuong = dungLuong;
    }

    public Drawable getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public double getDungLuong() {
        return dungLuong;
    }
}
