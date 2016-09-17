package com.lucidcoders.coolundertwenty.state;

import android.app.Application;

public class CoolUnderTwentyApp extends Application {
    private static CoolUnderTwentyApp sInstance;

    public static CoolUnderTwentyApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }
}
