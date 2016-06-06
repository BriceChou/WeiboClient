package com.bricechou.weiboclient.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.models.Status;

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
    private Status mStatus; // define a Status instance to save the JSON data

    /**
     * initial WeiboRequestListener constructor
     *
     * @author BriceChou
     * @datetime 16-6-6 17:22
     */
    public WeiboRequestListener(Context mContext, Status mStatus) {
        this.mContext = mContext;
        this.mStatus = mStatus;
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
        if (!TextUtils.isEmpty(response)) {
            if (response.startsWith("{\"statuses\"")) {

                // the Status instance load the data from JSON data.
                mStatus = mStatus.parse(response);
            }
        }
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