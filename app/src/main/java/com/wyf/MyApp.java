package com.wyf;

import android.app.Application;

import com.wyf.auto.AutoUtils;

/**
 * Created by wyf on 2017/8/18.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AutoUtils.init(this, 1080, true);
    }
}
