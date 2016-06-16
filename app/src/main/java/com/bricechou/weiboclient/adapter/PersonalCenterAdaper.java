package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.model.UserCounts;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;

/**
 * Created by user on 6/15/16.
 */
public class PersonalCenterAdaper extends BaseAdapter {
    private final static String TAG = "PersonalCenterAdaper";
    private Context mContext;
    private ArrayList<User> mUserList;
    private UserCounts mUserCounts;

    public PersonalCenterAdaper(Context context, ArrayList<User> userList) {
        this.mContext = context;
        this.mUserList = userList;
    }

    public void setmUserCounts(UserCounts mUserCounts) {
        this.mUserCounts = mUserCounts;
    }

    public UserCounts getmUserCounts() {
        return mUserCounts;
    }

    @Override
    public int getCount() {
        return mUserList.size();
    }

    @Override
    public User getItem(int position) {
        return mUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_friends, null);
            holder.mLinearLayoutContent = (LinearLayout) convertView
                    .findViewById(R.id.ll_content);

            holder.mImageViewAvatar = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);
            holder.mRelativeLayoutContent = (RelativeLayout) convertView
                    .findViewById(R.id.rl_content);
            holder.mTextViewUsername = (TextView) convertView
                    .findViewById(R.id.tv_username);
            holder.mTextViewCaption = (TextView) convertView
                    .findViewById(R.id.tv_caption);


            holder.mTextViewStatus = (TextView) convertView
                    .findViewById(R.id.tv_status_count);
            holder.mTextViewFriends = (TextView) convertView
                    .findViewById(R.id.tv_friends_count);
            holder.mTextViewFollows = (TextView) convertView
                    .findViewById(R.id.tv_follows_count);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // bind data
        final User user = getItem(position);
        holder.mTextViewUsername.setText(user.screen_name);
        holder.mTextViewCaption.setText(user.description);

        holder.mTextViewStatus.setText(mUserCounts.statuses_count);
        holder.mTextViewFriends.setText(mUserCounts.friends_count);
        holder.mTextViewFollows.setText(mUserCounts.followers_count);

        return convertView;
    }

    public static class ViewHolder {

        // item_friends xml file content
        public LinearLayout mLinearLayoutContent;

        public ImageView mImageViewAvatar;
        public RelativeLayout mRelativeLayoutContent;
        // user name
        public TextView mTextViewUsername;
        //weibo  brief introduction
        public TextView mTextViewCaption;

        //include_userinfo_interaction xml
        public TextView mTextViewStatus;
        public TextView mTextViewFriends;
        public TextView mTextViewFollows;

    }
}
