package com.openwudi.common.util;

import android.text.TextUtils;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by wudi on 14-11-29.
 */
public final class OpenTextUtils {
    private OpenTextUtils() {
    }

    /**
     * 通过正则表达式获取URL和它的起始位置
     *
     * @param source 待处理的字符串
     * @return 返回Pair的List, 第一个参数为URL, 第二个参数为起始位置
     */
    public static List<Pair<String, Integer>> getURLPosition(String source) {
        List<Pair<String, Integer>> result = new ArrayList<Pair<String, Integer>>();
        Pattern pattern = Pattern.compile(REGEX.WEB);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            String url = matcher.group();
            int position = matcher.start();
            result.add(Pair.create(url, position));
        }
        return result;
    }

    /**
     * 判断是否包含Emoji表情
     *
     * @param source 待判断字符串
     * @return 包含返回true
     */
    public static boolean containsEmoji(String source) {
        if (TextUtils.isEmpty(source)) {
            return false;
        }
        Pattern emoji = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher emojiMatcher = (emoji.matcher(source));
        if (emojiMatcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 正则表达式
     */
    private static class REGEX {
        public static final String WEB = new StringBuilder()
                .append("((?:(http|https|Http|Https|rtsp|Rtsp):")
                .append("\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)")
                .append("\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_")
                .append("\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?")
                .append("((?:(?:[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}\\.)+")   // named host
                .append("(?:")   // plus top level domain
                .append("(?:aero|arpa|asia|a[cdefgilmnoqrstuwxz])")
                .append("|(?:biz|b[abdefghijmnorstvwyz])")
                .append("|(?:cat|com|coop|c[acdfghiklmnoruvxyz])")
                .append("|d[ejkmoz]")
                .append("|(?:edu|e[cegrstu])")
                .append("|f[ijkmor]")
                .append("|(?:gov|g[abdefghilmnpqrstuwy])")
                .append("|h[kmnrtu]")
                .append("|(?:info|int|i[delmnoqrst])")
                .append("|(?:jobs|j[emop])")
                .append("|k[eghimnrwyz]")
                .append("|l[abcikrstuvy]")
                .append("|(?:mil|mobi|museum|m[acdghklmnopqrstuvwxyz])")
                .append("|(?:name|net|n[acefgilopruz])")
                .append("|(?:org|om)")
                .append("|(?:pro|p[aefghklmnrstwy])")
                .append("|qa")
                .append("|r[eouw]")
                .append("|s[abcdeghijklmnortuvyz]")
                .append("|(?:tel|travel|t[cdfghjklmnoprtvwz])")
                .append("|u[agkmsyz]")
                .append("|v[aceginu]")
                .append("|w[fs]")
                .append("|y[etu]")
                .append("|z[amw]))")
                .append("|(?:(?:25[0-5]|2[0-4]") // or ip address
                .append("[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(?:25[0-5]|2[0-4][0-9]")
                .append("|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1]")
                .append("[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(?:25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}")
                .append("|[1-9][0-9]|[0-9])))")
                .append("(?:\\:\\d{1,5})?)") // plus option port number
                .append("(\\/(?:(?:[a-zA-Z0-9\\;\\/\\?\\:\\@\\&\\=\\#\\~")  // plus option query params
                .append("\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?")
                .append("(?:\\b|$)").toString();
    }
}
