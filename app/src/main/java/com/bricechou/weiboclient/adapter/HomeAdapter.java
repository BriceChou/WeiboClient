package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.activity.WeiboDetailActivity;
import com.bricechou.weiboclient.utils.TimeFormat;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;

/**
 * Bind user weibo content data to HomeFragment
 *
 * @author BriceChou
 * @datetime 16-6-6 17:39
 * @TODO to konw the adapter concept
 */
public class HomeAdapter extends BaseAdapter {
    private final static String TAG = "HomeAdapter";
    private Context mContext;
    private ArrayList<Status> mStatusList;
    private ImageLoader mImageLoader;

    public HomeAdapter(Context context, ArrayList<Status> statusList) {
        this.mContext = context;
        this.mStatusList = statusList;
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return mStatusList.size();
    }

    @Override
    public Status getItem(int position) {
        return mStatusList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HomeViewHolder holder;
        if (convertView == null) {
            holder = new HomeViewHolder();
            convertView = View.inflate(mContext, R.layout.item_status, null);
            holder.mLinearLayoutMainContent = (LinearLayout) convertView
                    .findViewById(R.id.ll_mainContent);
            // weibo avatar
            holder.mImageViewPortrait = (ImageView) convertView
                    .findViewById(R.id.iv_portrait);
            holder.mRelativeLayoutContent = (RelativeLayout) convertView
                    .findViewById(R.id.rl_content);
            holder.mTextViewUsername = (TextView) convertView
                    .findViewById(R.id.tv_username);
            holder.mTextViewCaption = (TextView) convertView
                    .findViewById(R.id.tv_caption);
            // weibo content
            holder.mTextViewStatusContent = (TextView) convertView
                    .findViewById(R.id.tv_item_content);
            // weibo content image
            holder.mFrameLayoutStatusImage = (FrameLayout) convertView
                    .findViewById(R.id.include_status_image);
            holder.mImageViewCustomImages = (GridView) holder.mFrameLayoutStatusImage
                    .findViewById(R.id.custom_images);
            holder.mImageViewStatusImage = (ImageView) holder.mFrameLayoutStatusImage
                    .findViewById(R.id.iv_image);
            // retweet weibo content
            holder.mLinearLayoutStatusRetweeted = (LinearLayout) convertView
                    .findViewById(R.id.include_status_retweeted);
            holder.mTextViewRetweetedContent = (TextView) holder.mLinearLayoutStatusRetweeted
                    .findViewById(R.id.tv_retweeted_content);
            // retweet weibo content image
            holder.mFrameLayoutRetweetedStatusImage = (FrameLayout) holder.mLinearLayoutStatusRetweeted
                    .findViewById(R.id.include_status_image);
            holder.mImageViewRetweetCustomImage = (GridView) holder.mFrameLayoutRetweetedStatusImage
                    .findViewById(R.id.custom_images);
            holder.mImageViewRetweetStatusImage = (ImageView) holder.mFrameLayoutRetweetedStatusImage
                    .findViewById(R.id.iv_image);
            // controller bar
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
            holder = (HomeViewHolder) convertView.getTag();
        }

        // bind data into the view
        final Status mStatus = getItem(position);
        User mUser = mStatus.user;
        mImageLoader.displayImage(mUser.profile_image_url, holder.mImageViewPortrait);
        holder.mTextViewUsername.setText(mUser.name);
        holder.mTextViewCaption.setText(TimeFormat.timeToString(mStatus.created_at) + " 来自 " + Html.fromHtml(mStatus.source));
        holder.mTextViewStatusContent.setText(mStatus.text);
        setImages(mStatus, holder.mFrameLayoutStatusImage, holder.mImageViewCustomImages, holder.mImageViewStatusImage);
        // retweeted weibo content
        Status retweetedStatus = mStatus.retweeted_status;
        if (retweetedStatus != null) {
            User retUser = retweetedStatus.user;
            holder.mLinearLayoutStatusRetweeted.setVisibility(View.VISIBLE);
            holder.mTextViewRetweetedContent.setText("@" + retUser.name + ":" + retweetedStatus.text);
            // show the retweeted weibo content image
            setImages(retweetedStatus, holder.mFrameLayoutRetweetedStatusImage, holder.mImageViewRetweetCustomImage, holder.mImageViewRetweetStatusImage);
        } else {
            holder.mLinearLayoutStatusRetweeted.setVisibility(View.GONE);
        }
        holder.mTextViewRetweet.setText(mStatus.reposts_count == 0 ?
                "转发" : mStatus.reposts_count + "");
        holder.mTextViewComment.setText(mStatus.comments_count == 0 ?
                "评论" : mStatus.comments_count + "");
        holder.mTextViewLike.setText(mStatus.attitudes_count == 0 ?
                "赞" : mStatus.attitudes_count + "");

        // set item click listener
        holder.mLinearLayoutMainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "页面正在跳转到详细页面!", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(mContext, WeiboDetailActivity.class);
                // @XXX Think about the best method and solve to pass value problem.
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("status", mStatus);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        holder.mLinearLayoutRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "您转发我了!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.mLinearLayoutComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, "您评论我了!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.mLinearLayoutLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "您赞赞我了!", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    /**
     * To show the Weibo content image in the homepage.
     *
     * @author BriceChou
     * @datetime 16-6-21 10:15
     */
    private void setImages(Status status, FrameLayout imgContainer,
                           GridView gridViewImg, ImageView singleImg) {
        ArrayList<String> picUrls = status.pic_urls;
        String picUrl = status.thumbnail_pic;
        if (picUrls != null && picUrls.size() > 1) {
            imgContainer.setVisibility(View.VISIBLE);
            gridViewImg.setVisibility(View.VISIBLE);
            singleImg.setVisibility(View.GONE);
            StatusGridImagesAdapter mStatusGridImagesAdapter = new StatusGridImagesAdapter(mContext, status);
            gridViewImg.setAdapter(mStatusGridImagesAdapter);
        } else if (picUrl != "") {
            imgContainer.setVisibility(View.VISIBLE);
            gridViewImg.setVisibility(View.GONE);
            singleImg.setVisibility(View.VISIBLE);
            mImageLoader.displayImage(picUrl, singleImg);
        } else {
            imgContainer.setVisibility(View.GONE);
        }
    }

    /**
     * To manage the Homepage layout resource.
     * Just create this class once time.
     *
     * @author BriceChou
     * @datetime 16-6-12 11:39
     */
    public static class HomeViewHolder {
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
        public GridView mImageViewCustomImages;
        public ImageView mImageViewStatusImage;
        // include_status_retweeted.xml -->> retweeted weibo content comment
        public LinearLayout mLinearLayoutStatusRetweeted;
        public TextView mTextViewRetweetedContent;
        public FrameLayout mFrameLayoutRetweetedStatusImage;
        public GridView mImageViewRetweetCustomImage;
        public ImageView mImageViewRetweetStatusImage;
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
