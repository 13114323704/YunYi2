package com.hq.yunyi2.bean;

import android.graphics.drawable.Drawable;
import java.io.Serializable;

public class PrivateExperienceBean{
    private String title;
    private String time;
    private String content;
    private Drawable image;

    public PrivateExperienceBean(String title, String time, String content, Drawable image) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

}
