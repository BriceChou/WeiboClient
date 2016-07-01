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
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
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
    private int mCurrentPage = 1;
    private boolean mIsPullToDown = false;

    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_home, null);
        mFootView = View.inflate(mMainActivity, R.layout.footview_loading, null);
        mRefreshListViewHome = (PullToRefreshListView) mView.findViewById(R.id.lv_home);
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
        mRefreshListViewHome.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadStatusData(1);
            }
        });
        mRefreshListViewHome.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                addFootView(mRefreshListViewHome, mFootView);
                loadStatusData(mCurrentPage + 1);
            }
        });
    }

    private void initWeiboContent() {
        loadStatusData(1);
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

    private void loadStatusData(final int page) {
        loadStatusData(0, 0, page);
    }

    private void loadStatusData(final long sinceId, final long maxId, final int page) {
        mStatusesAPI.homeTimeline(sinceId, maxId, Constants.SHOW_STATUS_COUNTS, page, false, 0, false, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"statuses\"")) {
                        // the Status instance load the data from JSON data.
                        mCurrentPage = page;
                        mStatusList = StatusList.parse(response);
                        if (mStatusList.statusList.size() > 0) {
                            setViewData(mStatusList.statusList);
                        }
                    }
                }
            }
        });
    }

    private void refreshViewDone() {
        mRefreshListViewHome.onRefreshComplete();
        removeFootView(mRefreshListViewHome, mFootView);
    }

    private void setViewData(ArrayList<Status> statuses) {
        ArrayList<Status> mTempStatuses = new ArrayList<Status>();
        mTempStatuses.addAll(mStatuses);
        if (mStatuses != null) {
            mStatuses.clear();
            for (Status tempSatus : statuses) {
                if (!mStatuses.add(tempSatus)) mStatuses.remove(tempSatus);
            }
            for (Status tempSatus : mTempStatuses) {
                if (!mStatuses.add(tempSatus)) mStatuses.remove(tempSatus);
            }
        } else {
            for (Status tempSatus : statuses) {
                if (!mStatuses.add(tempSatus)) mStatuses.remove(tempSatus);
            }
        }
        mHomeAdapter = new HomeAdapter(mMainActivity, mStatuses);
        mRefreshListViewHome.setAdapter(mHomeAdapter);
        refreshViewDone();
    }

    private void addFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() == 1) {
            lv.addFooterView(footView);
        }
    }

    private void removeFootView(PullToRefreshListView plv, View footView) {
        ListView lv = plv.getRefreshableView();
        if (lv.getFooterViewsCount() > 1) {
            lv.removeFooterView(footView);
        }
    }
}
