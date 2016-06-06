package com.bricechou.weiboclient.fragement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.Status;

import org.w3c.dom.Text;

/**
 * Created by sdduser on 5/28/16.
 */
public class HomeFragment extends BaseFragment {

    private View mView;
    private TextView mTextView;
    private StatusesAPI mStatusesAPI;
    private Status mStatus;

    /**
     * show the weibo home page
     *
     * @comment by BriceChou
     * @datetime 16-6-6 17:28
     * @TODO to show the weibo content
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = View.inflate(mMainActivity, R.layout.frag_home, null);
        initWeiboContent(mMainActivity);
        return mView;
    }

    /**
     * initial the weibo content and display in the home page
     *
     * @author BriceChou
     * @datetime 16-6-6 15:14
     */
    private void initWeiboContent(Context context) {
        mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, LoginUserToken.getAccessToken(context));
        mStatusesAPI.friendsTimeline(0, 0, 5, 1, false, 0, false, new WeiboRequestListener(context, mStatus));
    }

    private void initView(Context context){

    }

}
