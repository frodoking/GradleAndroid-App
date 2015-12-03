package com.android.app.test;

import com.android.app.AbstractListActivity;

/**
 * Created by frodo on 2015/12/3.
 */
public class TestActivity  extends AbstractListActivity {
    @Override
    public String filterPath() {
        return "com.android.app.main.test.Path";
    }

    @Override
    public String filterCategory() {
        return "com.android.app.main.test.SAMPLE_CODE";
    }
}
