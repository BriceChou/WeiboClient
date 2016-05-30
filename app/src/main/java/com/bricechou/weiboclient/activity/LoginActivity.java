package com.bricechou.weiboclient.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.bricechou.weiboclient.R;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    /**  @TODO 后期考虑引入MD5将这个地址进行加密算法
     *   @author BriceChou
     */
    private static final String WEIBO_APP_SECRET = "94f3b81802a3999b25bf57720c32d98e";

    // 通过 code 获取 Token 的 URL
    private static final String OAUTH2_ACCESS_TOKEN_URL = "https://open.weibo.cn/oauth2/access_token";

    // 初始化应用的APP KEY/ 回调页面
    private AuthInfo mAuthInfo;

    // 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
    private Oauth2AccessToken mAccessToken;

    //注意：SsoHandler 仅当 SDK 支持 SSO 时有效
    private SsoHandler mSsoHandler;


    private Button mButtonSubmit;  // 成员变量登录按钮
    private EditText mEditTextUsername;  // 登陆的帐号
    private EditText mEditTextPassword;  // 登录的密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        mEditTextUsername = (EditText)findViewById(R.id.text_account);
        mEditTextPassword = (EditText)findViewById(R.id.text_password);
        mButtonSubmit = (Button)findViewById(R.id.login_submit);
    }

}
