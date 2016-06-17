package com.bricechou.weiboclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.api.LoginAuthListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.utils.SQLiteUtil;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private AuthInfo mAuthInfo; // A Weibo instance
    private SsoHandler mSsoHandler; // deal with the user login class
    private Button mButtonSubmit;
    private SQLiteUtil mSqLiteUtil;


    private void initView() {
        mButtonSubmit = (Button) findViewById(R.id.login_submit);
        mButtonSubmit.setOnClickListener(this);
    }

    /**
     * Initial Weibo instance
     *
     * @author BriceChou
     * @datetime 16-6-14 17:02
     */
    private void initWeibo() {
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // Bind the weibo instance together with SSO auth method
        mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
        // To call the web login page which provide with Weibo itself
        // @TODO 自动判断的方式比较慢,通过weiboAPI手动先进行判断,如果有客户端用客户端,没有就用WEB或者手机短信注册登录
        mSsoHandler.authorizeWeb(new LoginAuthListener(this) {
            @Override
            public void onComplete(Bundle values) {
                super.onComplete(values);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * When user click the button, this function will be called.
     *
     * @author BriceChou
     * @TODO 当用户点击非登录按钮时, 进行相应信息提示操作!如:提示输入怎样的用户名或者密码!
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_submit:
                initWeibo();
                // @XXX it's use to test some activity.
                // startActivity(new Intent(LoginActivity.this,MainActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @author BriceChou
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 微博ＡＰＩ规定必须添加回调页面,没有这个函数覆写将不能调用回调页面
        // 当用户取消授权登录时,SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}