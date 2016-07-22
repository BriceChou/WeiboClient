package com.bricechou.weiboclient.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.bricechou.weiboclient.R;
import com.bricechou.weiboclient.adapter.MessageAdapter;
import com.bricechou.weiboclient.api.WeiboRequestListener;
import com.bricechou.weiboclient.config.Constants;
import com.bricechou.weiboclient.db.LoginUserToken;
import com.bricechou.weiboclient.utils.BaseFragment;
import com.bricechou.weiboclient.utils.TitleBuilder;
import com.sina.weibo.sdk.openapi.CommentsAPI;
import com.sina.weibo.sdk.openapi.models.CommentList;

public class MessageFragment extends BaseFragment {
    private static final String TAG = "weiboclient.fragment.MessageFragment";
    private View mView;
    private CommentsAPI mCommentsAPI;
    private CommentList mCommentList;
    private MessageAdapter mMessageAdapter;
    private ListView mListViewMessage;

    private void initView() {
        mView = View.inflate(mMainActivity, R.layout.frag_message, null);
        mListViewMessage = (ListView) mView.findViewById(R.id.lv_message);

        new TitleBuilder(mView)
                .setCenterText("消息")
                .setLeftText("发现群")
                .setRightImage(R.drawable.icon_newchat)
                .setLeftOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mMainActivity,
                                R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                    }
                })
                .setRightOnclickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mMainActivity,
                                R.string.toast_function_unfinished, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initMessage() {
//        mCommentsAPI = new CommentsAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.showAccessToken());
        mCommentsAPI = new CommentsAPI(mMainActivity, Constants.APP_KEY, LoginUserToken.getAccessToken(mMainActivity));
        mCommentsAPI.toME(0, 0, 10, 1, 0, 0, new WeiboRequestListener(mMainActivity) {
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
                if (!TextUtils.isEmpty(response)) {
                    if (response.startsWith("{\"comments\"")) {
                        mCommentList = CommentList.parse(response);
                        if (null != mCommentList.commentList) {
                            mMessageAdapter = new MessageAdapter(mMainActivity, mCommentList.commentList);
                            mListViewMessage.setAdapter(mMessageAdapter);
                        }
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initView();
        initMessage();
        return mView;
    }
}
