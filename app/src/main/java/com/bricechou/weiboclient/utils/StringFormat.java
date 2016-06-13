package com.bricechou.weiboclient.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * To format some String type data
 *
 * @author BriceChou
 * @datetime 16-6-13 14:47
 */

public class StringFormat {


    private final static String TAG = "StringFormat";

    /**
     * To format weibo status source
     *
     * @author BriceChou
     * @datetime 16-6-13 14:49\
     * @TODO think about more fast format function.
     * This is not a good function to deal with it.
     */
    public static String formatStatusSource(String source) {
        String regex = "\">(.*)</a>";
        try {
            // 创建 Pattern 对象
            Pattern pattern = Pattern.compile(regex);
            // 现在创建 matcher 对象
            Matcher mtc = pattern.matcher(source);
            while (mtc.find()) {
                source = mtc.group();
            }
            source = source.substring(2,source.length()-4);
        } catch (Exception e) {
            Log.e(TAG, "formatStatusSource: " + e.getMessage(), new Throwable());
        }
        return source;
    }

}
