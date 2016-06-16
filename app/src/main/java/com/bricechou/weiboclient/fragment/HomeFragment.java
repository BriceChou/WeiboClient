package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.WeiboHomeAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.StatusList;

/**
 * Created by sdduser on 5/28/16.
 */
public class HomeFragment extends BaseFragment {

    private final static String TAG = "HomeFragment";
    private View mView;
    // fragment_home list view
    private ListView lv_home;
    // Weibo content interface
    private StatusesAPI mStatusesAPI;
    // All Weibo content collection
    private StatusList mStatusList;

    /**
     * show the weibo home page
     *
     * @comment by BriceChou
     * @datetime 16-6-6 17:28
     * @TODO to show the weibo content
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        initWeiboContent();
        /**
         * Main page titlebar
         *
         * @comment by BriceChou
         * @datetime 16-6-14 17:56
         * @TODO Show the current user name
         */
        new TitleBuilder(mView)
                .setCenterText("帐号昵称")
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

    /**
     * initial the weibo content and display in the home page
     *
     * @author BriceChou
     * @datetime 16-6-6 15:14
     */
    private void initWeiboContent() {
        mStatusesAPI = new StatusesAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        mStatusesAPI.friendsTimeline(0, 0, 20, 1, false, 0, false, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);

                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"statuses\"")) {
                        // the Status instance load the data from JSON data.
                        mStatusList = mStatusList.parse(response);
                        lv_home.setAdapter(new WeiboHomeAdapter(mMainActivity, mStatusList.statusList));
                    }
                }
            }
        });
    }

    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_home, null);
        lv_home = (ListView) mView.findViewById(R.id.lv_home);
    }
}
