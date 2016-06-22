package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.utils.StringFormat;
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
        final HomeViewHolder holder;
        if (convertView == null) {
            holder = new HomeViewHolder();
            convertView = View.inflate(mContext, R.layout.item_status, null);
            holder.mLinearLayoutMainContent = (LinearLayout) convertView
                    .findViewById(R.id.ll_mainContent);

            holder.mImageViewPortrait = (ImageView) convertView
                    .findViewById(R.id.iv_portrait);
            holder.mRelativeLayoutContent = (RelativeLayout) convertView
                    .findViewById(R.id.rl_content);
            holder.mTextViewUsername = (TextView) convertView
                    .findViewById(R.id.tv_username);
            holder.mTextViewCaption = (TextView) convertView
                    .findViewById(R.id.tv_caption);

            holder.mTextViewStatusContent = (TextView) convertView
                    .findViewById(R.id.tv_item_content);

            holder.mFrameLayoutStatusImage = (FrameLayout) convertView
                    .findViewById(R.id.include_status_image);
            holder.mImageViewCustomImages = (GridView) holder.mFrameLayoutStatusImage
                    .findViewById(R.id.custom_images);
            holder.mImageViewStatusImage = (ImageView) holder.mFrameLayoutStatusImage
                    .findViewById(R.id.iv_image);


            holder.mLinearLayoutStatusRetweeted = (LinearLayout) convertView
                    .findViewById(R.id.include_status_retweeted);
            holder.mTextViewRetweetedContent = (TextView) holder.mLinearLayoutStatusRetweeted
                    .findViewById(R.id.tv_retweeted_content);
            holder.mFrameLayoutRetweetedStatusImage = (FrameLayout) holder.mLinearLayoutStatusRetweeted
                    .findViewById(R.id.include_status_image);

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
        Status status = getItem(position);

        User user = status.user;
        mImageLoader.displayImage(user.profile_image_url, holder.mImageViewPortrait);
        holder.mTextViewUsername.setText(user.name);
        holder.mTextViewCaption.setText(TimeFormat.timeToString(status.created_at) + " 来自 " + StringFormat.formatStatusSource(status.source));
        holder.mTextViewStatusContent.setText(status.text);

        setImages(status, holder.mFrameLayoutStatusImage, holder.mImageViewCustomImages, holder.mImageViewStatusImage);
        // retweeted weibo content
        Status retweeted_status = status.retweeted_status;

        if (retweeted_status != null) {
            User retUser = retweeted_status.user;
            holder.mLinearLayoutStatusRetweeted.setVisibility(View.VISIBLE);
            holder.mTextViewRetweetedContent.setText("@" + retUser.name + ":" + retweeted_status.text);
        } else {
            holder.mLinearLayoutStatusRetweeted.setVisibility(View.GONE);
        }
        holder.mTextViewRetweet.setText(status.reposts_count == 0 ?
                "转发" : status.reposts_count + "");
        holder.mTextViewComment.setText(status.comments_count == 0 ?
                "评论" : status.comments_count + "");
        holder.mTextViewLike.setText(status.attitudes_count == 0 ?
                "赞" : status.attitudes_count + "");
        return convertView;
    }

    /**
     * To show the Weibo content image in the homepage.
     *
     * @author BriceChou
     * @datetime 16-6-21 10:15
     * @TODO To solve the imageLoader can resolve the image URL and show image.
     */
    private void setImages(Status status, FrameLayout imgContainer,
                           GridView gv_images, ImageView iv_image) {
        ArrayList<String> pic_urls = status.pic_urls;
        String thumbnail_pic = status.thumbnail_pic;
        if (pic_urls != null && pic_urls.size() > 1) {
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.VISIBLE);
            iv_image.setVisibility(View.GONE);

            StatusGridImagesAdapter mStatusGridImagesAdapter = new StatusGridImagesAdapter(mContext, status);
            gv_images.setAdapter(mStatusGridImagesAdapter);
        } else if (thumbnail_pic != null) {
            imgContainer.setVisibility(View.VISIBLE);
            gv_images.setVisibility(View.GONE);
            iv_image.setVisibility(View.VISIBLE);

            mImageLoader.displayImage(thumbnail_pic, iv_image);
        } else {
            imgContainer.setVisibility(View.GONE);
        }
    }

    /**
     * To manage the Homepage layout resource.
     * Only create this class once time.
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
