package com.hq.yunyi2.bean;

import android.graphics.drawable.Drawable;


public class FamilyCircleBean {
    private String publish_user_nickname;
    private String publish_time;
    private String publish_content_text;
    private int publish_user_image;
    private Drawable publish_content_image=null;

    public FamilyCircleBean(int publish_user_image, String publish_user_nickname, String publish_time, String publish_content_text, Drawable publish_content_image) {
        this.publish_user_image = publish_user_image;
        this.publish_user_nickname = publish_user_nickname;
        this.publish_time = publish_time;
        this.publish_content_text = publish_content_text;
        this.publish_content_image = publish_content_image;
    }


    public String getPublish_user_nickname() {
        return publish_user_nickname;
    }

    public void setPublish_user_nickname(String publish_user_nickname) {
        this.publish_user_nickname = publish_user_nickname;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getPublish_content_text() {
        return publish_content_text;
    }

    public void setPublish_content_text(String publish_content_text) {
        this.publish_content_text = publish_content_text;
    }

    public int getPublish_user_image() {
        return publish_user_image;
    }

    public void setPublish_user_image(int publish_user_image) {
        this.publish_user_image = publish_user_image;
    }

    public Drawable getPublish_content_image() {
        return publish_content_image;
    }

    public void setPublish_content_image(Drawable publish_content_image) {
        this.publish_content_image = publish_content_image;
    }
}
