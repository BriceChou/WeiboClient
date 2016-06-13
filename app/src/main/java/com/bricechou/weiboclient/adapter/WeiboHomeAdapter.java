package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bricechou.weiboclient.R;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;

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
            convertView = View.inflate(mContext,R.layout.item_status, null);
            holder.ll_mainContent = (LinearLayout) convertView
                    .findViewById(R.id.ll_mainContent);

            holder.iv_portrait = (ImageView) convertView
                    .findViewById(R.id.iv_portrait);
            holder.rl_content = (RelativeLayout) convertView
                    .findViewById(R.id.rl_content);
            holder.tv_username = (TextView) convertView
                    .findViewById(R.id.tv_username);
            holder.tv_caption = (TextView) convertView
                    .findViewById(R.id.tv_caption);

            holder.tv_item_content = (TextView) convertView
                    .findViewById(R.id.tv_item_content);
            // holder.include_status_image = (FrameLayout) convertView
            //        .findViewById(R.id.include_status_image);

            holder.include_status_retweeted = (LinearLayout) convertView
                    .findViewById(R.id.include_status_retweeted);
            holder.tv_retweeted_content = (TextView) holder.include_status_retweeted
                    .findViewById(R.id.tv_retweeted_content);
            // holder.include_retweeted_status_image = (FrameLayout) holder.include_status_retweeted
            //        .findViewById(R.id.include_status_image);

            holder.ll_retweet = (LinearLayout) convertView
                    .findViewById(R.id.ll_retweet);
            holder.iv_retweet = (ImageView) convertView
                    .findViewById(R.id.iv_retweet);
            holder.tv_retweet = (TextView) convertView
                    .findViewById(R.id.tv_retweet);
            holder.ll_comment = (LinearLayout) convertView
                    .findViewById(R.id.ll_comment);
            holder.iv_comment = (ImageView) convertView
                    .findViewById(R.id.iv_comment);
            holder.tv_comment = (TextView) convertView
                    .findViewById(R.id.tv_comment);
            holder.ll_like = (LinearLayout) convertView
                    .findViewById(R.id.ll_like);
            holder.iv_like = (ImageView) convertView
                    .findViewById(R.id.iv_like);
            holder.tv_like = (TextView) convertView
                    .findViewById(R.id.tv_like);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // bind data
        final Status status = getItem(position);
        User user = status.user;
        holder.tv_username.setText(user.name);
        holder.tv_caption.setText(status.created_at + " 来自 " + status.source);
        holder.tv_item_content.setText(status.text);

        // retweeted weibo content
        Status retweeted_status = status.retweeted_status;

        if (retweeted_status != null) {
            User retUser = retweeted_status.user;
            holder.include_status_retweeted.setVisibility(View.VISIBLE);
            holder.tv_item_content.setText("@" + retUser.name + ":" + retweeted_status.text);
        } else {
            holder.include_status_retweeted.setVisibility(View.GONE);
        }
        holder.tv_retweet.setText(status.reposts_count == 0 ?
                "转发" : status.reposts_count + "");
        holder.tv_comment.setText(status.comments_count == 0 ?
                "评论" : status.comments_count + "");
        holder.tv_like.setText(status.attitudes_count == 0 ?
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
        public LinearLayout ll_mainContent;

        // include_portrait xml file content
        public ImageView iv_portrait;
        public RelativeLayout rl_content;
        // user name
        public TextView tv_username;
        // user identification form what phone client?
        public TextView tv_caption;

        // item_status.xml -->> weibo content
        public TextView tv_item_content;
        // include_status_image.xml -->> weibo content image
        // public FrameLayout include_status_image;

        // include_status_retweeted.xml -->> retweeted weibo content comment
        public LinearLayout include_status_retweeted;
        public TextView tv_retweeted_content;
        // public FrameLayout include_retweeted_status_image;

        // include_status_controlbar.xml -->> retweet,comment,like
        public LinearLayout ll_retweet;
        public ImageView iv_retweet;
        public TextView tv_retweet;
        public LinearLayout ll_comment;
        public ImageView iv_comment;
        public TextView tv_comment;
        public LinearLayout ll_like;
        public ImageView iv_like;
        public TextView tv_like;
    }

}
