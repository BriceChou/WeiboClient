package com.bricechou.weiboclient.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.fragement.FragmentController;

/**
 * Created by sdduser on 5/27/16.
 */
public class MainActivity extends FragmentActivity implements OnCheckedChangeListener,OnClickListener {

    private RadioGroup mRadioGroup;//切换选项卡
    private ImageView mImageView;//发表新微博
    private FragmentController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        //点击添加按钮发表微博
    }
}
