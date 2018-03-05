package com.hq.yunyi2.bean;


public class MyOwnUserInfo {
    private String userId;
    private String userName;
    private String image_uri;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public MyOwnUserInfo(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        image_uri="http://p.qq181.com/cms/1210/2012100413195471481.jpg";
    }
}
