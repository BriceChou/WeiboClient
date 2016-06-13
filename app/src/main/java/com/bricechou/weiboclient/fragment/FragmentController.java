package com.bricechou.weiboclient.fragment;//package com.bricechou.weiboclient.fragement;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by sdduser on 5/28/16.
 */
public class FragmentController {

    private int mcontainerId;//当前需要显示内容的ID
    private FragmentManager mfragmentManager;
    private ArrayList<Fragment> mfragments;
    private FragmentTransaction mFragmentTransaction;

    private static FragmentController scontroller;
    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
        if(scontroller == null){
            scontroller = new FragmentController(activity,containerId);
        }
        return scontroller;
    }

    /**
     * 单例化模式 防止被创建多个该类
     * @param activity
     * @param containerId
     * @comment by BriceChou
     */
    private FragmentController(FragmentActivity activity,int containerId) {
        this.mcontainerId = containerId;
        mfragmentManager = activity.getSupportFragmentManager();
        initFragment();
    }

    /**
     * 初始化Fragment 用List集合将需要的Fragment进行储存
     *
     * @comment by BriceChou
     * @datetime 16-6-6 上午10:31
     */
    private void initFragment() {
        mfragments = new ArrayList<Fragment>();
        mfragments.add(new HomeFragment());
        mfragments.add(new MessageFragment());
        mfragments.add(new SearchFragment());
        mfragments.add(new UserFragment());
        mFragmentTransaction = mfragmentManager.beginTransaction();
        for(Fragment fragment : mfragments) {
            mFragmentTransaction.add(mcontainerId,fragment);
        }
        mFragmentTransaction.commit();
    }

    /**
     * 隐藏全部的Fragment
     *
     * @comment by BriceChou
     * @datetime 16-6-6 上午10:33
     */
    public void hideFragment() {
        mFragmentTransaction = mfragmentManager.beginTransaction();
        for(Fragment fragment : mfragments) {
            if(fragment != null) {
                mFragmentTransaction.hide(fragment);
            }
        }
        mFragmentTransaction.commit();
    }

    /**
     * 显示相应的Fragment
     *
     * @comment by BriceChou
     * @datetime 16-6-6 上午10:33
     */

    public void showFragment(int position) {
        hideFragment();
        Fragment fragment = mfragments.get(position);
        mFragmentTransaction = mfragmentManager.beginTransaction();
        mFragmentTransaction.show(fragment);
        mFragmentTransaction.commit();
    }

    /**
     * 获取对应位置的Fragment
     *
     * @comment by BriceChou
     * @datetime 16-6-6 上午10:33
     */

    public Fragment getFragment(int position) {
        return mfragments.get(position);
    }
}