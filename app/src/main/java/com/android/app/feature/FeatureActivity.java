package com.android.app.feature;

import com.android.app.AbstractListActivity;

/**
 * Created by frodo on 2015/12/3.
 */
public class FeatureActivity extends AbstractListActivity {
    @Override
    public String filterPath() {
        return "com.android.app.main.feature.Path";
    }

    @Override
    public String filterCategory() {
        return "com.android.app.main.feature.SAMPLE_CODE";
    }
}
