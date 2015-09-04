package com.android.app.function.upload.upload;

import android.content.Context;
import android.util.Log;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * 上传队列
 * Created by frodo on 2015/4/14.
 */
public class FileUploadTaskQueue {
    public static final String TAG = "FileUploadTaskQueue";
    private FileUploadCenter fileUploadCenter;
    private Context context;
    private LinkedList<FileUpload> taskNames = new LinkedList<>();
    private LinkedList<FileUpload> lastTaskNames = new LinkedList<>();
    private LinkedList<FileUpload> finishTaskNames = new LinkedList<>();

    private FileUploadTask currentTask;

    public FileUploadTaskQueue(FileUploadCenter fileUploadCenter, Context context) {
        this(fileUploadCenter, context, null);
    }

    public FileUploadTaskQueue(FileUploadCenter fileUploadCenter, Context context, LinkedList<FileUpload> fileUploads) {
        this.fileUploadCenter = fileUploadCenter;
        this.context = context;

        if (fileUploads != null) {
            taskNames.addAll(fileUploads);
            lastTaskNames.addAll(fileUploads);
        }
    }

    public void add(FileUpload fileUpload) {
        taskNames.add(fileUpload);
        lastTaskNames.add(fileUpload);

        try {
            onNotify();
        } catch (IllegalAccessException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onNotify() throws IllegalAccessException, InterruptedException {
        if (taskNames.isEmpty()) {
            throw new IllegalAccessException("队列为空, 无法完成执行任务!!!");
        } else {
            if (taskNames.size() == finishTaskNames.size()) {
                log("all finish", "");
            } else {
                log("getNextTask", "");
                FileUploadTask nextTask = getNextTask();

                if (nextTask != null) {
                    currentTask = nextTask;
                }

                new Thread(currentTask).start();
            }
        }
    }

    private FileUploadTask getNextTask() {
        if (currentTask != null) {
            if (currentTask.getState().equals(FileUploadTask.State.RUNNING)) {
                return null;
            }
        }

        final FileUpload currentTask = lastTaskNames.peek();

        return new FileUploadTask(context, currentTask, new TaskListener() {
            @Override
            public void onProgress(String group, String fileName, int bytesWritten, int totalSize) {
                String log =  MessageFormat.format("===group==>> {0}, ===fileName==>> {1}, bytesWritten==>> {2}, totalSize==>> {3}", group,fileName, bytesWritten, totalSize);
                log("onProgress", log);
            }

            @Override
            public void onFailure(String group, int statusCode, Object failedObj, Throwable throwable) {
                String log =  MessageFormat.format("===group==>> {0}, ===statusCode==>> {1}, failedMap==>> {2}, throwable==>> {3}", group,statusCode, failedObj, Arrays.toString(throwable.getStackTrace()));
                log("onFailure", log);
                if (fileUploadCenter.writeCrafts(lastTaskNames)) {
                    clean();
                }
            }

            @Override
            public void onSuccess(String group, int statusCode, Object finishedObj) {
                String log =  MessageFormat.format("===group==>> {0}, ===statusCode==>> {1}, finishedMap==>> {2}", group,statusCode, finishedObj);
                log("onSuccess", log);
                finishTaskByName(currentTask);
                try {
                    onNotify();
                } catch (IllegalAccessException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void finishTaskByName(FileUpload taskName) {
        finishTaskNames.offer(taskName);
        lastTaskNames.poll();
    }

    public void clean() {
        taskNames.clear();
        finishTaskNames.clear();
        lastTaskNames.clear();
    }

    private void log(String method, String msg) {
        Log.i(TAG, "=== " + method + " === MSG >> " + msg);
    }
}