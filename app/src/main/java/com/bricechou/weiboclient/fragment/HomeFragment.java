package com.bricechou.weiboclient.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.HomeAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserInformation;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdduser on 5/28/16.
 */
public class HomeFragment extends BaseFragment {
    private final static String TAG = "HomeFragment";
    private View mView;
    private PullToRefreshListView lv_home; // fragment_home list view
    private StatusList mStatusList; // All Weibo content collection
    private StatusesAPI mStatusesAPI; // Weibo content interface
    private LoginUserInformation mLoginUserInformation;
    private User mUser;
    private View mFootView;

    private HomeAdapter mHomeAdapter;
    private List<Status> statuses = new ArrayList<Status>();
    private int mCurPage = 1;

    /**
     * To show the weibo homepage
     *
     * @comment by BriceChou
     * @datetime 16-6-6 17:28
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        initWeiboContent(1);
        // initLoginUser();


        return mView;
    }

    /**
     * To save the current login user information.
     *
     * @author BriceChou
     * @datetime 16-6-20 13:59
     * @FIXME We can't save the data into a class model.
     */
    private void initLoginUser() {
        mLoginUserInformation = new LoginUserInformation();
        UsersAPI mUsersAPI = new UsersAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        // UsersAPI mUsersAPI = new UsersAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.getAccessToken());
        mUsersAPI.show(LoginUserToken.showAccessToken().getUid(), new WeiboRequestListener(mMainActivity) {
        // mUsersAPI.show(LoginUserToken.getAccessToken().getUid(), new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                mUser = mUser.parse(response);
                mLoginUserInformation.setLoginUser(mLoginUserInformation, mUser);
            }
        });
    }

    /**
     * initial the weibo content and display in the home page
     *
     * @author BriceChou
     * @datetime 16-6-6 15:14
     */
    private void initWeiboContent(final int page) {
        mStatusesAPI = new StatusesAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        // mStatusesAPI = new StatusesAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.getAccessToken());
        mStatusesAPI.friendsTimeline(0, 0, 3, page, false, 0, false, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);

                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"statuses\"")) {
                        // the Status instance load the data from JSON data.
                        mStatusList = mStatusList.parse(response);
                        lv_home.setAdapter(new HomeAdapter(mMainActivity, mStatusList.statusList));

                        if(page == 1) {
                            statuses.clear();
                        }
                        mCurPage = page;
                    }
                }


            }

        });
        Log.i(TAG, ""+mCurPage);
        //mFootView = View.inflate(mMainActivity, R.layout.footview_loading, null);
    }

    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_home, null);
        lv_home = (PullToRefreshListView) mView.findViewById(R.id.lv_home);
//        lv_home.setMode(Mode.BOTH);
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
        lv_home.setOnRefreshListener(new OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                initWeiboContent(1);
                new FinishRefresh().execute();
            }
        });
        lv_home.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                initWeiboContent(mCurPage + 1);
            }
        });

    }

    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            mHomeAdapter = new HomeAdapter(mMainActivity, mStatusList.statusList);
            mHomeAdapter.notifyDataSetChanged();
            lv_home.onRefreshComplete();
        }
    }


    //Pull down refresh
//    lv_home.setOnRefreshListener(new OnRefreshListener2<ListView>() {
//        @Override
//        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//            initWeiboContent();
//            new FinishRefresh().execute();
//            //mHomeAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
//            initWeiboContent();
//            new FinishRefresh().execute();
//            //mHomeAdapter.notifyDataSetChanged();
//        }
//    });

}
