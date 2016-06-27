package com.bricechou.weiboclient.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by sdduser on 5/28/16.
 */
public class FragmentController {
    private static FragmentController scontroller;
    private int mcontainerId;//当前需要显示内容的ID
    private FragmentManager mfragmentManager;
    private ArrayList<Fragment> mfragments;
    private FragmentTransaction mFragmentTransaction;

    private FragmentController(FragmentActivity activity, int containerId) {
        this.mcontainerId = containerId;
        mfragmentManager = activity.getSupportFragmentManager();
        initFragment();
    }

    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
        scontroller = new FragmentController(activity, containerId);
        return scontroller;
    }

    private void initFragment() {
        if (mfragments == null) {
            mfragments = new ArrayList<Fragment>();
            mfragments.add(new HomeFragment());
            mfragments.add(new MessageFragment());
            mfragments.add(new SearchFragment());
            mfragments.add(new UserFragment());
        }
        mFragmentTransaction = mfragmentManager.beginTransaction();
        for (Fragment fragment : mfragments) {
            mFragmentTransaction.add(mcontainerId, fragment);
        }
        mFragmentTransaction.commit();
    }

    public void hideFragment() {
        mFragmentTransaction = mfragmentManager.beginTransaction();
        for (Fragment fragment : mfragments) {
            if (fragment != null) {
                mFragmentTransaction.hide(fragment);
            }
        }
        mFragmentTransaction.commit();
    }

    public void showFragment(int position) {
        hideFragment();
        Fragment fragment = mfragments.get(position);
        mFragmentTransaction = mfragmentManager.beginTransaction();
        mFragmentTransaction.show(fragment);
        mFragmentTransaction.commit();
    }

    public Fragment getFragment(int position) {
        return mfragments.get(position);
    }
}