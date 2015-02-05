package com.app.android.dynamicloading;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import android.content.Context;

public class Util {
    public static void copyJarFile(Context paramContext) {
        String str = getCacheDir(paramContext) + "/plugindex.jar";
        try {
            JarFile localJarFile = new JarFile(paramContext.getApplicationInfo().publicSourceDir);
            ZipEntry localZipEntry = localJarFile.getEntry("assets/plugindex.jar");
            File localFile = new File(str);
            byte[] arrayOfByte = new byte[65536];
            BufferedInputStream localBufferedInputStream =
                    new BufferedInputStream(localJarFile.getInputStream(localZipEntry));
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

    public static String getCacheDir(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }
}
