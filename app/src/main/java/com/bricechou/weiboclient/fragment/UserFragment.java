package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.UserAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.model.UserList;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.FriendshipsAPI;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * Created by sdduser on 5/28/16.
 */
public class UserFragment extends BaseFragment {
    private final static String TAG = "UserFragment";
    private UserAdapter mUserAdapter;
    private View mView;
    private ListView mFollowList;
    private FriendshipsAPI mFriendshipsAPI;
    private UsersAPI mUsersAPI;
    private long mUid;
    private UserList mUserList;
    private User mUserInfo;
    private ImageLoader mImageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        loadUserData();
        return mView;
    }


    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_user, null);
        mFollowList = (ListView) mView.findViewById(R.id.lv_follow_list);
        mFriendshipsAPI = new FriendshipsAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        // mFriendshipsAPI = new FriendshipsAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.getAccessToken());
        mUsersAPI = new UsersAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        // mUsersAPI = new UsersAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.getAccessToken());
        // mOauth2AccessToken = LoginUserToken.getAccessToken(mMainActivity);
        // mUid = Long.parseLong(mOauth2AccessToken.getUid());
        mUid = Long.parseLong("2851891152");
        // mUid = Long.parseLong(mOauth2AccessToken.getUid());
        //titlebar
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(ImageLoaderConfiguration.createDefault(mMainActivity));
        new TitleBuilder(mView)
                .setCenterText("我")
                .setLeftText("添加好友")
                .setRightText("设置")
                .setLeftOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mMainActivity,
                                R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                    }
                })
                .setRightOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mMainActivity,
                                R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadUserData() {
        /**
         * get user's friends list
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
                        mUserAdapter = new UserAdapter(mMainActivity, mUserList.userList);
                        mFollowList.setAdapter(mUserAdapter);
                    }
                }
            }
        });

        mUsersAPI.show(mUid, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                Log.i(".....user screen name", response);
                mUserInfo = mUserInfo.parse(response);
                mUserAdapter.setUserInfo(mUserInfo).holderLoginData(mView, mImageLoader);
            }
        });
    }
}
