package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bricechou.weiboclient.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;

/**
 * Created by user on 6/15/16.
 */
public class UserAdapter extends BaseAdapter {
    private final static String TAG = "UserAdapter";
    private Context mContext;
    private ArrayList<User> mUserList;
    private User mUserInfo;
    private ImageLoader mImageLoader;

    public UserAdapter(Context context, ArrayList<User> userList) {
        this.mContext = context;
        this.mUserList = userList;
    }

    public UserAdapter setUserInfo(User userInfo) {
        this.mUserInfo = userInfo;
        return this;
    }

    public void holderLoginData(View view,ImageLoader mImageLoader) {

        ViewHolder holder = new ViewHolder();
        holder.mImageViewAvatar = (ImageView) view
                .findViewById(R.id.iv_avatar);
        holder.mTextViewLoginName = (TextView) view
                .findViewById(R.id.tv_loginname);
        holder.mTextViewLoginCaption = (TextView) view
                .findViewById(R.id.tv_logincaption);
        holder.mTextViewStatus = (TextView) view
                .findViewById(R.id.tv_status_count);
        holder.mTextViewFriends = (TextView) view
                .findViewById(R.id.tv_friends_count);
        holder.mTextViewFollows = (TextView) view
                .findViewById(R.id.tv_follows_count);
        mImageLoader.displayImage(mUserInfo.profile_image_url,holder.mImageViewAvatar);
        holder.mTextViewLoginName.setText(mUserInfo.screen_name);
        holder.mTextViewLoginCaption.setText("简介：" + mUserInfo.description);
        holder.mTextViewFriends.setText(String.valueOf(mUserInfo.friends_count));
        holder.mTextViewStatus.setText(String.valueOf(mUserInfo.statuses_count));
        holder.mTextViewFollows.setText(String.valueOf(mUserInfo.followers_count));
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

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // bind data
        final User user = getItem(position);
        ImageLoader.getInstance().displayImage(user.profile_image_url,holder.mImageViewAvatar);
        holder.mTextViewUsername.setText(user.screen_name);
        holder.mTextViewCaption.setText(user.description);
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

        public LinearLayout mLinearLayoutInteraction;
        //include_userinfo_interaction xml
        public TextView mTextViewStatus;
        public TextView mTextViewFriends;
        public TextView mTextViewFollows;

        //include_logininfo xml
        public TextView mTextViewLoginName;
        public TextView mTextViewLoginCaption;

    }
}
