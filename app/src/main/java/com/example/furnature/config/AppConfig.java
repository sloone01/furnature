package com.example.furnature.config;


import com.activeandroid.ActiveAndroid;

public class AppConfig extends com.activeandroid.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

}
