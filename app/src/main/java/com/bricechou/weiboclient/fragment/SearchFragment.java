package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.SearchItemAdapter;
import com.bricechou.weiboclient.model.SearchItem;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdduser on 5/28/16.
 */
public class SearchFragment extends BaseFragment {
    private View mView;
    private SearchItemAdapter mSearchItemAdapter;
    private List<SearchItem> mSearchItems;
    private ListView mSearchList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        setItem();
        return mView;
    }

    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_search, null);
        mSearchList = (ListView) mView.findViewById(R.id.lv_search_item);
        new TitleBuilder(mView)
                .setSearchText(R.id.titlebar_et_search)
                .setRightImage(R.drawable.icon_voice)
                .setLeftOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //leftbar click event
                        Toast.makeText(mMainActivity,
                                R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                    }
                })
                .setRightOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //rightbar click event
                        Toast.makeText(mMainActivity,
                                R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                    }
                });
        mSearchItems = new ArrayList<SearchItem>();
        mSearchItemAdapter = new SearchItemAdapter(mMainActivity, mSearchItems);
        mSearchList.setAdapter(mSearchItemAdapter);
    }

    private void setItem() {
        mSearchItems.add(new SearchItem(false, R.mipmap.icon_weibo, "热门微博", "全站最热微博尽搜罗"));
        mSearchItems.add(new SearchItem(false, R.mipmap.icon_weibo, "找人", ""));
        mSearchItems.add(new SearchItem(true, R.mipmap.icon_weibo, "超级红人节", "超级红人节精彩瞬间盘点"));
        mSearchItems.add(new SearchItem(false, R.mipmap.icon_weibo, "玩游戏", "微博上最火的游戏"));
        mSearchItems.add(new SearchItem(false, R.mipmap.icon_weibo, "周边", "遇见“张江”有缘人"));
        mSearchItems.add(new SearchItem(true, R.mipmap.icon_weibo, "股票", "赚钱的操作尽在股票组合"));
        mSearchItems.add(new SearchItem(false, R.mipmap.icon_weibo, "电影", "优惠电影票就在这里！"));
        mSearchItems.add(new SearchItem(false, R.mipmap.icon_weibo, "红人淘", "全网唯一网红资讯频道"));
        mSearchItems.add(new SearchItem(false, R.mipmap.icon_weibo, "直播", "女神明星视频直播中"));
        mSearchItems.add(new SearchItem(false, R.mipmap.icon_weibo, "微博头条", "随时随地一起看新闻"));
        mSearchItems.add(new SearchItem(true, R.mipmap.icon_weibo, "更多频道", ""));

    }
}
