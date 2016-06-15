package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bricechou.weiboclient.adapter.WeiboHomeAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.legacy.FriendshipsAPI;
import com.sina.weibo.sdk.openapi.models.StatusList;

/**
 * Created by sdduser on 5/28/16.
 */
public class UserFragment extends BaseFragment {
    private View mView;
    private ListView mFollowList;
    private FriendshipsAPI mFriendshipsAPI;
    private StatusList mStatusList;
    private Long mUid;
    private Oauth2AccessToken mOauth2AccessToken;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        loadFollowList();
        return mView;
    }


    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_user,null);
        mFollowList = (ListView)mView.findViewById(R.id.lv_follow_list);
        mFriendshipsAPI = new FriendshipsAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.getAccessToken(mMainActivity));

        mOauth2AccessToken = LoginUserToken.getAccessToken(mMainActivity);
        mUid = Long.parseLong(mOauth2AccessToken.getUid());
        Log.i(".................UID",mUid+"");
        //titlebar
        new TitleBuilder(mView)
                .setCenterText("我")
                .setLeftText("添加好友")
                .setRightText("设置")
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
    }

    private void loadFollowList() {
        /**
         * 获取用户的关注列表。
         *
         * @param uid           需要查询的用户UID
         * @param count         单页返回的记录条数，默认为50，最大不超过200
         * @param cursor        返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0
         * @param trim_status   返回值中user字段中的status字段开关，false：返回完整status字段、true：status字段仅返回status_id，默认为true
         * @param listener      异步请求回调接口
         */

        mFriendshipsAPI.friends(mUid, 20, 0, false, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                Log.i("................",response);

//                if (!TextUtils.isEmpty(response)) {
//                    if (response.startsWith("{\"users\"")) {
//                        // the Status instance load the data from JSON data.
//                        mStatusList = mStatusList.parse(response);
//                        mFollowList.setAdapter(new WeiboHomeAdapter(mMainActivity, mStatusList.statusList));
//                    }
//                }
            }
        });
    }
}
