package com.comjia.keyboard;

import android.app.Application;

/**
 *
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static int keyboardHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }


}
