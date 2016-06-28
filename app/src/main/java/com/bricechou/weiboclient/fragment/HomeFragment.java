package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.HomeAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.StatusList;

public class HomeFragment extends BaseFragment {
    private final static String TAG = "HomeFragment";
    private View mView;
    private ListView mListViewHome; // fragment_home list view
    private StatusList mStatusList; // All Weibo content collection
    private StatusesAPI mStatusesAPI; // Weibo content interface

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        initWeiboContent();
        /**
         * Main page titlebar
         *
         * @comment by BriceChou
         * @datetime 16-6-14 17:56
         * @FIXME Show the current user name
         */
        new TitleBuilder(mView)
                // .setCenterText("" + mLoginUserInformation.loginUser.name)
                .setCenterText("周晢")
                .setLeftImage(R.drawable.icon_friendattention)
                .setRightImage(R.drawable.icon_radar)
                .setLeftOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .setRightOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
        return mView;
    }

    private void initWeiboContent() {
        mStatusesAPI = new StatusesAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        // mStatusesAPI = new StatusesAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.getAccessToken());
        mStatusesAPI.friendsTimeline(0, 0, 20, 1, false, 0, false, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"statuses\"")) {
                        // the Status instance load the data from JSON data.
                        mStatusList = mStatusList.parse(response);
                        if (null != mStatusList) {
                            mListViewHome.setAdapter(new HomeAdapter(mMainActivity, mStatusList.statusList));
                        } else {
                            onComplete(response);
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_home, null);
        mListViewHome = (ListView) mView.findViewById(R.id.lv_home);
    }
}
