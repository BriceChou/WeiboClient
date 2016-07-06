package com.bricechou.weiboclient.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

/**
 * To save Oauth2AccessToken object by SharedPreferences.
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

    public static void saveAccessToken(Context context, Oauth2AccessToken accessToken) {
        if (null == accessToken) {
            Log.e(TAG, "saveAccessToken: accessToken is NULL.");
            return;
        }
        // To get the SharedPreferences instance.
        // @param SharedPreferences OPEN MODE : MODE_WORLD_WRITEABLE，MODE_WORLD_READABLE
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        // save the accessToken data by Editor.
        Editor editor = pref.edit();
        editor.putString(KEY_UID, accessToken.getUid());
        editor.putString(KEY_ACCESS_TOKEN, accessToken.getToken());
        editor.putString(KEY_REFRESH_TOKEN, accessToken.getRefreshToken());
        editor.putLong(KEY_EXPIRES_IN, accessToken.getExpiresTime());
        // commit all data in the editor.
        editor.commit();
    }

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
     * @XXX we don't input weibo account and password that we use the developer account.
     */
    public static Oauth2AccessToken showAccessToken() {
        Oauth2AccessToken accessToken = new Oauth2AccessToken();

        accessToken.setToken("2.005KPAHDDkQmLCfb61244d50Rb_7xC"); // BriceChou
        // accessToken.setToken("2.0074nniFDkQmLCeb9d82635f3hL8gB"); // wanwang
        accessToken.setRefreshToken("2.005KPAHDDkQmLCf648c5fafdaOK6ME"); // BriceChou
        accessToken.setUid("2851891152"); // BriceChou
        accessToken.setExpiresIn("157679999"); // BriceChou
        return accessToken;
    }
}
