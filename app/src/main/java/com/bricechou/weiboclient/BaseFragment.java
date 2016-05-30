package com.bricechou.weiboclient;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.bricechou.weiboclient.activity.MainActivity;

/**
 * Created by sdduser on 5/28/16.
 */
public class BaseFragment extends Fragment {

    protected MainActivity mMainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity) getActivity();
    }

    protected void intent2Activity (Class<? extends Activity> targetActivity) {
        Intent intent = new Intent(mMainActivity,targetActivity);
        startActivity(intent);

    }
}
