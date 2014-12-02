package com.openwudi.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * 图片工具类
 * Created by wudi on 14-11-29.
 */
public final class OpenImageUtils {
    private OpenImageUtils() {
    }

    /**
     * 将图片文件加入Gallery
     *
     * @param context context
     * @param file    图片文件
     */
    public static void galleryAddPic(Context context, File file) {
        if (file == null || context == null) return;
        String mCurrentPhotoPath = file.getAbsolutePath();
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        galleryAddPic(context, contentUri);
    }

    /**
     * 通过Uri将图片加入到Gallery
     *
     * @param context context
     * @param uri     图片Uri
     */
    public static void galleryAddPic(Context context, Uri uri) {
        if (uri == null || context == null) return;
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        context.sendBroadcast(mediaScanIntent);
    }
}
