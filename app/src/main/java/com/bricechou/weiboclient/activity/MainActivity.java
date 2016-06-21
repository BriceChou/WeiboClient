package com.bricechou.weiboclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.fragment.FragmentController;

/**
 * Created by sdduser on 5/27/16.
 */
public class MainActivity extends FragmentActivity implements OnCheckedChangeListener, OnClickListener {

    private RadioGroup mRadioGroup;//切换选项卡
    private ImageView mImageView;//发表新微博
    private FragmentController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // To get the layout instance in the xml file.
        // @comment by BriceChou
        // @datetime 2016-06-02
        mController = FragmentController.getInstance(this, R.id.fl_content);
        mController.showFragment(0);
        initView();
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        mImageView = (ImageView) findViewById(R.id.iv_add);

        mRadioGroup.setOnCheckedChangeListener(this);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                mController.showFragment(0);
                break;
            case R.id.rb_message:
                mController.showFragment(1);
                break;
            case R.id.rb_search:
                mController.showFragment(2);
                break;
            case R.id.rb_user:
                mController.showFragment(3);
                break;
            default:
                break;
        }

    }

    @Override
    public void onClick(View v) {
        // To post a new Weibo content.
        // @author BriceChou
        switch (v.getId()) {
            case R.id.iv_add:
                startActivity(new Intent(MainActivity.this,PostWeiboActivity.class));
                break;
            default:
                break;
        }
    }
}