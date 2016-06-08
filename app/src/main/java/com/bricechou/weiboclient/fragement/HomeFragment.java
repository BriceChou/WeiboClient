package com.bricechou.weiboclient.fragement;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.view.ViewGroup;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.WeiboHomeAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.StatusList;

/**
 * Created by sdduser on 5/28/16.
 */
public class HomeFragment extends BaseFragment {

    private final static String TAG = "HomeFragment";
    private View mView;
    private ListView lv_home;
    private StatusesAPI mStatusesAPI;
    private StatusList mStatusList;
    private WeiboHomeAdapter mWeiboHomeAdapter;

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
        lv_home = (ListView) mView.findViewById(R.id.lv_home);
        initWeiboContent(mMainActivity);
        return mView;
    }

    /**
     * initial the weibo content and display in the home page
     *
     * @author BriceChou
     * @datetime 16-6-6 15:14
     */
    private void initWeiboContent(final Context context) {
        mStatusesAPI = new StatusesAPI(context, Constants.APP_KEY, LoginUserToken.showAccessToken());
        mStatusesAPI.friendsTimeline(0, 0, 5, 1, false, 0, false, new WeiboRequestListener(context) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"statuses\"")) {
                        // the Status instance load the data from JSON data.
                        mStatusList = mStatusList.parse(response);
                        mWeiboHomeAdapter = new WeiboHomeAdapter(context,mStatusList);
                        lv_home.setAdapter(mWeiboHomeAdapter);
                    }
                }
            }
        });
    }

}
