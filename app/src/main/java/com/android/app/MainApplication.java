package com.android.app;

import android.app.Application;
import android.content.Context;

import com.android.app.function.dynamicloading.DynamicLoadingUtil;

/**
 * Created by frodo on 2015/2/4.
 */
public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        DynamicLoadingUtil.attachBaseContext(base);
    }
}
