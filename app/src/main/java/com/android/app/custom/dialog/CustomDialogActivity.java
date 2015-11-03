package com.android.app.custom.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.app.R;

/**
 * Created by frodo on 2015/9/6.
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

        Button btn4 = new Button(this);
        btn4.setText("showSingleChoiceListViewDialog");
        btn4.setTextColor(getResources().getColor(R.color.general_white));
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalDialog.showSingleChoiceListViewDialog(CustomDialogActivity.this);
            }
        });

        Button btn5 = new Button(this);
        btn5.setText("showMultiChoiceListViewDialog");
        btn5.setTextColor(getResources().getColor(R.color.general_white));
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalDialog.showMultiChoiceListViewDialog(CustomDialogActivity.this);
            }
        });

        ll.addView(btn1);
        ll.addView(btn2);
        ll.addView(btn3);
        ll.addView(btn4);
        ll.addView(btn5);

        setContentView(ll);
    }
}
