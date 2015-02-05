package com.app.android.dynamicloading;

import android.app.Application;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import dalvik.system.DexClassLoader;

public class TestApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        File file = new File("/data/data/" + base.getPackageName() + "/.cache/");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            Runtime.getRuntime().exec("chmod 755 " + file.getAbsolutePath()).waitFor();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Util.copyJarFile(this);
        Object currentActivityThread =
                RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread", new Class[]{},
                        new Object[]{});
        String packageName = getPackageName();
        ArrayMap mPackages =
                (ArrayMap) RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mPackages");
        WeakReference wr = (WeakReference) mPackages.get(packageName);
        DexClassLoader dLoader = new DexClassLoader("/data/data/" + base.getPackageName() + "/.cache/classdex.jar", "/data/data/"
                + base.getPackageName() + "/.cache", "/data/data/" + base.getPackageName() + "/.cache/",
                base.getClassLoader());
        try {
            Class<?> class1 = dLoader.loadClass("com.example.test.TestActivity");
            Log.i("b364", "----------->class1: " + class1);
        } catch (ClassNotFoundException e) {
            Log.i("b364", "----------->class not found Exception!");
            e.printStackTrace();
        }
        Log.i("b364", "------>PackageInfo: " + wr.get());
        // DexClassLoader dLoader = new DexClassLoader(apkFileName, odexPath,
        // libPath, (ClassLoader) RefInvoke.getFieldOjbect(
        // "android.app.LoadedApk", wr.get(), "mClassLoader"));
        RefInvoke.setFieldOjbect("android.app.LoadedApk", "mClassLoader", wr.get(), dLoader);
    }
}
