package com.android.app.custom.roundedrectangleview;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.R;
import com.android.app.custom.view.RoundedRectangleTextView;

/**
 * Created by frodo on 2015/9/18.
 */
public class RoundedRectangleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
        params.gravity = Gravity.CENTER;
        params.topMargin = 20;
        params.bottomMargin = 20;
        params.leftMargin = 50;
        params.rightMargin = 50;

        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    Toast.makeText(RoundedRectangleActivity.this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        RoundedRectangleTextView textView = new RoundedRectangleTextView(this);
        Resources resources = getResources();
        textView.normalDrawable(resources.getColor(R.color.general_blue), resources.getColor(R.color.transparent))
                .pressedDrawable(resources.getColor(R.color.general_blue), resources.getColor(R.color.general_gray_light))
                .textColor(resources.getColor(R.color.general_black), resources.getColor(R.color.general_white), resources.getColor(R.color.general_gray_deep))
                .update();
        textView.setText("Stanford");
        textView.setOnClickListener(l);
        ll.addView(textView, params);

        RoundedRectangleTextView textView2 = new RoundedRectangleTextView(this);
        textView2.normalDrawable(resources.getColor(R.color.general_green), resources.getColor(R.color.transparent))
                .pressedDrawable(resources.getColor(R.color.general_green), resources.getColor(R.color.general_gray_light))
                .textColor(resources.getColor(R.color.general_black), resources.getColor(R.color.general_white), resources.getColor(R.color.general_gray_deep))
                .radius(100).update();
        textView2.setText("California");
        textView2.setOnClickListener(l);
        textView2.setSelected(true);
        ll.addView(textView2, params);

        RoundedRectangleTextView textView3 = new RoundedRectangleTextView(this);
        textView2.normalDrawable(resources.getColor(R.color.general_persimmon), resources.getColor(R.color.transparent))
                .pressedDrawable(resources.getColor(R.color.general_persimmon), resources.getColor(R.color.general_gray_light))
                .unableDrawable(resources.getColor(R.color.general_persimmon), resources.getColor(R.color.general_gray_deep))
                .textColor(resources.getColor(R.color.general_black), resources.getColor(R.color.general_white), resources.getColor(R.color.general_gray_deep))
                .update();
        textView3.setText("Florida");
        textView3.setOnClickListener(l);
        textView3.setEnabled(false);
        ll.addView(textView3, params);
        setContentView(ll);
    }
}
