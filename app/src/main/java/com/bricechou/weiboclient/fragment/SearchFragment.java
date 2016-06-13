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
public class SearchFragment extends BaseFragment {
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = View.inflate(mMainActivity, R.layout.frag_search,null);
        new TitleBuilder(mView)
                .setSearchText(R.id.titlebar_et_search)
                .setRightImage(R.drawable.icon_voice)
                .setLeftOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //leftbar click event

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
