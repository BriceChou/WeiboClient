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
import com.bricechou.weiboclient.adapter.HomeAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {
    private static final String TAG = "fragment.HomeFragment";
    private View mView;
    // All Weibo content collection
    private StatusList mStatusList;
    // To get Weibo content interface
    private StatusesAPI mStatusesAPI;
    private ArrayList<Status> mStatuses;
    private HomeAdapter mHomeAdapter;
    // Is getting a new weibo content or old weibo content?
    private boolean mPullToDown = false;
    // Record the Weibo content id in the top of current page.
    private long mSinceId = 0;
    // Record the Weibo content id in the bottom of current page.
    private long mMaxId = 0;
    // To record user current weibo content position
    private int mListViewY = 0;
    // fragment_home pull to refresh list view
    private PullToRefreshListView mRefreshListViewHome;
//    private View mFootView;

    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_home, null);
//        mFootView = View.inflate(mMainActivity, R.layout.footview_loading, null);
        mRefreshListViewHome = (PullToRefreshListView) mView.findViewById(R.id.lv_home);
        // To set the refresh list view can up and down refresh page.
        mRefreshListViewHome.setMode(Mode.BOTH);
        // @comment by BriceChou
        // @datetime 16-6-14 17:56
        // @FIXME Show the current user name
        new TitleBuilder(mView)
                .setCenterText("周晢")
                .setLeftImage(R.drawable.icon_friendattention)
                .setRightImage(R.drawable.icon_radar)
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

    private void initWeiboContent() {
        loadStatusData(0, 0, Constants.SHOW_STATUS_COUNTS, 1);
    }

    /**
     * @TODO add foot view
     * @author BriceChou
     */
    private void initRefreshView() {
//        mRefreshListViewHome.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
//            @Override
//            public void onLastItemVisible() {
//                addFootView(mRefreshListViewHome, mFootView);
//            }
//        });
        mRefreshListViewHome.setOnRefreshListener(new OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPullToDown = true;
                // Load the up-to-date weibo content
                loadSinceStatusData(mSinceId);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPullToDown = false;
                // Load the old weibo content
                loadMaxStatusData(mMaxId);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mStatusesAPI = new StatusesAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        mStatuses = new ArrayList<Status>();
        initView();
        initWeiboContent();
        initRefreshView();
        return mView;
    }

    private void loadSinceStatusData(final long sinceId) {
        loadStatusData(sinceId, 0, Constants.SHOW_STATUS_COUNTS, 1);
    }

    private void loadMaxStatusData(final long maxId) {
        loadStatusData(0, maxId, Constants.SHOW_STATUS_COUNTS, 1);
    }

    private void loadStatusData(final long sinceId, final long maxId, final int counts, final int page) {
        mStatusesAPI.homeTimeline(sinceId, maxId, counts, page, false, 1, false, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"statuses\"")) {
                        // the Status instance load the data from JSON data.
                        mStatusList = StatusList.parse(response);
                        if (null != mStatusList.statusList) {
                            setViewData(mStatusList.statusList);
                        } else {
                            Log.d(TAG, "Refresh onComplete: No Result.");
                            refreshViewDone();
                        }
                    }
                }
            }
        });
    }

    private void refreshViewDone() {
        mRefreshListViewHome.onRefreshComplete();
//        removeFootView(mRefreshListViewHome, mFootView);
    }

    private void setViewData(ArrayList<Status> statuses) {
        ArrayList<Status> tempStatuses = new ArrayList<Status>();
        // To judge is loading a new weibo content.
        if (mPullToDown) {
            if (null != mStatuses && mStatuses.size() > 0) {
                // Remove the duplicate weibo content.
                String newStatusId = statuses.get(statuses.size() - 1).id;
                if (mStatuses.get(0).id.equals(newStatusId)) {
                    mStatuses.remove(0);
                }
            }
            // Make the recent weibo content show in the top of page.
            tempStatuses.addAll(mStatuses);
            mStatuses.clear();
            mStatuses.addAll(statuses);
            mStatuses.addAll(tempStatuses);
            mListViewY = 0;
        } else {
            if (null != mStatuses && mStatuses.size() > 0) {
                // Remove the duplicate weibo content.
                String oldStatusId = statuses.get(0).id;
                if (mStatuses.get(mStatuses.size() - 1).id.equals(oldStatusId)) {
                    mStatuses.remove(mStatuses.size() - 1);
                }
            }
            mListViewY = mStatuses.size();
            mStatuses.addAll(statuses);
        }
        mMaxId = Long.parseLong(statuses.get(statuses.size() - 1).id);
        mSinceId = Long.parseLong(statuses.get(0).id);
        refreshViewDone();
        // reset all data
        mPullToDown = false;
        mHomeAdapter = new HomeAdapter(mMainActivity, mStatuses);
        mRefreshListViewHome.setAdapter(mHomeAdapter);
        mRefreshListViewHome.getRefreshableView().setSelection(mListViewY);
    }

//    private void addFootView(PullToRefreshListView plv, View footView) {
//        ListView lv = plv.getRefreshableView();
//        if (lv.getFooterViewsCount() == 1) {
//            lv.addFooterView(footView);
//        }
//    }
//
//    private void removeFootView(PullToRefreshListView plv, View footView) {
//        ListView lv = plv.getRefreshableView();
//        if (lv.getFooterViewsCount() > 1) {
//            lv.removeFooterView(footView);
//        }
//    }
}
