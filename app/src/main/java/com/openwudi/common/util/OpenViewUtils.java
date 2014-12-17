package com.openwudi.common.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 提供设置控件属性的工具方法. 现在主要支持:
 * <ul>
 *     <li>设置背景</li>
 *     <li>禁用控件</li>
 * </ul>
 */
public final class OpenViewUtils {

    /**
     * 设置View的背景.
     * @param view 视图
     * @param drawable 背景
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void setBackground(View view, Drawable drawable) {
        int pb = view.getPaddingBottom();
        int pt = view.getPaddingTop();
        int pl = view.getPaddingLeft();
        int pr = view.getPaddingRight();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
        if (pb != 0 && pt != 0 && pl != 0 && pr != 0) {
            view.setPadding(pl, pt, pr, pb);
        }
    }

    /**
     * 为View添加布局完成响应事件.
     * @param view 视图
     * @param listener 布局完成响应事件.
     */
    public static void addGlobalLayoutListener(View view, OnGlobalLayoutListener listener) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    /**
     * 设置控件是否置灰.
     * @param v 控件
     * @param dimmed 是否置灰
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setDimmed(View v, boolean dimmed) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (dimmed) {
                v.setAlpha(0.5f);
            } else {
                v.setAlpha(1.0f);
            }
        } else {
            int alpha = 255;
            if (dimmed) {
                alpha = 128;
            }
            if (v.getBackground() != null) {
                v.getBackground().mutate().setAlpha(alpha);
            }
            if (v instanceof ImageView) {
                ImageView iv = (ImageView) v;
                if (iv.getDrawable() != null) {
                    iv.setAlpha(alpha);
                }
            } else if (v instanceof TextView) {
                TextView tv = (TextView)v;
                tv.setTextColor(tv.getTextColors().withAlpha(alpha));
                tv.setHintTextColor(tv.getHintTextColors().withAlpha(alpha));
                tv.setLinkTextColor(tv.getLinkTextColors().withAlpha(alpha));
                Drawable[] cds = tv.getCompoundDrawables();
                if (cds != null && cds.length > 0) {
                    for (Drawable d : cds) {
                        if (d != null) {
                            d.mutate().setAlpha(alpha);
                        }
                    }
                }
            }
        }
    }

    /**
     * 设置View是否可以点击. 如果不可点击，增加透明度.
     * @param v 控件
     * @param enabled 是否可以点击
     */
    public static void setEnabled(View v, boolean enabled) {
        if (v.isEnabled() != enabled) {
            setDimmed(v, !enabled);
            v.setEnabled(enabled);
        }
    }

    /**
     * 将编辑框的游标移动到文本末尾.
     * @param edit 编辑框
     */
    public static void setCursorEndOfEditText(EditText edit) {
        ((EditText)edit).setSelection(edit.getText().toString().length());
    }

    public static void showText(final TextView textView, final String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
    }

    private OpenViewUtils() {}
}
