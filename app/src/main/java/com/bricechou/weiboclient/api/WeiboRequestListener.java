package com.bricechou.weiboclient.api;

import android.content.Context;
import android.util.Log;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

/**
 * This is a callback method class and deal with the weibo JSON data.
 *
 * @author BriceChou
 * @datetime 16-6-6 15:55
 * @TODO add a dialog to show the load weibo content progress
 */

public class WeiboRequestListener implements RequestListener {

    private static final String TAG = "WeiboRequestListener";

    private Context mContext; // define the current activity

    /**
     * initial WeiboRequestListener constructor
     *
     * @author BriceChou
     * @datetime 16-6-6 17:22
     */
    public WeiboRequestListener(Context context) {
        this.mContext = context;
    }

    /**
     * when we get the weibo content successed,this function will be executed.
     *
     * @author BriceChou
     * @datetime 16-6-6 15:59
     * @TODO if return a NULL type and how to deal with it.
     */
    @Override
    public void onComplete(String response) {
        Log.i(TAG, "onComplete: successed load the weibo content.");
    }

    /**
     * print the log to tell the developer about exception
     *
     * @author BriceChou
     * @datetime 16-6-6 17:18
     */
    @Override
    public void onWeiboException(WeiboException e) {
        Log.e(TAG, "onWeiboException: " + e.getMessage());
    }
}