package com.bricechou.weiboclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.api.LoginAuthListener;
import com.bricechou.weiboclient.config.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class LoginActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "weiboclient.activity.LoginActivity";
    private AuthInfo mAuthInfo; // A Weibo instance
    private SsoHandler mSsoHandler; // deal with the user login class
    private Button mButtonSubmit;

    private void initView() {
        mButtonSubmit = (Button) findViewById(R.id.login_submit);
        mButtonSubmit.setOnClickListener(this);
    }

    private void initWeibo() {
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // Bind the weibo instance together with SSO auth method
        mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
        // To call the web login page which provide with Weibo itself.
        // @TODO To recognise the current phone status and use different way to login.
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_submit:
                // initWeibo();
                // @HACK it's use to test some activity.
                // If you want to fix and use your own ,
                // you should change the showAccessToken() to getAccessToken()
                // Below this JAVA files :
                // PostWeiboActivity / HomeFragment / UserFragment
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            default:
        }
    }

    /**
     * When SSO handler exit,this function will be called.
     *
     * @author BriceChou
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Without this callback function,we can't use the SSO handler.
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}