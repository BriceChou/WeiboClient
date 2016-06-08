package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bricechou.weiboclient.R;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * @author BriceChou
 * @datetime 16-6-6 17:39
 * @TODO to konw the adapter concept
 */
public class WeiboHomeAdapter extends BaseAdapter {
    private Context context;
    private StatusList mStatusList;

    public WeiboHomeAdapter(Context context, StatusList statusList) {
        this.context = context;
        this.mStatusList = statusList;
    }

    @Override
    public int getCount() {
        return mStatusList.statusList.size();
    }

    @Override
    public Status getItem(int i) {
        return mStatusList.statusList.get(i);
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
            convertView = View.inflate(context, R.layout.item_status, null);

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
            holder.include_status_image = (FrameLayout) convertView
                    .findViewById(R.id.include_status_image);

            holder.include_status_retweeted = (LinearLayout) convertView
                    .findViewById(R.id.include_status_retweeted);

            holder.tv_reweed_content = (TextView) holder.include_status_retweeted
                    .findViewById(R.id.tv_reweed_content);
            holder.include_retweeted_status_image = (FrameLayout) holder.include_status_retweeted
                    .findViewById(R.id.include_status_image);

            holder.ll_retweed = (LinearLayout) convertView
                    .findViewById(R.id.ll_retweed);
            holder.iv_retweed = (ImageView) convertView
                    .findViewById(R.id.iv_retweed);
            holder.tv_retweed = (TextView) convertView
                    .findViewById(R.id.tv_retweed);


            holder.ll_comment = (LinearLayout) convertView
                    .findViewById(R.id.ll_comment);
            holder.iv_comment = (ImageView) convertView
                    .findViewById(R.id.iv_comment);
            holder.tv_comment = (TextView) convertView
                    .findViewById(R.id.tv_comment);
            holder.ll_like = (LinearLayout) convertView
                    .findViewById(R.id.ll_like);
            holder.iv_like= (ImageView) convertView
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
        holder.tv_caption.setText(status.created_at
                + " 来自 " + status.source);
        holder.tv_item_content.setText(status.text);


        Status retweeted_status = status.retweeted_status;
        if(retweeted_status != null) {
            User retUser = retweeted_status.user;

            holder.include_status_retweeted.setVisibility(View.VISIBLE);
            holder.tv_item_content.setText("@" + retUser.name + ":" + retweeted_status.text);
        } else {
            holder.include_status_retweeted.setVisibility(View.GONE);
        }

        holder.tv_retweed.setText(status.reposts_count == 0 ?
                "转发" : status.reposts_count + "");

        holder.tv_comment.setText(status.comments_count == 0 ?
                "评论" : status.comments_count + "");

        holder.tv_like.setText(status.attitudes_count == 0 ?
                "赞" : status.attitudes_count + "");

        return convertView;
    }

    public static class ViewHolder {
        public LinearLayout ll_mainContent;
        public ImageView iv_portrait;
        public RelativeLayout rl_content;
        public TextView tv_username;
        public TextView tv_caption;

        public TextView tv_item_content;
        public FrameLayout include_status_image;

        public LinearLayout include_status_retweeted;
        public TextView tv_reweed_content;
        public FrameLayout include_retweeted_status_image;

        public LinearLayout ll_retweed;
        public ImageView iv_retweed;
        public TextView tv_retweed;
        public LinearLayout ll_comment;
        public ImageView iv_comment;
        public TextView tv_comment;
        public LinearLayout ll_like;
        public ImageView iv_like;
        public TextView tv_like;
    }

}
