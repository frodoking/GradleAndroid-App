package com.android.app;

import android.content.Context;
import com.android.app.framework.config.Configuration;

import com.android.app.function.dynamicloading.DynamicLoadingUtil;

/**
 * Created by frodo on 2015/2/4.
 */
public class MainApplication extends AppApplication {
    @Override
    protected void attachBaseContext(Context base) {
        DynamicLoadingUtil.attachBaseContext(base);
    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }
}
