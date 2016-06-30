package com.bricechou.weiboclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.StatusGridImagesAdapter;
import com.bricechou.weiboclient.utils.TimeFormat;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;

public class WeiboDetailActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "weiboclient.activity.WeiboDetailActivity";
    private Status mStatus;
    private ImageLoader mImageLoader;
    // tile bar view
    private ImageView mImageViewBack;
    private ImageView mImageViewMore;
    // weibo detail portrait view
    private ImageView mImageViewPortrait;
    private TextView mTextViewUsername;
    private TextView mTextViewCaption;
    // weibo detail content view
    private LinearLayout mLinearLayoutMainContent;
    private TextView mTextViewContent;
    private FrameLayout mFrameLayoutStatusImage;
    private ImageView mImageViewImageDetail;
    private GridView mGridViewImageDetail;
    // weibo detail status retweeted
    private TextView mTextViewContentRetweeted;
    private LinearLayout mLinearLayoutContentRetweeted;
    private FrameLayout mFrameLayoutStatusImageRetweeted;
    private ImageView mImageViewImageDetailRetweeted;
    private GridView mGridViewImageDetailRetweeted;
    // weibo detail bottom
    private RadioButton mRadioButtonComment;
    private RadioButton mRadioButtonRetweet;
    private RadioButton mRadioButtonLike;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);
        mStatus = (Status) this.getIntent().getSerializableExtra("status");
        mImageLoader = ImageLoader.getInstance();
        initView();
        initViewData();
    }

    private void initView() {
        // Title bar view
        mImageViewBack = (ImageView) findViewById(R.id.weibo_detail_back);
        mImageViewMore = (ImageView) findViewById(R.id.weibo_detail_more);
        // weibo detail portrait view
        mImageViewPortrait = (ImageView) findViewById(R.id.iv_portrait);
        mTextViewUsername = (TextView) findViewById(R.id.tv_username);
        mTextViewCaption = (TextView) findViewById(R.id.tv_caption);
        // weibo detail content view
        mLinearLayoutMainContent = (LinearLayout) findViewById(R.id.ll_mainContent);
        mTextViewContent = (TextView) findViewById(R.id.tv_item_content_detail);
        mFrameLayoutStatusImage = (FrameLayout) mLinearLayoutMainContent.findViewById(R.id.include_status_image_detail);
        mGridViewImageDetail = (GridView) mFrameLayoutStatusImage.findViewById(R.id.custom_images);
        mImageViewImageDetail = (ImageView) mFrameLayoutStatusImage.findViewById(R.id.iv_image);
        // weibo detail status retweeted
        mLinearLayoutContentRetweeted = (LinearLayout) findViewById(R.id.include_status_retweeted_detail);
        mTextViewContentRetweeted = (TextView) mLinearLayoutContentRetweeted.findViewById(R.id.tv_retweeted_content);
        mFrameLayoutStatusImageRetweeted = (FrameLayout) mLinearLayoutContentRetweeted.findViewById(R.id.include_status_image);
        mGridViewImageDetailRetweeted = (GridView) mFrameLayoutStatusImageRetweeted.findViewById(R.id.custom_images);
        mImageViewImageDetailRetweeted = (ImageView) mFrameLayoutStatusImageRetweeted.findViewById(R.id.iv_image);
        // weibo detail bottom button
        mRadioButtonLike = (RadioButton) findViewById(R.id.rb_likes_detail);
        mRadioButtonRetweet = (RadioButton) findViewById(R.id.rb_retweets_detail);
        mRadioButtonComment = (RadioButton) findViewById(R.id.rb_comments_detail);

        mImageViewBack.setOnClickListener(this);
        mImageViewMore.setOnClickListener(this);
        mRadioButtonLike.setOnClickListener(this);
        mRadioButtonRetweet.setOnClickListener(this);
        mRadioButtonComment.setOnClickListener(this);
    }

    /**
     * @TODO Add comments / likes / retweets content list in the detail page bottom.
     * @author BriceChou
     * @datetime 16-6-28 15:02
     */
    private void initViewData() {
        mImageLoader.displayImage(mStatus.user.profile_image_url, mImageViewPortrait);
        mTextViewUsername.setText(mStatus.user.name);
        mTextViewCaption.setText(TimeFormat.timeToString(mStatus.created_at) +
                "  来自 " + Html.fromHtml(mStatus.source));
        setImages(mStatus, mFrameLayoutStatusImage, mGridViewImageDetail, mImageViewImageDetail);
        if (TextUtils.isEmpty(mStatus.text)) {
            mTextViewContent.setVisibility(View.GONE);
        } else {
            mTextViewContent.setVisibility(View.VISIBLE);
            mTextViewContent.setText(mStatus.text);
        }
        Status retweetedStatus = mStatus.retweeted_status;
        if (retweetedStatus != null) {
            User retUser = retweetedStatus.user;
            mLinearLayoutContentRetweeted.setVisibility(View.VISIBLE);
            mTextViewContentRetweeted.setText("@" + retUser.name + ":" + retweetedStatus.text);
            setImages(retweetedStatus, mFrameLayoutStatusImageRetweeted,
                    mGridViewImageDetailRetweeted, mImageViewImageDetailRetweeted);
        } else {
            mLinearLayoutContentRetweeted.setVisibility(View.GONE);
        }
        mRadioButtonRetweet.setText("转发 " + mStatus.reposts_count);
        mRadioButtonComment.setText("评论 " + mStatus.comments_count);
        mRadioButtonLike.setText("赞 " + mStatus.attitudes_count);
    }

    private void setImages(Status status, ViewGroup imgContainer, GridView gridViewImg, final ImageView singleImg) {
        if (status == null) {
            return;
        }
        ArrayList<String> picUrls = status.pic_urls;
        String picUrl = status.bmiddle_pic;
        if (picUrls != null && picUrls.size() == 1) {
            imgContainer.setVisibility(View.VISIBLE);
            gridViewImg.setVisibility(View.GONE);
            singleImg.setVisibility(View.VISIBLE);
            mImageLoader.displayImage(picUrl, singleImg);
        } else if (picUrls != null && picUrls.size() > 1) {
            imgContainer.setVisibility(View.VISIBLE);
            gridViewImg.setVisibility(View.VISIBLE);
            singleImg.setVisibility(View.GONE);
            StatusGridImagesAdapter mStatusGridImagesAdapter = new StatusGridImagesAdapter(this, status);
            gridViewImg.setAdapter(mStatusGridImagesAdapter);
        } else {
            imgContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weibo_detail_back:
                // Exit this activity.
                finish();
                break;
            case R.id.weibo_detail_more:
                Toast.makeText(WeiboDetailActivity.this,
                        R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }
}