package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;

/**
 * Created by sdduser on 5/28/16.
 */
public class MessageFragment extends BaseFragment {
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = View.inflate(mMainActivity, R.layout.frag_message, null);
        new TitleBuilder(mView)
                .setCenterText("消息")
                .setLeftText("发现群")
                .setRightImage(R.drawable.icon_newchat)
                .setLeftOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mMainActivity,
                                R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                    }
                })
                .setRightOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mMainActivity,
                                R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                    }
                });
        return mView;
    }
}
