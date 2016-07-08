package com.bricechou.weiboclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.CommentsAdapter;
import com.bricechou.weiboclient.adapter.StatusGridImagesAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.model.Counts;
import com.bricechou.weiboclient.utils.TimeFormat;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.CommentsAPI;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.CommentList;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;

public class WeiboDetailActivity extends Activity {
    private static final String TAG = "weiboclient.activity.WeiboDetailActivity";
    private ImageLoader mImageLoader;
    // tile bar view
    private RelativeLayout mTitlebar;
    //item_status.xml include_status_retweeted.xml
    private View mItemStatus;
    private View mRetweetedStatus;
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
    // shadow_tab
    private View mShadowDetailTab;
    private RadioButton mShadowButtonRetweet;
    private RadioButton mShadowButtonLike;
    private RadioButton mShadowButtonComment;
    // listView headerView
    private View mDetailTab;
    private RadioButton mRadioButtonComment;
    private RadioButton mRadioButtonRetweet;
    private RadioButton mRadioButtonLike;
    //weibo detail comments listview
    private PullToRefreshListView mPullToRefreshListView;
    private StatusesAPI mStatusesAPI;
    private Counts mCounts;
    private CommentsAPI mCommentsAPI;
    private CommentList mCommentList;
    private CommentsAdapter mCommentsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_detail);
        Status status = (Status) this.getIntent().getSerializableExtra("status");
        mImageLoader = ImageLoader.getInstance();
        initView();
        // Show the old data
        initViewData(status, mCounts);
    }

    private void initView() {
        initTitleBar();
        initDetailHead();
        initTab();
        initListView();
    }

    private void initTitleBar() {
        mTitlebar = (RelativeLayout) findViewById(R.id.rl_titlebar);
        mTitlebar.setBackgroundResource(R.color.bg_gray);
        new TitleBuilder(this)
                .setCenterText("微博正文")
                .setLeftImage(R.drawable.navigationbar_back)
                .setRightImage(R.drawable.navigationbar_more)
                .setLeftOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Exit this activity.
                        finish();
                    }
                })
                .setRightOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(WeiboDetailActivity.this, R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initDetailHead() {
        mItemStatus = View.inflate(this, R.layout.item_status, null);
        mItemStatus.setBackgroundResource(R.color.white);
        //hide the id.ll_bottom_control in item_status.xml
        mItemStatus.findViewById(R.id.ll_bottom_control).setVisibility(View.GONE);
        // weibo detail portrait view
        mImageViewPortrait = (ImageView) mItemStatus.findViewById(R.id.iv_portrait);
        mTextViewUsername = (TextView) mItemStatus.findViewById(R.id.tv_username);
        mTextViewCaption = (TextView) mItemStatus.findViewById(R.id.tv_caption);
        // weibo detail content view
        mLinearLayoutMainContent = (LinearLayout) mItemStatus.findViewById(R.id.ll_mainContent);
        mTextViewContent = (TextView) mItemStatus.findViewById(R.id.tv_item_content);
        mFrameLayoutStatusImage = (FrameLayout) mItemStatus.findViewById(R.id.include_status_image);
        mGridViewImageDetail = (GridView) mItemStatus.findViewById(R.id.custom_images);
        mImageViewImageDetail = (ImageView) mItemStatus.findViewById(R.id.iv_image);
        // weibo detail status retweeted
        mRetweetedStatus = mItemStatus.findViewById(R.id.include_status_retweeted);
        mLinearLayoutContentRetweeted = (LinearLayout) mItemStatus.findViewById(R.id.include_status_retweeted);
        mTextViewContentRetweeted = (TextView) mItemStatus.findViewById(R.id.tv_retweeted_content);
        mFrameLayoutStatusImageRetweeted = (FrameLayout) mRetweetedStatus.findViewById(R.id.include_status_image);
        mGridViewImageDetailRetweeted = (GridView) mFrameLayoutStatusImageRetweeted.findViewById(R.id.custom_images);
        mImageViewImageDetailRetweeted = (ImageView) mFrameLayoutStatusImageRetweeted.findViewById(R.id.iv_image);

    }

    private void initTab() {
        // weibo detail bottom button
        // shadow
        mShadowDetailTab = findViewById(R.id.status_detail_tab);
        mShadowButtonRetweet = (RadioButton) mShadowDetailTab.findViewById(R.id.rb_retweets_detail);
        mShadowButtonComment = (RadioButton) mShadowDetailTab.findViewById(R.id.rb_comments_detail);
        mShadowButtonLike = (RadioButton) mShadowDetailTab.findViewById(R.id.rb_likes_detail);
        // header
        mDetailTab = View.inflate(this, R.layout.include_detail_bottom, null);
        mRadioButtonRetweet = (RadioButton) mDetailTab.findViewById(R.id.rb_retweets_detail);
        mRadioButtonComment = (RadioButton) mDetailTab.findViewById(R.id.rb_comments_detail);
        mRadioButtonLike = (RadioButton) mDetailTab.findViewById(R.id.rb_likes_detail);
    }

    private void initListView() {
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.weibo_detail_list);
        final ListView lv = mPullToRefreshListView.getRefreshableView();
        lv.addHeaderView(mItemStatus);
        lv.addHeaderView(mDetailTab);
        mPullToRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // 0-pullHead 1-detailHead 2-tab
                mShadowDetailTab.setVisibility(firstVisibleItem >= 2 ?
                        View.VISIBLE : View.GONE);
            }
        });
    }

    /**
     * @TODO Add comments / likes / retweets content list in the detail page bottom.
     * @author BriceChou
     * @datetime 16-6-28 15:02
     */
    private void initViewData(Status status, Counts counts) {
        refreshStatusCounts(status);
        mImageLoader.displayImage(status.user.profile_image_url, mImageViewPortrait);
        mTextViewUsername.setText(status.user.name);
        mTextViewCaption.setText(TimeFormat.timeToString(status.created_at) +
                "  来自 " + Html.fromHtml(status.source));
        setImages(status, mFrameLayoutStatusImage, mGridViewImageDetail, mImageViewImageDetail);
        if (TextUtils.isEmpty(status.text)) {
            mTextViewContent.setVisibility(View.GONE);
        } else {
            mTextViewContent.setVisibility(View.VISIBLE);
            mTextViewContent.setText(status.text);
        }
        Status retweetedStatus = status.retweeted_status;
        if (retweetedStatus != null) {
            User retUser = retweetedStatus.user;
            mLinearLayoutContentRetweeted.setVisibility(View.VISIBLE);
            mTextViewContentRetweeted.setText("@" + retUser.name + ":" + retweetedStatus.text);
            setImages(retweetedStatus, mFrameLayoutStatusImageRetweeted,
                    mGridViewImageDetailRetweeted, mImageViewImageDetailRetweeted);
        } else {
            mLinearLayoutContentRetweeted.setVisibility(View.GONE);
        }
    }

    private void initCommentsData(long id) {
        mCommentsAPI = new CommentsAPI(this, Constants.APP_KEY, LoginUserToken.showAccessToken());
        mCommentsAPI.show(id, 0, 0, 20, 1, 0, new WeiboRequestListener(this) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"comments\"")) {
                        mCommentList = mCommentList.parse(response);
                        if (null != mCommentList) {
                            mCommentsAdapter = new CommentsAdapter(WeiboDetailActivity.this, mCommentList.commentList);
                            mPullToRefreshListView.setAdapter(mCommentsAdapter);
                        }
                    }
                }
            }
        });
    }

    private void refreshStatusCounts(final Status status) {
        initCommentsData(Long.parseLong(status.id));
        mStatusesAPI = new StatusesAPI(this, Constants.APP_KEY, LoginUserToken.showAccessToken());
        String[] ids = new String[]{status.id};
        mStatusesAPI.count(ids, new WeiboRequestListener(this) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (!TextUtils.isEmpty(response)) {
                    mCounts = Counts.parse(response);
                    if (null != mCounts) {
                        setCountsData();
                    }
                }
            }
        });
    }

    private void setCountsData() {
        //shadow
        mShadowButtonRetweet.setText("转发 " + mCounts.reposts);
        mShadowButtonComment.setText("评论 " + mCounts.comments);
        mShadowButtonLike.setText("赞 " + mCounts.attitudes);
        //head
        mRadioButtonRetweet.setText("转发 " + mCounts.reposts);
        mRadioButtonComment.setText("评论 " + mCounts.comments);
        mRadioButtonLike.setText("赞 " + mCounts.attitudes);
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
}
