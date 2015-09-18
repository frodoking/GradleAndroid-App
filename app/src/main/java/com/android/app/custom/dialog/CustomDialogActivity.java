package com.android.app.custom.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.app.R;

/**
 * Created by xuwei19 on 2015/9/6.
 */
public class CustomDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);


        Button btn1 = new Button(this);
        btn1.setText("showMsgDialog");
        btn1.setTextColor(getResources().getColor(R.color.general_white));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalDialog.showMsgDialog(CustomDialogActivity.this);
            }
        });

        Button btn2 = new Button(this);
        btn2.setText("showToastDialog");
        btn2.setTextColor(getResources().getColor(R.color.general_white));
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalDialog.showToastDialog(CustomDialogActivity.this);
            }
        });

        Button btn3 = new Button(this);
        btn3.setText("showArrayListViewDialog");
        btn3.setTextColor(getResources().getColor(R.color.general_white));
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalDialog.showArrayListViewDialog(CustomDialogActivity.this);
            }
        });

        ll.addView(btn1);
        ll.addView(btn2);
        ll.addView(btn3);

        setContentView(ll);
    }
}
