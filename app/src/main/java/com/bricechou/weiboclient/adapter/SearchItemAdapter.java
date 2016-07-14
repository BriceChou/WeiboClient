package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.model.SearchItem;

import java.util.List;

/**
 * Created by user on 6/21/16.
 */
public class SearchItemAdapter extends BaseAdapter {

    private Context context;
    private List<SearchItem> datas;

    public SearchItemAdapter(Context context, List<SearchItem> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public SearchItem getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_search, null);
            holder.mViewDivider = convertView.findViewById(R.id.v_divider);
            holder.mImageViewIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.mTextViewSubhead = (TextView) convertView.findViewById(R.id.tv_subhead);
            holder.mTextViewCaption = (TextView) convertView.findViewById(R.id.tv_caption);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // set data
        SearchItem item = getItem(position);
        holder.mImageViewIcon.setImageResource(item.getImageId());
        holder.mTextViewSubhead.setText(item.getSubhead());
        holder.mTextViewCaption.setText(item.getCaption());

        holder.mViewDivider.setVisibility(item.isShowTopDivider() ?
                View.VISIBLE : View.GONE);

        return convertView;
    }

    public static class ViewHolder {
        //item_search.xml
        public View mViewDivider;
        public ImageView mImageViewIcon;
        public TextView mTextViewSubhead;
        public TextView mTextViewCaption;
    }

}
