package com.android.app.custom.view.flowlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.app.custom.R;

/**
 * Created by frodo on 2016/6/12.
 */

public class FlowLayoutActivity extends Activity {
    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    private TagFlowLayout mFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flow_layout);

        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_flowlayout);

        mFlowLayout.setAdapter(new TagAdapter<String>(mVals)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = new TextView(FlowLayoutActivity.this);
                tv.setText(s);
                return tv;
            }
        });
    }
}
