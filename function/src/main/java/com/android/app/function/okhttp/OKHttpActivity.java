package com.android.app.function.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class OKHttpActivity extends Activity {
    private final OkHttpClient client = new OkHttpClient();
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView sv = new ScrollView(this);
        tv = new TextView(this);
        sv.addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setContentView(sv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doRequestOKHttp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void doRequestOKHttp() throws Exception {
        Log.i("okhttp", "===========start==============");

        Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);

        final String responseString = new String(response.body().bytes(), "UTF-8");
        Log.i("okhttp", responseString);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(responseString);
            }
        });
        Log.i("okhttp", "===========end==============");
    }
}
