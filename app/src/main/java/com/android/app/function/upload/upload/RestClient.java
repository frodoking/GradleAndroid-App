package com.android.app.function.upload.upload;

import android.accounts.NetworkErrorException;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * 使用android-async-http 请求封装（采用单例即可）
 * Created by frodo on 2015/4/15.
 */
public class RestClient {
    private static final int TIME_OUT = 60000;
    private static RestClient DEFAULT;


    private SyncHttpClient client;
    private String baseUrl = "http://api.twitter.com/1/";

    public static RestClient getDefault() {
        if (DEFAULT == null) {
            synchronized (RestClient.class) {
                DEFAULT = new RestClient();
            }
        }
        return DEFAULT;
    }

    private RestClient() {
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

    public void postMultiFile(String url, Map<String, String> params, FileUpload fileUpload, TaskListener listener) {
        if (fileUpload == null || fileUpload.files == null || fileUpload.files.isEmpty()) {
            listener.onFailure(fileUpload != null ? fileUpload.group : null, 400, null, new IllegalArgumentException("上传文件列表不能为空!!!"));
            return;
        }

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName(HTTP.UTF_8));//设置请求的编码格式
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//设置浏览器兼容模式

        if (fileUpload.files != null && !fileUpload.files.isEmpty()) {
            for (String fileString : fileUpload.files) {
                File file = new File(fileString);
                builder.addBinaryBody("files", file);
            }
        }
        //设置请求参数
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue());
            }
        }

        HttpEntity entity = builder.build();// 生成 HTTP POST 实体
        long totalSize = entity.getContentLength();//获取上传文件的大小

        HttpPost post = new HttpPost(getAbsoluteUrl(url));
        post.setEntity(entity);

        DefaultHttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), TIME_OUT);
        HttpConnectionParams.setSoTimeout(client.getParams(), TIME_OUT);

        try {
            org.apache.http.HttpResponse resp = client.execute(post);
            ByteArrayBuffer bt = new ByteArrayBuffer(4096);
            HttpEntity he = resp.getEntity();
            GZIPInputStream gis = new GZIPInputStream(he.getContent());
            int l;
            byte[] tmp = new byte[4096];
            while ((l = gis.read(tmp)) != -1) {
                bt.append(tmp, 0, l);
                listener.onProgress(fileUpload.group, fileUpload.group, bt.length(), (int) totalSize);
            }

            String str = new String(bt.toByteArray(), "UTF-8");
            int statusCode = resp.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                listener.onSuccess(fileUpload.group, statusCode, str);
            } else {
                listener.onFailure(fileUpload.group, statusCode, null, new NetworkErrorException("请求数据失败!!!"));
            }
        } catch (Throwable e) {
            listener.onFailure(fileUpload.group, 500, null, e);
        } finally {
            client.getConnectionManager().shutdown();
        }
    }
}
