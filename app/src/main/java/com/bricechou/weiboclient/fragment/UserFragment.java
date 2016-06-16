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
import com.bricechou.weiboclient.adapter.PersonalCenterAdaper;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.model.UserCounts;
import com.bricechou.weiboclient.model.UserList;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.FriendshipsAPI;

/**
 * Created by sdduser on 5/28/16.
 */
public class UserFragment extends BaseFragment {
    private final static String TAG = "UserFragment";
    //private StatusesAPI mStatusesAPI;
    private PersonalCenterAdaper mPersonalCenterAdaper;
    private UserCounts mUserCounts;
    private View mView;
    private ListView mFollowList;
    private LinearLayout mUserInfo;
    private FriendshipsAPI mFriendshipsAPI;
    private UsersAPI mUsersAPI;
    private Long mUid;
    private long[] mUids;
    private UserList mUserList;
    private Oauth2AccessToken mOauth2AccessToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        loadFollowList();
        return mView;
    }


    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_user, null);
        mFollowList = (ListView) mView.findViewById(R.id.lv_follow_list);
        mUserInfo = (LinearLayout) mView.findViewById(R.id.ll_content_info);
        mFriendshipsAPI = new FriendshipsAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        mUsersAPI = new UsersAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
//        mOauth2AccessToken = LoginUserToken.getAccessToken(mMainActivity);
//        mUid = Long.parseLong(mOauth2AccessToken.getUid());
        mUid = Long.parseLong("2851891152");
        mUids = new long[1];
        mUids[0] = Long.parseLong("2851891152");
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

        mFriendshipsAPI.friends(mUid, 50, 0, true, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"users\"")) {
                        Log.i("................", response);
                        // the Status instance load the data from JSON data.
                        mUserList = mUserList.parse(response);
                        mPersonalCenterAdaper = new PersonalCenterAdaper(mMainActivity, mUserList.userList);
                        mFollowList.setAdapter(mPersonalCenterAdaper);
                        //mStatusesAPI = new StatusesAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
//                        Log.d(TAG, "total_number: " + mUserList.total_number);
//                        Log.d(TAG, "user: " + mUserList.userList.get(0));
//                        Log.d(TAG, "status_id: " + mUserList.userList.get(1).status_id);
//                        long id = 3986099586186146L;
//                        mStatusesAPI.show(id,new WeiboRequestListener(mMainActivity){
//                            @Override
//                            public void onComplete(String response) {
//                                super.onComplete(response);
//                                Log.d(TAG, "mStatusesAPI.show :"+ response);
//                            }
//                        });
                    }
                }
            }
        });

        mUsersAPI.counts(mUids, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                Log.i("................", response);
//                if (!TextUtils.isEmpty(response)) {
//                    mUserCounts = mUserCounts.parse(response);
//                    mPersonalCenterAdaper.setmUserCounts(mUserCounts);
//                    Log.d(TAG, "mUserCounts: " + mUserCounts.followers_count);
//                }
            }
        });
    }
}
