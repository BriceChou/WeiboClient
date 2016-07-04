package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.text.TextUtils;
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
    private View mFootView;
    private PullToRefreshListView mRefreshListViewHome; // fragment_home list view
    private StatusList mStatusList; // All Weibo content collection
    private StatusesAPI mStatusesAPI; // Weibo content interface
    private ArrayList<Status> mStatuses;
    private HomeAdapter mHomeAdapter;
    private boolean mPullToDown = false;
    private long mSinceId = 0;
    private long mMaxId = 0;
    private int mListViewY = 0;

    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_home, null);
        mFootView = View.inflate(mMainActivity, R.layout.footview_loading, null);
        mRefreshListViewHome = (PullToRefreshListView) mView.findViewById(R.id.lv_home);
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
                loadSinceStatusData(mSinceId);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPullToDown = false;
                loadMaxStatusData(mMaxId);
            }
        });
    }

    private void initWeiboContent() {
        loadStatusData(0, 0, Constants.SHOW_STATUS_COUNTS, 1);
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
                        if (mStatusList.statusList.size() > 0) {
                            setViewData(mStatusList.statusList);
                        } else {
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
        if (mPullToDown) {
            if (mStatuses.size() > 0) {
                String newStatusId = statuses.get(statuses.size() - 1).id;
                if (mStatuses.get(0).id.equals(newStatusId)) {
                    mStatuses.remove(0);
                }
            }
            tempStatuses.addAll(mStatuses);
            mStatuses.clear();
            mStatuses.addAll(statuses);
            mStatuses.addAll(tempStatuses);
            mListViewY = 0;
        } else {
            if (mStatuses.size() > 0) {
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
