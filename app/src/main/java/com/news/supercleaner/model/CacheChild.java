package com.news.supercleaner.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Kopiteshot on 7/23/2017.
 */

public class CacheChild {
    private Drawable iconCache;
    private String nameAppCache;
    private double sizeCache;

    public CacheChild(Drawable iconCache, String nameAppCache, double sizeCache) {
        this.iconCache = iconCache;
        this.nameAppCache = nameAppCache;
        this.sizeCache = sizeCache;
    }

    public Drawable getIconCache() {
        return iconCache;
    }

    public void setIconCache(Drawable iconCache) {
        this.iconCache = iconCache;
    }

    public String getNameAppCache() {
        return nameAppCache;
    }

    public void setNameAppCache(String nameAppCache) {
        this.nameAppCache = nameAppCache;
    }

    public double getSizeCache() {
        return sizeCache;
    }

    public void setSizeCache(long sizeCache) {
        this.sizeCache = sizeCache;
    }
}
