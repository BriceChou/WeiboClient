package com.bricechou.weiboclient.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.R;

/**
 * Created by sdduser on 5/28/16.
 */
public class SearchFragment extends BaseFragment {
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = View.inflate(mMainActivity, R.layout.frag_search,null);
        return mView;
    }
}
