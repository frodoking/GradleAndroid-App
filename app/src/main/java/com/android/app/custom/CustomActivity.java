package com.android.app.custom;

import com.android.app.AbstractListActivity;

/**
 * Created by frodo on 2014/12/26.
 */
public class CustomActivity extends AbstractListActivity {
    @Override
    public String filterPath() {
        return "com.android.app.main.custom.Path";
    }

    @Override
    public String filterCategory() {
        return "com.android.app.main.custom.SAMPLE_CODE";
    }
}