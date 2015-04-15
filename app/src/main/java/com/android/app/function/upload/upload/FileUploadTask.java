package com.android.app.function.upload.upload;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;

import com.android.app.function.utils.NetworkProber;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 每一次任务组 Task, 采用single线程池作为执行工具
 * Created by frodo on 2015/4/14.
 */
public class FileUploadTask implements Runnable {
    private Context context;
    private FileUpload fileUpload;
    private TaskListener listener;
    private volatile State state = State.IDLE;

    private ConcurrentMap<String, String> finishedList = new ConcurrentHashMap<>();
    private ConcurrentMap<String, String> lastedList = new ConcurrentHashMap<>();

    private AtomicInteger count = new AtomicInteger(0);

    public FileUploadTask(Context context, FileUpload fileUpload, TaskListener listener) {
        this.context = context;
        this.fileUpload = fileUpload;
        this.listener = listener;

        for (String fileString : fileUpload.files) {
            final File f = new File(fileString);
            lastedList.putIfAbsent(f.getName(), f.getAbsolutePath());
        }
    }

    @Override
    public void run() {
        for (String fileString : fileUpload.files) {
            final String tmp = fileString;
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    count.incrementAndGet();

                    if (NetworkProber.isNetworkAvailable(context)) {
                        final File f = new File(tmp);
                        RequestParams params = new RequestParams();
                        params.put("name", f.getName());
                        params.put("md5", "dsafasdfasdf");
                        try {
                            params.put("file", f);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        AsyncRestClient.getDefault().post("manager/file/upload", params, new AsyncHttpResponseHandler() {
                            @Override
                            public void onProgress(int bytesWritten, int totalSize) {
                                super.onProgress(bytesWritten, totalSize);
                                FileUploadTask.this.onProgress(f.getName(), bytesWritten, totalSize);
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String response = new String(responseBody);
                                Log.i("", f.getName() + " onSuccess ," + " response >> " + response);

                                finishedList.putIfAbsent(f.getName(), response);
                                lastedList.remove(f.getName(), f.getAbsolutePath());

                                //if (finishedList.size() == fileUpload.files.size() && lastedList.isEmpty()) {
                                //}
                                if (count.get() == fileUpload.files.size()) {
                                    FileUploadTask.this.onSuccess(statusCode);
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Log.i("", f.getName() + " onFailure ," + " error >> " + error.getStackTrace().toString());
                                //if (finishedList.size() < fileUpload.files.size() && !lastedList.isEmpty()) {
                                //}
                                if (count.get() == fileUpload.files.size()) {
                                    FileUploadTask.this.onFailure(statusCode, error);
                                }

                            }
                        });
                    } else {
                        if (count.get() == fileUpload.files.size()) {
                            FileUploadTask.this.onFailure(500, new NetworkErrorException("网络不可用"));
                        }
                    }
                }
            };

            FileUploadCenter.execute(task);
        }
    }

    private void onProgress(String taskName, int bytesWritten, int totalSize) {
        state = State.RUNNING;
        listener.onProgress(fileUpload.group, taskName, bytesWritten, totalSize);
    }

    private void onFailure(int statusCode, Throwable throwable) {
        state = State.FAILURE;
        listener.onFailure(fileUpload.group, statusCode, lastedList, throwable);
    }

    private void onSuccess(int statusCode) {
        state = State.SUCCESS;
        listener.onSuccess(fileUpload.group, statusCode, finishedList);
    }

    public State getState() {
        return state;
    }

    public static class State {
        public static final State IDLE = new State(0);
        public static final State RUNNING = new State(1);
        public static final State FAILURE = new State(2);
        public static final State SUCCESS = new State(3);

        private int stateCode;

        public State(int stateCode) {
            this.stateCode = stateCode;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof State) {
                State state = (State) o;
                if (state.stateCode == stateCode) {
                    return true;
                }
            }

            return super.equals(o);
        }
    }
}
