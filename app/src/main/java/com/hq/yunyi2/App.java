package com.hq.yunyi2;

import android.app.Application;

import io.rong.imkit.RongIM;


public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
    }
}
