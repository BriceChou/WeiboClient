package com.bricechou.weiboclient.api;

/**
 * Implements the WeiboAuthListener class
 *
 * @author BriceChou
 * @datetime 16-6-14 11:36
 */
import android.util.Log;
import android.os.Bundle;
import android.widget.Toast;
import android.text.TextUtils;
import android.content.Context;

import com.bricechou.weiboclient.R;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * 微博认证授权回调类。
 * 1. SSO 授权时，需要在 {onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
 * 该回调才会被执行。
 * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
 * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
 *
 * @author BriceChou
 * @datetime 16-5-30 上午11:12
 * @TODO 更改提示框的样式, 现在使用的时默认样式
 */
public class LoginAuthListener implements WeiboAuthListener {
    private final static String TAG = "LoginAuthListener";
    private Context mContext;
    private Oauth2AccessToken mAccessToken;

    public LoginAuthListener(Context context) {
        this.mContext = context;
    }

    /**
     * When user cancel to login and show the message
     *
     * @author BriceChou
     */
    @Override
    public void onCancel() {
        Toast.makeText(mContext,
                R.string.toast_login_auth_canceled, Toast.LENGTH_LONG).show();
    }

    /**
     * When user finished the input account information operation and call this function
     *
     * @author BriceChou
     * @datetime 16-5-30 上午11:22
     */
    @Override
    public void onComplete(Bundle values) {
        // get the Token value from Bundle
        mAccessToken = Oauth2AccessToken.parseAccessToken(values);
        if (mAccessToken.isSessionValid()) {
            // save Token to SharedPreferences
            LoginUserToken.saveAccessToken(mContext, mAccessToken);
            Toast.makeText(mContext,
                    R.string.toast_auth_success, Toast.LENGTH_SHORT).show();
        } else {
            // 以下几种情况，您会收到 Code：
            // 1. 当您未在平台上注册的应用程序的包名与签名时；
            // 2. 当您注册的应用程序包名与签名不正确时；
            // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
            String code = values.getString("code");
            String message = mContext.getString(R.string.toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                message = message + "\nObtained the code: " + code;
            }
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * When got a error information ,this function will be called.
     *
     * @param e
     * @author BriceChou
     * @datetime 16-5-30 上午11:31
     * @TODO 考虑登录错误后, 进行其他操作
     */
    @Override
    public void onWeiboException(WeiboException e) {
        Log.e(TAG, "onWeiboException: There are some problem occurred.", new Throwable(e.getMessage()));
        Toast.makeText(mContext,
                "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}