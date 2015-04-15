package com.android.app.function.upload.upload;

import android.util.Log;

import java.util.LinkedList;

/**
 * 离线写入工具
 * Created by frodo on 2015/4/14.
 */
public class FileUploadCraft {
    public static final String TAG = "FileUploadCraft";

    public FileUpload readCraft(String group) {
        log("readCraft", group);
        return null;
    }

    public boolean writeCraft(FileUpload craft) {
        log("writeCraft", craft.group);
        return false;
    }

    public LinkedList<FileUpload> readCrafts() {
        log("readCrafts", readCrafts().toString());
        return null;
    }

    public boolean writeCrafts(LinkedList<FileUpload> crafts) {
        log("writeCrafts", crafts.toString());
        return false;
    }

    private void log(String method, String msg) {
        Log.i(TAG, "=== " + method + " === MSG >> " + msg);
    }
}
