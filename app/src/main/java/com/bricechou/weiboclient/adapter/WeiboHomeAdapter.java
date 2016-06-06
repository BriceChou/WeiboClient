package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bricechou.weiboclient.model.WeiboContent;

import java.util.List;

/**
 * @author BriceChou
 * @datetime 16-6-6 17:39
 * @TODO to konw the adapter concept
 */
public class WeiboHomeAdapter extends BaseAdapter {
    private Context context;
    private List<WeiboContent> mListWeibo;

    public WeiboHomeAdapter(Context context, List<WeiboContent> mListWeibo) {
        this.context = context;
        this.mListWeibo = mListWeibo;
    }

    @Override
    public int getCount() {
        return mListWeibo.size();
    }

    @Override
    public Object getItem(int position) {
        return mListWeibo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        return null;
    }
}
