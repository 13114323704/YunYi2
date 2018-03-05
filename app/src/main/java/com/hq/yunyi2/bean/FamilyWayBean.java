package com.hq.yunyi2.bean;


public class FamilyWayBean {
    private String content;
    private String time;

    public FamilyWayBean(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
