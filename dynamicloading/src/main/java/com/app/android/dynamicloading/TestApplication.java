package com.app.android.dynamicloading;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import dalvik.system.DexClassLoader;

public class TestApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        File file = new File(Util.getCacheDir(base));
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            Runtime.getRuntime().exec("chmod 755 " + file.getAbsolutePath()).waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }

        Util.copyJarFile(this);

        Object currentActivityThread =
                RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread", new Class[]{},
                        new Object[]{});
        String packageName = getPackageName();
        HashMap mPackages =
                (HashMap) RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mPackages");
        WeakReference wr = (WeakReference) mPackages.get(packageName);
        DexClassLoader dLoader =
                new DexClassLoader(Util.getCacheDir(base) + "/plugindex.jar", Util.getCacheDir(base),
                        Util.getCacheDir(base), base.getClassLoader());
        RefInvoke.setFieldOjbect("android.app.LoadedApk", "mClassLoader", wr.get(), dLoader);
    }
}
