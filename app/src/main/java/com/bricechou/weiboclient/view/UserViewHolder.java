package com.bricechou.weiboclient.view;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by user on 6/20/16.
 */
public class UserViewHolder {

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
