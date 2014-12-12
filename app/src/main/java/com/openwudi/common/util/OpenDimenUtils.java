package com.openwudi.common.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * px和dp、sp之间的转换函数集.
 */
public final class OpenDimenUtils {
    /**
     * 将dp转换为px
     * @param context Android环境
     * @param dp 分辨率无关尺寸
     * @return 实际像素
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    
    /**
     * 将sp转换为px
     * @param context Android环境
     * @param sp 分辨率无关尺寸，用于文字
     * @return 实际像素
     */
    public static int sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(sp * scale + 0.5f);
    }

    /**
     * 将px转换为dp
     * @param context Android环境
     * @param px 实际像素长度
     * @return 分辨率无关尺寸
     */
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
    
    /**
     * 获取屏幕宽度.
     * @param context Android环境
     * @return 屏幕宽度，px
     */
    public static int getWindowWidth(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm.widthPixels;
    }
    
    /**
     * 获取屏幕高度.
     * @param context Android环境
     * @return 屏幕高度，px
     */
    public static int getWindowHeight(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm.heightPixels;
    }
}
