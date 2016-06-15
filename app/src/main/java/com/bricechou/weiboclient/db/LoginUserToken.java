package com.bricechou.weiboclient.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * 将用户登录时产生的 oAuth Token 进行储存
 * <p/>
 * SQLite： SQLite是一个轻量级的数据库，支持基本SQL语法，是常被采用的一种数据存储方式。
 * Android为此数据库提供了一个名为SQLiteDatabase的类，封装了一些操作数据库的API。
 * <p/>
 * SharedPreference： 是一种常用的数据存储方式，其本质就是一个xml文件，常用于存储较简单的参数设置。
 * 它的本质是基于XML文件存储key-value键值对数据，
 * 通常用来存储一些简单的配置信息。其存储位置在/data/data/<包名>/shared_prefs目录下。
 * SharedPreferences对象本身只能获取数据而不支持存储和修改，存储修改是通过Editor对象实现。
 * 实现SharedPreferences存储的步骤如下：
 * 一、根据Context获取SharedPreferences对象
 * 二、利用edit()方法获取Editor对象。
 * 三、通过Editor对象存储key-value键值对数据。
 * 四、通过commit()方法提交数据。
 * <p/>
 * <p/>
 * Created by BriceChou on 16-6-3.
 */

public class LoginUserToken {
    private static final String TAG = "LoginUserToken";
    private static final String PREFERENCES_NAME = "com_bricechou_weiboclient";
    private static final String KEY_UID = "uid";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EXPIRES_IN = "expires_in";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";

    // 当用户使用手机登录时 或者 手机注册的手机号码 会产生 手机号码,其他方式是没有手机号码的
    private static final String KEY_PHONE_NUM = "phone_number";

    /**
     * 保存用户登录的 Token 并写入到 SharedPreference 中
     *
     * @author BriceChou
     * @datetime 16-6-3 上午11:35
     */
    public static void saveAccessToken(Context context, Oauth2AccessToken accessToken) {
        if (null == accessToken) {
            Log.e(TAG, "saveAccessToken: accessToken is NULL.");
            return;
        }

        // 获取SharedPreferences对象
        // SharedPreferences preference = getSharedPreferences("数据Map的名称", 打开模式 );
        // 打开模式有多种，一般用可读和可写两种，MODE_WORLD_WRITEABLE，MODE_WORLD_READABLE。
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        // 写入数据 需要使用SharedPreferences的一个内部接口Editor
        Editor editor = pref.edit();
        editor.putString(KEY_UID, accessToken.getUid());
        editor.putString(KEY_ACCESS_TOKEN, accessToken.getToken());
        editor.putString(KEY_REFRESH_TOKEN, accessToken.getRefreshToken());
        editor.putLong(KEY_EXPIRES_IN, accessToken.getExpiresTime());
        editor.commit();
    }

    /**
     * 从 SharedPreferences 获取 Token 信息。
     *
     * @param context context 应用程序上下文环境
     * @return 返回 Token 对象
     * @datetime 16-6-3 下午2:05
     * @author BriceChou
     */
    public static Oauth2AccessToken getAccessToken(Context context) {
        if (null == context) {
            Log.e(TAG, "getAccessToken: context is NULL.");
            return null;
        }
        Oauth2AccessToken accessToken = new Oauth2AccessToken();
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        accessToken.setUid(pref.getString(KEY_UID, ""));
        accessToken.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
        accessToken.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
        accessToken.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
        return accessToken;
    }

    /**
     * 清空 SharedPreferences 中 Token信息。
     *
     * @param context 应用程序上下文环境
     */
    public static void clear(Context context) {
        if (null == context) {
            Log.e(TAG, "clear: context is NULL.");
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * just a test function that we can quickly to login for main_activity
     *
     * @author BriceChou
     * @datetime 16-6-7 10:40
     * @XXX we don't input weibo account and password
     */
    public static Oauth2AccessToken showAccessToken() {
        Oauth2AccessToken accessToken = new Oauth2AccessToken();
        accessToken.setToken("2.005KPAHDDkQmLCfb61244d50Rb_7xC");
        accessToken.setUid("2851891152");
        return accessToken;
    }

}