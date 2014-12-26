package com.android.app.function;

import com.android.app.AbstractListActivity;

/**
 * Created by frodo on 2014/12/26.
 */
public class FunctionActivity extends AbstractListActivity {
    @Override
    public String filterPath() {
        return "com.android.app.main.function.Path";
    }

    @Override
    public String filterCategory() {
        return "com.android.app.main.function.SAMPLE_CODE";
    }
}
