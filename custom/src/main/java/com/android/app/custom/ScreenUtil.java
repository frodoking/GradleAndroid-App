package com.android.app.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

public class ScreenUtil {

    public static int getDisplayHeightWithoutStatuBar(Context context) {

        try {
            View decorView = ((Activity) context).getWindow().getDecorView();
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            return decorView.getHeight() - rect.top;
        } catch (NullPointerException e) {
            return 800;
        }
    }

    public static int getDisplayWidth(Context context) {

        try {
            View decorView = ((Activity) context).getWindow().getDecorView();
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            return rect.width();
        } catch (NullPointerException e) {
            return 480;
        }
    }

    public static int getDisplayHeight(Context context) {
        try {
            View decorView = ((Activity) context).getWindow().getDecorView();
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            return decorView.getHeight();
        } catch (NullPointerException e) {
            return 480;
        }
    }

    public static int getStatuBarHeight(Context context) {
        try {
            View decorView = ((Activity) context).getWindow().getDecorView();
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            return rect.top;
        } catch (NullPointerException e) {
            return 480;
        }
    }

    public static float getDisplayDensity(Context context) {

        if (context != null) {
            DisplayMetrics dm = getDisplayMetrics(context);
            if (dm != null) {
                return dm.density;
            } else {
                return 2.0f;
            }
        } else {
            return 2.0f;
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {

        if (context != null) {
            return context.getResources().getDisplayMetrics();
        } else {
            return null;
        }
    }

    /** * 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * 
     * @param pxValue
     * @param fontScale
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        DisplayMetrics disMetric = getDisplayMetrics(context);
        float fontScale = 1.5f;
        if (disMetric == null) {
            return (int) (spValue * 1.5 + 0.5f);
        } else {
            fontScale = (disMetric.density == 0) ? 1.5f : disMetric.density;
        }
        return (int) (spValue * fontScale + 0.5f);
    }
}
