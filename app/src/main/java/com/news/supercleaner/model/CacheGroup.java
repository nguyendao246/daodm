package com.news.supercleaner.model;

/**
 * Created by Kopiteshot on 7/24/2017.
 */

public class CacheGroup {
    private String title;
    private long size;

    public CacheGroup(String title, long size) {
        this.title = title;
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
