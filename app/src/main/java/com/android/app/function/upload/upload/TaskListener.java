package com.android.app.function.upload.upload;

import java.util.Map;

/**
 * 每个任务组的进度监听
 * Created by frodo on 2015/4/15.
 */
public interface TaskListener {
    void onProgress(String group, String fileName, int bytesWritten, int totalSize);

    void onFailure(String group, int statusCode, Map<String, String> failedMap, Throwable throwable);

    void onSuccess(String group, int statusCode, Map<String, String> finishedMap);
}
