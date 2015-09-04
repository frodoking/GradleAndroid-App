package com.android.app.custom;

import android.os.Bundle;

import com.android.app.AbstractListActivity;
import com.android.app.custom.dialog.GlobalDialog;

/**
 * Created by frodo on 2014/12/26.
 */
public class CustomActivity extends AbstractListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalDialog.showDialog(this);
    }

    @Override
    public String filterPath() {
        return "com.android.app.main.custom.Path";
    }

    @Override
    public String filterCategory() {
        return "com.android.app.main.custom.SAMPLE_CODE";
    }
}