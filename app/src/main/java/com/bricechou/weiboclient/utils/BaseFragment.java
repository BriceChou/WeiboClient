package com.bricechou.weiboclient.utils;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bricechou.weiboclient.activity.MainActivity;

/**
 * Created by sdduser on 5/28/16.
 */
public class BaseFragment extends Fragment {

    protected MainActivity mMainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取当前与Fragment布局相关联的Activity
        // @comment by BriceChou
        mMainActivity = (MainActivity) getActivity();
    }
}