package com.bricechou.weiboclient.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bricechou.weiboclient.R;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    /**  @TODO 后期考虑引入MD5将这个地址进行加密算法
     *   @author BriceChou
     */
    private static final String WEIBO_APP_SECRET = "94f3b81802a3999b25bf57720c32d98e";

    /** 通过 code 获取 Token 的 URL*/
    private static final String OAUTH2_ACCESS_TOKEN_URL = "https://open.weibo.cn/oauth2/access_token";

    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
