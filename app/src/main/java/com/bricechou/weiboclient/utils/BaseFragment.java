package com.bricechou.weiboclient.utils;

import android.app.Activity;
import android.content.Intent;
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
        // To get the current activity bind with fragment.
        // @comment by BriceChou
        mMainActivity = (MainActivity) getActivity();
    }
    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(mMainActivity, tarActivity);
        startActivity(intent);
    }
}