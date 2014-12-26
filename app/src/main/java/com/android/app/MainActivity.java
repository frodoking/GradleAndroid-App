package com.android.app;

/**
 * Created by frodo on 2014/12/26.
 */
public class MainActivity extends AbstractListActivity {
    @Override
    public String filterPath() {
        return "com.android.app.main.Path";
    }

    @Override
    public String filterCategory() {
        return "com.android.app.main.SAMPLE_CODE";
    }
}