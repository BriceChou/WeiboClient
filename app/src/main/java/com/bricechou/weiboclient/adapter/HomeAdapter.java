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
    private static final String TAG = "weiboclient.adapter.HomeAdapter";
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
            holder.sLinearLayoutMainContent = (LinearLayout) convertView
                    .findViewById(R.id.ll_mainContent);
            // weibo avatar
            holder.sImageViewPortrait = (ImageView) convertView
                    .findViewById(R.id.iv_portrait);
            holder.sRelativeLayoutContent = (RelativeLayout) convertView
                    .findViewById(R.id.rl_content);
            holder.sTextViewUsername = (TextView) convertView
                    .findViewById(R.id.tv_username);
            holder.sTextViewCaption = (TextView) convertView
                    .findViewById(R.id.tv_caption);
            // weibo content
            holder.sTextViewStatusContent = (TextView) convertView
                    .findViewById(R.id.tv_item_content);
            // weibo content image
            holder.sFrameLayoutStatusImage = (FrameLayout) convertView
                    .findViewById(R.id.include_status_image);
            holder.sImageViewCustomImages = (GridView) holder.sFrameLayoutStatusImage
                    .findViewById(R.id.custom_images);
            holder.sImageViewStatusImage = (ImageView) holder.sFrameLayoutStatusImage
                    .findViewById(R.id.iv_image);
            // retweet weibo content
            holder.sLinearLayoutStatusRetweeted = (LinearLayout) convertView
                    .findViewById(R.id.include_status_retweeted);
            holder.sTextViewRetweetedContent = (TextView) holder.sLinearLayoutStatusRetweeted
                    .findViewById(R.id.tv_retweeted_content);
            // retweet weibo content image
            holder.sFrameLayoutRetweetedStatusImage = (FrameLayout) holder.sLinearLayoutStatusRetweeted
                    .findViewById(R.id.include_status_image);
            holder.sImageViewRetweetCustomImage = (GridView) holder.sFrameLayoutRetweetedStatusImage
                    .findViewById(R.id.custom_images);
            holder.sImageViewRetweetStatusImage = (ImageView) holder.sFrameLayoutRetweetedStatusImage
                    .findViewById(R.id.iv_image);
            // controller bar
            holder.sLinearLayoutRetweet = (LinearLayout) convertView
                    .findViewById(R.id.ll_retweet);
            holder.sImageViewRetweet = (ImageView) convertView
                    .findViewById(R.id.iv_retweet);
            holder.sTextViewRetweet = (TextView) convertView
                    .findViewById(R.id.tv_retweet);
            holder.sLinearLayoutComment = (LinearLayout) convertView
                    .findViewById(R.id.ll_comment);
            holder.sImageViewComment = (ImageView) convertView
                    .findViewById(R.id.iv_comment);
            holder.sTextViewComment = (TextView) convertView
                    .findViewById(R.id.tv_comment);
            holder.sLinearLayoutLike = (LinearLayout) convertView
                    .findViewById(R.id.ll_like);
            holder.sImageViewLike = (ImageView) convertView
                    .findViewById(R.id.iv_like);
            holder.sTextViewLike = (TextView) convertView
                    .findViewById(R.id.tv_like);
            convertView.setTag(holder);
        } else {
            holder = (HomeViewHolder) convertView.getTag();
        }

        // bind data into the view
        final Status status = getItem(position);
        User user = status.user;
        mImageLoader.displayImage(user.profile_image_url, holder.sImageViewPortrait);
        holder.sTextViewUsername.setText(user.name);
        holder.sTextViewCaption.setText(TimeFormat.timeToString(status.created_at) +
                " 来自 " + Html.fromHtml(status.source));
        holder.sTextViewStatusContent.setText(status.text);
        setImages(status, holder.sFrameLayoutStatusImage, holder.sImageViewCustomImages,
                holder.sImageViewStatusImage);
        // retweeted weibo content
        Status retweetedStatus = status.retweeted_status;
        if (retweetedStatus != null) {
            User retUser = retweetedStatus.user;
            holder.sLinearLayoutStatusRetweeted.setVisibility(View.VISIBLE);
            holder.sTextViewRetweetedContent.setText("@" + retUser.name + ":" + retweetedStatus.text);
            // show the retweeted weibo content image
            setImages(retweetedStatus, holder.sFrameLayoutRetweetedStatusImage, holder.sImageViewRetweetCustomImage,
                    holder.sImageViewRetweetStatusImage);
        } else {
            holder.sLinearLayoutStatusRetweeted.setVisibility(View.GONE);
        }
        holder.sTextViewRetweet.setText(status.reposts_count == 0 ?
                "转发" : status.reposts_count + "");
        holder.sTextViewComment.setText(status.comments_count == 0 ?
                "评论" : status.comments_count + "");
        holder.sTextViewLike.setText(status.attitudes_count == 0 ?
                "赞" : status.attitudes_count + "");

        // set item click listener
        holder.sLinearLayoutMainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WeiboDetailActivity.class);
                // @XXX Think about the best method and solve to pass value problem.
                Bundle bundle = new Bundle();
                bundle.putSerializable("status", status);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        holder.sLinearLayoutRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "您转发我了!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.sLinearLayoutComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.comments_count > 0) {
                    Intent intent = new Intent(mContext, WeiboDetailActivity.class);
                    intent.putExtra("status", status);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "您评论我了!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.sLinearLayoutLike.setOnClickListener(new View.OnClickListener() {
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
            StatusGridImagesAdapter statusGridImagesAdapter = new StatusGridImagesAdapter(mContext, status);
            gridViewImg.setAdapter(statusGridImagesAdapter);
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
        public LinearLayout sLinearLayoutMainContent;
        // include_portrait xml file content
        public ImageView sImageViewPortrait;
        public RelativeLayout sRelativeLayoutContent;
        // user name
        public TextView sTextViewUsername;
        // user identification form what phone client
        public TextView sTextViewCaption;
        // item_status.xml -->> weibo content
        public TextView sTextViewStatusContent;
        // include_status_image.xml -->> weibo content image
        public FrameLayout sFrameLayoutStatusImage;
        public GridView sImageViewCustomImages;
        public ImageView sImageViewStatusImage;
        // include_status_retweeted.xml -->> retweeted weibo content comment
        public LinearLayout sLinearLayoutStatusRetweeted;
        public TextView sTextViewRetweetedContent;
        public FrameLayout sFrameLayoutRetweetedStatusImage;
        public GridView sImageViewRetweetCustomImage;
        public ImageView sImageViewRetweetStatusImage;
        // include_status_controlbar.xml -->> retweet,comment,like
        public LinearLayout sLinearLayoutRetweet;
        public ImageView sImageViewRetweet;
        public TextView sTextViewRetweet;
        public LinearLayout sLinearLayoutComment;
        public ImageView sImageViewComment;
        public TextView sTextViewComment;
        public LinearLayout sLinearLayoutLike;
        public ImageView sImageViewLike;
        public TextView sTextViewLike;
    }
}