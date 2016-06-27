package com.bricechou.weiboclient.api;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * Implements asynchronous request to open the Weibo web login page.
 * When we using it, we don't need to implements all method.
 *
 * @author BriceChou
 * @datetime 16-5-30 上午11:12
 */
public class LoginAuthListener implements WeiboAuthListener {
    private final static String TAG = "LoginAuthListener";
    private Context mContext;
    private Oauth2AccessToken mAccessToken;

    public LoginAuthListener(Context context) {
        this.mContext = context;
    }

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
            // save Token into SharedPreferences
            LoginUserToken.saveAccessToken(mContext, mAccessToken);
            Toast.makeText(mContext,
                    R.string.toast_auth_success, Toast.LENGTH_SHORT).show();
        } else {
            // When got error information, remote server will return the error code.
            String code = values.getString("code");
            String message = mContext.getString(R.string.toast_auth_failed);
            if (!TextUtils.isEmpty(code)) {
                message = message + "\nObtained the code: " + code;
            }
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @TODO How to solve the error information and advise user how to do this.
     * @datetime 16-5-30 上午11:31
     * @author BriceChou
     */
    @Override
    public void onWeiboException(WeiboException e) {
        Log.e(TAG, "onWeiboException: There are some problem occurred.", new Throwable(e.getMessage()));
        Toast.makeText(mContext,
                "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}