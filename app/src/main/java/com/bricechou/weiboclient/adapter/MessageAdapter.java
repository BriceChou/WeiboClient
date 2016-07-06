package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.utils.TimeFormat;
import com.bricechou.weiboclient.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.models.Comment;

import java.util.ArrayList;

/**
 * Binding the current login user message list data.
 *
 * @author BriceChou
 * @datetime 16-6-20 15:21
 * @TODO This is not important feature. So we finish it at the end of the project.
 */
public class MessageAdapter extends BaseAdapter {
    private static final String TAG = "weiboclient.adapter.MessageAdapter";
    private Context mContext;
    private ArrayList<Comment> mComments;
    private ImageLoader mImageLoader;
    private Oauth2AccessToken mOauthToken;

    public MessageAdapter(Context context, ArrayList<Comment> comments) {
        this.mContext = context;
        this.mComments = comments;
        mImageLoader = ImageLoader.getInstance();
        mOauthToken = LoginUserToken.getAccessToken(context);
    }

    @Override
    public int getCount() {
        return mComments.size();
    }

    @Override
    public Comment getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MessageViewHolder holder;
        if (null == convertView) {
            holder = new MessageViewHolder();
            convertView = View.inflate(mContext, R.layout.include_message_bottom, null);
            // To show the user's information that he send message to me.
            holder.sImageViewMessagePortrait = (CircleImageView) convertView
                    .findViewById(R.id.civ_message_profile);
            holder.sTextViewtvMessageUsername = (TextView) convertView
                    .findViewById(R.id.tv_message_username);
            holder.sTextViewtvMessageRemark = (TextView) convertView
                    .findViewById(R.id.tv_message_remark);
            holder.sTextViewtvMessageReply = (TextView) convertView
                    .findViewById(R.id.tv_message_reply);
            holder.sTextViewtvMessageCaption = (TextView) convertView
                    .findViewById(R.id.tv_message_caption);
            // To show comment information what I say to this user.
            holder.sLinearLayoutMessageComment = (LinearLayout) convertView
                    .findViewById(R.id.include_message_comment);
            holder.sTextViewtvMessageComment = (TextView) holder.sLinearLayoutMessageComment
                    .findViewById(R.id.tv_message_comment);
            // Message control-bar
            holder.sLinearLayoutCheck = (LinearLayout) convertView
                    .findViewById(R.id.ll_message_check);
            holder.sLinearLayoutReply = (LinearLayout) convertView
                    .findViewById(R.id.ll_message_reply);
            convertView.setTag(holder);
        } else {
            holder = (MessageViewHolder) convertView.getTag();
        }

        final Comment comment = mComments.get(position);
        // Config friend's reply and comment information
        mImageLoader.displayImage(comment.user.profile_image_url,
                holder.sImageViewMessagePortrait);
        holder.sTextViewtvMessageUsername.setText(comment.user.name);
        if (!TextUtils.isEmpty(comment.user.remark)){
            holder.sTextViewtvMessageRemark.setText("("+comment.user.remark+")");
        }
        holder.sTextViewtvMessageReply.setText(comment.text);
        holder.sTextViewtvMessageCaption.setText(TimeFormat.timeToString(comment.created_at) +
                " 来自 " + Html.fromHtml(comment.source));
        // Show the original information commented by current user.
        if (null != comment.reply_comment) {
            holder.sLinearLayoutMessageComment.setVisibility(View.VISIBLE);
            holder.sTextViewtvMessageComment.setText("回复我的评论： " + comment.reply_comment.text);
        } else {
            holder.sLinearLayoutMessageComment.setVisibility(View.VISIBLE);
            holder.sTextViewtvMessageComment.setText("评论我的微博：" + comment.status.text);
        }

        holder.sLinearLayoutReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "亲亲，您暂时没有权限查看哟!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.sLinearLayoutCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "亲亲，您已经进行回复咯!", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    /**
     * To manage the Message page layout resource.
     * Just create this class once time.
     *
     * @author BriceChou
     * @datetime 16-6-12 11:39
     */
    public static class MessageViewHolder {
        public LinearLayout sLinearLayoutMessageComment;
        public CircleImageView sImageViewMessagePortrait;
        public TextView sTextViewtvMessageUsername;
        public TextView sTextViewtvMessageRemark;
        public TextView sTextViewtvMessageReply;
        public TextView sTextViewtvMessageComment;
        public TextView sTextViewtvMessageCaption;
        public LinearLayout sLinearLayoutReply;
        public LinearLayout sLinearLayoutCheck;
    }
}
