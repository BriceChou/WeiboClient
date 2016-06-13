package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.utils.TimeFormat;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;

import com.bricechou.weiboclient.utils.StringFormat;

/**
 * @author BriceChou
 * @datetime 16-6-6 17:39
 * @TODO to konw the adapter concept
 */
public class WeiboHomeAdapter extends BaseAdapter {
    private final static String TAG = "WeiboHomeAdapter";
    private Context mContext;
    private ArrayList<Status> mStatusList;

    public WeiboHomeAdapter(Context context, ArrayList<Status> statusList) {
        this.mContext = context;
        this.mStatusList = statusList;
    }

    @Override
    public int getCount() {
        return mStatusList.size();
    }

    @Override
    public Status getItem(int i) {
        return mStatusList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_status, null);
            holder.mLinearLayoutMainContent = (LinearLayout) convertView
                    .findViewById(R.id.ll_mainContent);

            holder.mImageViewPortrait = (ImageView) convertView
                    .findViewById(R.id.iv_portrait);
            holder.mRelativeLayoutContent = (RelativeLayout) convertView
                    .findViewById(R.id.rl_content);
            holder.mTextViewUsername = (TextView) convertView
                    .findViewById(R.id.tv_username);
            holder.mTextViewCaption = (TextView) convertView
                    .findViewById(R.id.tv_caption);

            holder.mTextViewStatusContent = (TextView) convertView
                    .findViewById(R.id.tv_item_content);
           /* holder.mFrameLayoutStatusImage = (FrameLayout) convertView
                    .findViewById(R.id.include_status_image);*/

            holder.mLinearLayoutStatusRetweeted = (LinearLayout) convertView
                    .findViewById(R.id.include_status_retweeted);
            holder.mTextViewRetweetedContent = (TextView) holder.mLinearLayoutStatusRetweeted
                    .findViewById(R.id.tv_retweeted_content);
            /*holder.mFrameLayoutRetweetedStatusImage = (FrameLayout) holder.mLinearLayoutStatusRetweeted
                    .findViewById(R.id.include_status_image);*/

            holder.mLinearLayoutRetweet = (LinearLayout) convertView
                    .findViewById(R.id.ll_retweet);
            holder.mImageViewRetweet = (ImageView) convertView
                    .findViewById(R.id.iv_retweet);
            holder.mTextViewRetweet = (TextView) convertView
                    .findViewById(R.id.tv_retweet);
            holder.mLinearLayoutComment = (LinearLayout) convertView
                    .findViewById(R.id.ll_comment);
            holder.mImageViewComment = (ImageView) convertView
                    .findViewById(R.id.iv_comment);
            holder.mTextViewComment = (TextView) convertView
                    .findViewById(R.id.tv_comment);
            holder.mLinearLayoutLike = (LinearLayout) convertView
                    .findViewById(R.id.ll_like);
            holder.mImageViewLike = (ImageView) convertView
                    .findViewById(R.id.iv_like);
            holder.mTextViewLike = (TextView) convertView
                    .findViewById(R.id.tv_like);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // bind data
        final Status status = getItem(position);
        User user = status.user;
        holder.mTextViewUsername.setText(user.name);
        holder.mTextViewCaption.setText(TimeFormat.timeToString(status.created_at)+ " 来自 " + StringFormat.formatStatusSource(status.source));
        holder.mTextViewStatusContent.setText(status.text);

        // retweeted weibo content
        Status retweeted_status = status.retweeted_status;

        if (retweeted_status != null) {
            User retUser = retweeted_status.user;
            holder.mLinearLayoutStatusRetweeted.setVisibility(View.VISIBLE);
            holder.mTextViewRetweetedContent.setText("@" + retUser.name + ":" + retweeted_status.text);
        } else {
            holder.mLinearLayoutStatusRetweeted.setVisibility(View.GONE);
        }
        holder.mTextViewRetweet.setText(status.reposts_count == 0 ?
                "转发" : status.reposts_count + "");
        holder.mTextViewComment.setText(status.comments_count == 0 ?
                "评论" : status.comments_count + "");
        holder.mTextViewLike.setText(status.attitudes_count == 0 ?
                "赞" : status.attitudes_count + "");
        return convertView;
    }

    /**
     * show Weibo main content layout
     *
     * @author BriceChou
     * @datetime 16-6-12 11:39
     */
    public static class ViewHolder {

        // item_status xml file content
        public LinearLayout mLinearLayoutMainContent;

        // include_portrait xml file content
        public ImageView mImageViewPortrait;
        public RelativeLayout mRelativeLayoutContent;
        // user name
        public TextView mTextViewUsername;
        // user identification form what phone client?
        public TextView mTextViewCaption;

        // item_status.xml -->> weibo content
        public TextView mTextViewStatusContent;
        // include_status_image.xml -->> weibo content image
        public FrameLayout mFrameLayoutStatusImage;

        // include_status_retweeted.xml -->> retweeted weibo content comment
        public LinearLayout mLinearLayoutStatusRetweeted;
        public TextView mTextViewRetweetedContent;
        public FrameLayout mFrameLayoutRetweetedStatusImage;

        // include_status_controlbar.xml -->> retweet,comment,like
        public LinearLayout mLinearLayoutRetweet;
        public ImageView mImageViewRetweet;
        public TextView mTextViewRetweet;
        public LinearLayout mLinearLayoutComment;
        public ImageView mImageViewComment;
        public TextView mTextViewComment;
        public LinearLayout mLinearLayoutLike;
        public ImageView mImageViewLike;
        public TextView mTextViewLike;
    }

}
