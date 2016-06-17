package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.PersonalCenterAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.api.UserCounts;
import com.bricechou.weiboclient.api.UserList;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.FriendshipsAPI;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * Created by sdduser on 5/28/16.
 */
public class UserFragment extends BaseFragment {
    private final static String TAG = "UserFragment";
    private PersonalCenterAdapter mPersonalCenterAdapter;
    private UserCounts mUserCounts;
    private View mView;
    private ListView mFollowList;
    private LinearLayout mLoginInfo;
    private FriendshipsAPI mFriendshipsAPI;
    private UsersAPI mUsersAPI;
    private Long mUid;
    private long[] mUids;
    private UserList mUserList;
    private Oauth2AccessToken mOauth2AccessToken;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        loadUserData();
        return mView;
    }


    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_user, null);
        mLoginInfo = (LinearLayout) mView.findViewById(R.id.ll_logininfo);
        mFollowList = (ListView) mView.findViewById(R.id.lv_follow_list);
        mFriendshipsAPI = new FriendshipsAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        mUsersAPI = new UsersAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        //mOauth2AccessToken = LoginUserToken.getAccessToken(mMainActivity);
        //mUid = Long.parseLong(mOauth2AccessToken.getUid());
        mUid = Long.parseLong("2851891152");
        mUids = new long[1];
        mUids[0] = Long.parseLong("2851891152");
        //mUids[0] = Long.parseLong(mOauth2AccessToken.getUid());
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

    private void loadUserData() {
        /**
         * 获取用户的关注列表。
         *
         * @param uid           the UID need to search
         * @param count         The number of records returned by a single page，default is 50，max 200
         * @param cursor        return cursor，The next page uses the next_cursor in the return value，previous page uses previous_cursor，default is 0
         * @param trim_status    The status field switch in the return value default is true
         * @param listener      Asynchronous request callback interface
         */

        mFriendshipsAPI.friends(mUid, 50, 0, true, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"users\"")) {
                        Log.i("................", response);
                        mUserList = mUserList.parse(response);
                        mPersonalCenterAdapter = new PersonalCenterAdapter(mMainActivity, mUserList.userList);
                        mFollowList.setAdapter(mPersonalCenterAdapter);
                    }
                }
            }
        });

        mUsersAPI.counts(mUids, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                Log.i("................", response);
                if (!TextUtils.isEmpty(response)) {
                    mUserCounts = mUserCounts.parse(response);
                    mPersonalCenterAdapter.setmUserCounts(mUserCounts).setCounts(mView);
                    Log.d(TAG, "mUserCounts: " + mUserCounts.followers_count);
                }
            }
        });
        mUsersAPI.show(mUid,new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                Log.i(".....user screen name", response);
                user = user.parse(response);
                Log.i(".....user", String.valueOf(user));
                mPersonalCenterAdaper.setUserInfo(user).holderLoginData(mView,user);
            }
        });
    }
}
