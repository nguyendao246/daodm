package com.news.supercleaner.model;

/**
 * Created by Kopiteshot on 7/21/2017.
 */

public class ResutlVirus {
    private int imageVirus;
    private String notification;
    private String content;
    private String manager;

    public String getManager() {
        return manager;
    }

    public ResutlVirus(int imageVirus, String notification, String content, String manager) {
        this.imageVirus = imageVirus;
        this.notification = notification;
        this.content = content;
        this.manager = manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }


    public int getImageVirus() {
        return imageVirus;
    }

    public void setImageVirus(int imageVirus) {
        this.imageVirus = imageVirus;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
