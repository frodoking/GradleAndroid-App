package com.android.app.function.glide;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.app.function.R;
import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

/**
 * Created by frodo on 2016/4/25.
 */
public class GlideActivity extends Activity {
    private static final String imageUrl = "http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg";
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        iv = new ImageView(this);
        Button button = new Button(this);
        button.setText("Show File");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final Bitmap bitmap = Glide.with(GlideActivity.this).load(imageUrl).asBitmap().into(100, 100).get();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(GlideActivity.this, bitmap == null ? "获取失败" : "获取成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        Button button2 = new Button(this);
        button2.setText("Show Cache Dir");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = Glide.getPhotoCacheDir(GlideActivity.this).getAbsolutePath();
                Toast.makeText(GlideActivity.this, string, Toast.LENGTH_SHORT).show();
            }
        });
        ll.addView(iv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(button, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.addView(button2, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .placeholder(R.drawable.skyblue_logo_wechatfavorite_checked)
                .crossFade()
                .into(iv);

        setContentView(sv);
    }
}
