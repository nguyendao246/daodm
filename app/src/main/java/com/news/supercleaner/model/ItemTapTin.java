package com.news.supercleaner.model;

/**
 * Created by asus on 21/07/2017.
 */

public class ItemTapTin {
    private int img;
    private String name, nameTwo;
    private String size;

    public ItemTapTin(int img, String name, String nameTwo, String size) {
        this.img = img;
        this.name = name;
        this.nameTwo = nameTwo;
        this.size = size;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getNameTwo() {
        return nameTwo;
    }

    public String getSize() {
        return size;
    }
}
