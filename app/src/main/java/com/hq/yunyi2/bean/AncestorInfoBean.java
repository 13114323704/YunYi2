package com.hq.yunyi2.bean;


public class AncestorInfoBean {

    private String name;
    private String relative;
    private String address;
    private String time;

    public AncestorInfoBean(String name, String relative, String address, String time) {
        this.name = name;
        this.relative = relative;
        this.address = address;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
