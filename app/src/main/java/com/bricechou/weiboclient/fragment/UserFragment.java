package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.utils.TitleBuilder;

/**
 * Created by sdduser on 5/28/16.
 */
public class UserFragment extends BaseFragment {
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = View.inflate(mMainActivity, R.layout.frag_user,null);
        new TitleBuilder(mView)
                .setCenterText("我")
                .setLeftText("添加好友")
                .setRightText("设置")
                .setLeftOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                })
                .setRightOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
        return mView;
    }
}
