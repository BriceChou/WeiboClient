package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.utils.TimeFormat;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.models.Comment;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;

/**
 * Created by user on 6/30/16.
 */
public class CommentsAdapter extends BaseAdapter {
    private final static String TAG = "weiboclient.adapter.CommentsAdapter";
    private Context mContext;
    private ArrayList<Comment> mCommentList;
    private ImageLoader mImageLoader;

    public CommentsAdapter(Context mContext, ArrayList<Comment> mCommentsList) {
        this.mContext = mContext;
        this.mCommentList = mCommentsList;
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        if (mCommentList != null) {
            return mCommentList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Comment getItem(int position) {
        return mCommentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final CommentViewHolder holder;
        if (convertView == null) {
            holder = new CommentViewHolder();
            convertView = View.inflate(mContext, R.layout.item_comment, null);
            // user avatar
            holder.sImageViewPortrait = (ImageView) convertView
                    .findViewById(R.id.iv_portrait);
            holder.sRelativeLayoutContent = (RelativeLayout) convertView
                    .findViewById(R.id.rl_content);
            holder.sTextViewUsername = (TextView) convertView
                    .findViewById(R.id.tv_username);
            holder.sTextViewCaption = (TextView) convertView
                    .findViewById(R.id.tv_caption);
            //user comment
            holder.sTextViewComment = (TextView) convertView
                    .findViewById(R.id.tv_comment);
            convertView.setTag(holder);
        } else {
            holder = (CommentViewHolder) convertView.getTag();
        }
        // bind data into the view
        final Comment comment = getItem(position);
        User user = comment.user;
        mImageLoader.displayImage(user.profile_image_url, holder.sImageViewPortrait);
        holder.sTextViewUsername.setText(user.name);
        holder.sTextViewUsername.setTextSize(14);
        holder.sTextViewCaption.setText(TimeFormat.timeToString(comment.created_at));
        holder.sTextViewCaption.setTextSize(10);
        holder.sTextViewComment.setText(comment.text);
        return convertView;
    }

    public static class CommentViewHolder {
        // include_portrait xml file content
        public ImageView sImageViewPortrait;
        public RelativeLayout sRelativeLayoutContent;
        // user name
        public TextView sTextViewUsername;
        // user identification form what phone client
        public TextView sTextViewCaption;
        // user comment
        public TextView sTextViewComment;

    }

}
