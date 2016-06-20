package com.bricechou.weiboclient.view;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * show Weibo main content layout
 *
 * @author BriceChou
 * @datetime 16-6-12 11:39
 */
public class HomeViewHolder {

    // item_status xml file content
    public LinearLayout mLinearLayoutMainContent;

    // include_portrait xml file content
    public ImageView mImageViewPortrait;
    public RelativeLayout mRelativeLayoutContent;
    // user name
    public TextView mTextViewUsername;
    // user identification form what phone client
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
