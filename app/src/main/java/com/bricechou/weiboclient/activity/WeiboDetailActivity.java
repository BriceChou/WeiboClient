package com.bricechou.weiboclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.StatusGridImagesAdapter;
import com.bricechou.weiboclient.utils.TimeFormat;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;

public class WeiboDetailActivity extends Activity implements View.OnClickListener {
    private final static String TAG = "weiboclient.activity.WeiboDetailActivity";
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
    private TextView mTextViewContent;
    private ImageView mImageViewDetailImage;
    private GridView mImageViewDetailGridImage;
    // weibo detail status retweeted
    private TextView mTextViewContentRetweeted;
    private ImageView mImageViewDetailImageRetweeted;
    private GridView mImageViewDetailGridImageRetweeted;
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
        mTextViewContent = (TextView) findViewById(R.id.tv_item_content_detail);
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

    private void initViewData() {
        mImageLoader.displayImage(mStatus.user.profile_image_url, mImageViewPortrait);
        mTextViewUsername.setText(mStatus.user.name);
        mTextViewCaption.setText(TimeFormat.timeToString(mStatus.created_at) +
                "  来自 " + Html.fromHtml(mStatus.source));
    /*    setImages(mStatus, include_status_image, gv_images, iv_image);*/
        if (TextUtils.isEmpty(mStatus.text)) {
            mTextViewContent.setVisibility(View.GONE);
        } else {
            mTextViewContent.setVisibility(View.VISIBLE);
            mTextViewContent.setText(mStatus.text);
        }

     /*     Status retweetedStatus = mStatus.retweeted_status;
        if (retweetedStatus != null) {
            include_retweeted_status.setVisibility(View.VISIBLE);
            String retweetContent = "@" + retweetedStatus.getUser().getName()
                    + ":" + retweetedStatus.getText();
            SpannableString weiboContent = StringUtils.getWeiboContent(
                    this, tv_retweeted_content, retweetContent);
            tv_retweeted_content.setText(weiboContent);
            setImages(retweetedStatus, fl_retweeted_imageview,
                    gv_retweeted_images, iv_retweeted_image);
        } else {
            include_retweeted_status.setVisibility(View.GONE);
        }*/

        mRadioButtonRetweet.setText("转发 " + mStatus.reposts_count);
        mRadioButtonComment.setText("评论 " + mStatus.comments_count);
        mRadioButtonLike.setText("赞 " + mStatus.attitudes_count);

        // bottom_control - 底部互动栏,包括转发/评论/点赞
       /* tv_share_bottom.setText(status.getReposts_count() == 0 ?
                "转发" : status.getReposts_count() + "");
        tv_comment_bottom.setText(status.getComments_count() == 0 ?
                "评论" : status.getComments_count() + "");
        tv_like_bottom.setText(status.getAttitudes_count() == 0 ?
                "赞" : status.getAttitudes_count() + "");*/

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