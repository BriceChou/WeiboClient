package com.bricechou.weiboclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;

public class WeiboDetailActivity extends Activity implements View.OnClickListener{
    private ImageView mImageViewBack;
    private ImageView mImageViewMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);
        initView();
    }

    private void initView(){
        mImageViewBack = (ImageView)findViewById(R.id.weibo_detail_back);
        mImageViewMore = (ImageView)findViewById(R.id.weibo_detail_more);
        mImageViewBack.setOnClickListener(this);
        mImageViewMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.weibo_detail_back:
                // Exit this activity.
                finish();
                break;
            case R.id.weibo_detail_more:
                Toast.makeText(WeiboDetailActivity.this,
                        R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
}

