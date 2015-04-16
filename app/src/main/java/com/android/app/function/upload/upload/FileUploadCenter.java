package com.android.app.function.upload.upload;

import android.content.Context;

import com.android.app.function.utils.NetworkProber;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 文件上传中心
 * Created by frodo on 2015/4/13.
 */
public class FileUploadCenter {
    private static FileUploadCenter DEFAULT;

    private static Executor executor = new ThreadPoolExecutor(
            0 /* corePoolSize */, 1 /* maximumPoolSize */, 60L /* keepAliveTime */, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(), threadFactory("FileUploadCenter ConnectionPool", true));

    private Context context;
    private FileUploadTaskQueue taskQueue;
    private FileUploadCraft craft;

    public static FileUploadCenter from(Context context) {
        if (DEFAULT == null) {
            synchronized (FileUploadCenter.class) {
                DEFAULT = new FileUploadCenter(context);
            }
        }
        return DEFAULT;
    }

    private FileUploadCenter(Context context) {
        this.context = context;
    }

    public void init(String server) {
        taskQueue = new FileUploadTaskQueue(this, context);
        craft = new FileUploadCraft();
        RestClient.getDefault().setBaseUrl(server);
    }


    public void addTask(FileUpload task) {
        checkField();
        if (!NetworkProber.isNetworkAvailable(context)) {
            craft.writeCraft(task);
        } else {
            taskQueue.add(task);
        }
    }

    public void onNotify() {
        checkField();
        try {
            taskQueue.onNotify();
        } catch (IllegalAccessException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean writeCrafts(LinkedList<FileUpload> crafts) {
        return craft.writeCrafts(crafts);
    }

    private void checkField() {
        if (taskQueue == null) {
            try {
                throw new IllegalAccessException("未完成初始化");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public static ThreadFactory threadFactory(final String name, final boolean daemon) {
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread result = new Thread(runnable, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }
}
