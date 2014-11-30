package com.openwudi.common.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 位图工具函数集.
 */
public final class OpenBitmapUtils {

    /**
     * 获取位图, 需要指定需要的大小.
     *
     * @param uri 文件路径
     * @return 获取的位图. 如果图片过大或者不存在，返回null.
     */
    public static Bitmap getBitmap(Uri uri, int reqWidth, int reqHeight) {
        try {
            String path = uri.getPath();
            Options op = new Options();
            op.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, op);
//            op.inSampleSize = calculateInSampleSize(op, reqWidth, reqHeight);
            op.inSampleSize = computeSampleSize(op, -1, reqWidth * reqHeight);
            Log.d("calculateInSampleSize", "inSampleSize:" + op.inSampleSize + " reqWidth:" + reqWidth + " reqHeight:" + reqHeight);
            op.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(path, op);
        } catch (Exception e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    /**
     * 获取位图, 需要指定需要的大小.
     *
     * @param uri 文件路径
     * @return 获取的位图. 如果图片过大或者不存在，返回null.
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri, int reqWidth, int reqHeight) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Options op = new Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, op);

        op.inSampleSize = computeSampleSize(op, -1, reqWidth * reqHeight);
        Log.d("calculateInSampleSize", "inSampleSize:" + op.inSampleSize + " reqWidth:" + reqWidth + " reqHeight:" + reqHeight);
        op.inJustDecodeBounds = false;
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, op);
        parcelFileDescriptor.close();
        return image;
    }

    /**
     * 获取位图, 需要指定需要的大小.
     *
     * @param path 文件路径
     * @return 获取的位图. 如果图片过大或者不存在，返回null.
     */
    public final static Bitmap getBitmap(String path) {
        try {
            Options op = new Options();
            op.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(path, op);
        } catch (Exception e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    /**
     * 从媒体库中读取最近一张照片
     *
     * @param context context
     * @return 图片
     */
    public static Bitmap getLastMediaStoreImage(Context context, int reqWidth, int reqHeight) {
        ContentResolver contentResolver = context.getContentResolver();

        Cursor cursor = contentResolver.query(Media.EXTERNAL_CONTENT_URI, new String[]{Media._ID, Media.ORIENTATION, Media.TITLE}, null, null, Media.DATE_MODIFIED + " DESC");
        Bitmap bitmap = null;
        Bitmap rotated = null;
        try {
            if (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Media._ID));
                String orientation = cursor.getString(cursor.getColumnIndexOrThrow(Media.ORIENTATION));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(Media.TITLE));


                Uri uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, id);
                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options);

                options.inSampleSize = computeSampleSize(options, -1, reqWidth * reqHeight);

                options.inJustDecodeBounds = false;
//
                bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options);
                if (orientation != null && (orientation.equals("90") || orientation.equals("180") || orientation.equals("270"))) {
                    Matrix matrix = new Matrix();
                    int degree = Integer.parseInt(orientation);
                    matrix.postRotate(degree);
                    rotated = Bitmap.createBitmap(bitmap, 0, 0, options.outWidth, options.outHeight, matrix, true);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (rotated != null) {
                if (bitmap != null) {
                    bitmap.recycle();
                }
                return rotated;
            } else {
                return bitmap;
            }
        }
    }

    /**
     * 保存图片
     */
    public static boolean saveImageBitmap(Bitmap bitmap, File path, int quality, Bitmap.CompressFormat format) {
        boolean opRet = false;
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
            bitmap.compress(format, quality, bos);
            bos.flush();
            bos.close();
            opRet = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return opRet;
    }

    private static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static int computeSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels < 0) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength < 0) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if (maxNumOfPixels < 0 && minSideLength < 0) {
            return 1;
        } else if (minSideLength < 0) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    private OpenBitmapUtils() {
    }
}
