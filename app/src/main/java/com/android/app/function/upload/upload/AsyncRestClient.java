package com.android.app.function.upload.upload;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * 使用android-async-http 请求封装（采用单例即可）
 * Created by frodo on 2015/4/15.
 */
public class AsyncRestClient {
    private static AsyncRestClient DEFAULT;

    private SyncHttpClient client;
    private String baseUrl = "http://api.twitter.com/1/";

    public static AsyncRestClient getDefault() {
        if (DEFAULT == null) {
            synchronized (AsyncRestClient.class) {
                DEFAULT = new AsyncRestClient();
            }
        }
        return DEFAULT;
    }

    private AsyncRestClient() {
        client = new SyncHttpClient();
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return baseUrl + relativeUrl;
    }
}
