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
    private ArrayList<Status> mStatuses = new ArrayList<Status>();
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
                        mStatusList = StatusList.parse(response);
                        if (page == 1) {
                            mStatuses.clear();
                        }
                        mCurrentPage = page;
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
    }

    private void setViewData(ArrayList<Status> statuses) {
        ArrayList<Status> mTotalStatuses = new ArrayList<Status>();
        if (mCurrentPage == 1) {
            int length = statuses.size(),
                    i;
            for (i = 0; i < length; i++) {
                mStatuses.add(statuses.get(i));
            }
            mHomeAdapter = new HomeAdapter(mMainActivity, mStatuses);
        } else {
            for (int j = 0; j < mStatuses.size(); j++) {
                mTotalStatuses.add(mStatuses.get(j));
            }
            for (int i = 0; i < statuses.size(); i++) {
                mTotalStatuses.add(statuses.get(i));
            }
            mHomeAdapter = new HomeAdapter(mMainActivity, mTotalStatuses);
        }
        refreshViewDone();
        mRefreshListViewHome.setAdapter(mHomeAdapter);
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
