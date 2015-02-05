package com.android.app.function.dynamicloading;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import dalvik.system.DexClassLoader;

public class DynamicLoadingUtil {

    public static void attachBaseContext(Context base) {
        File file = new File(getAppDataDir(base) + "/.cache/");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            Runtime.getRuntime().exec("chmod 755 " + file.getAbsolutePath()).waitFor();
        } catch (InterruptedException | IOException e1) {
            e1.printStackTrace();
        }

        copyJarFile(base);

        DexClassLoader dLoader = new DexClassLoader(getAppDataDir(base) + "/.cache/plugindex.jar",getAppDataDir(base) + "/.cache", getAppDataDir(base) + "/.cache/",
                base.getClassLoader());
        try {
            Class<?> clazz = dLoader.loadClass("com.android.app.PluginActivity");
            clazz.newInstance();
            Log.i("dynamicloading", "----------->clazz: " + clazz);
        } catch (ClassNotFoundException e) {
            Log.i("dynamicloading", "----------->class not found Exception!");
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void copyJarFile(Context context) {
        String str = getAppDataDir(context) + "/.cache/plugindex.jar";
        try {
            JarFile localJarFile = new JarFile(context.getApplicationInfo().publicSourceDir);
            ZipEntry localZipEntry = localJarFile.getEntry("assets/plugindex.jar");
            File localFile = new File(str);
            byte[] arrayOfByte = new byte[65536];
            BufferedInputStream localBufferedInputStream = new BufferedInputStream(localJarFile.getInputStream(localZipEntry));
            BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile));
            while (true) {
                int i = localBufferedInputStream.read(arrayOfByte);
                if (i <= 0) {
                    localBufferedOutputStream.flush();
                    localBufferedOutputStream.close();
                    localBufferedInputStream.close();
                    return;
                }
                localBufferedOutputStream.write(arrayOfByte, 0, i);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public static String getAppDataDir(Context base){
        return base.getFilesDir().getAbsolutePath() + base.getPackageName();
    }

}
