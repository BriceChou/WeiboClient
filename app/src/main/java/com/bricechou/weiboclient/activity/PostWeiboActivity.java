package com.bricechou.weiboclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.openapi.StatusesAPI;

/**
 * User can post a weibo content
 *
 * @author BriceChou
 * @datetime 16-6-14 14:10
 * @TODO add weibo content image and emoticon
 */
public class PostWeiboActivity extends Activity implements View.OnClickListener {
    private final static String TAG = "weiboclient.activity.PostWeiboActivity";
    private ImageView mImageViewBack; // back to main page
    private ImageView mImageViewSend; // send a weibo to server
    private EditText mEditTextContent;  // post weibo content
    private ImageView mImageViewContent; // add weibo content image
    private StatusesAPI mStatusesAPI;

    private void initView() {
        mEditTextContent = (EditText) findViewById(R.id.et_post_weibo_content);
        mImageViewContent = (ImageView) findViewById(R.id.iv_post_weibo_image);
        mImageViewBack = (ImageView) findViewById(R.id.iv_post_weibo_back);
        mImageViewSend = (ImageView) findViewById(R.id.iv_post_weibo_send);
        // Set the click listener
        mImageViewBack.setOnClickListener(this);
        mImageViewSend.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_weibo);
        initView();
    }

    /**
     * Confirm to send a Weibo.
     *
     * @author BriceChou
     * @datetime 16-6-14 17:57
     * @TODO Show the correct earth location by Google Geographical APIs
     */
    private void postWeibo(String text) {
        // mStatusesAPI = new StatusesAPI(this, Constants.APP_KEY, LoginUserToken.getAccessToken());
        mStatusesAPI = new StatusesAPI(this, Constants.APP_KEY, LoginUserToken.showAccessToken());
        mStatusesAPI.update(text, "22", "12", new WeiboRequestListener(this) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                Toast.makeText(PostWeiboActivity.this, R.string.toast_post_weibo_successed, Toast.LENGTH_SHORT).show();
           }
            @Override
            public void onWeiboException(WeiboException e) {
                super.onWeiboException(e);
                Toast.makeText(PostWeiboActivity.this, R.string.toast_operation_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_post_weibo_back:
                Toast.makeText(PostWeiboActivity.this,
                        R.string.toast_post_weibo_canceled, Toast.LENGTH_SHORT).show();
                // Exit this activity.
                finish();
                break;
            case R.id.iv_post_weibo_send:
                String text = mEditTextContent.getText().toString();
                // Exit this activity.
                finish();
                // To send a Weibo with text.(Don't have image.)
                postWeibo(text);
                break;
            default:
        }
    }
}
