package com.bricechou.weiboclient.utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bricechou.weiboclient.R;

/**
 * Created by user on 6/6/16.
 */
public class TitleBuilder {
    private View mView; //defined RelativeLayout view;
    private TextView mTextViewCenter; //the titlebar center text;
    private TextView mTextViewLeft; //the titlebar left text;
    private ImageView mImageViewLeft; //the titlebar left icon;
    private TextView mTextViewRight; //the titlebar right text;
    private ImageView mImageViewRight; //the titlebar right icon;
    private EditText mEditTextSearch;

    //used in Fragment
    public TitleBuilder(View context) {
        mView = context.findViewById(R.id.rl_titlebar);
        mTextViewCenter = (TextView) mView.findViewById(R.id.titlebar_tv_center);
        mTextViewLeft = (TextView) mView.findViewById(R.id.titlebar_tv_left);
        mImageViewLeft = (ImageView) mView.findViewById(R.id.titlebar_iv_left);
        mTextViewRight = (TextView) mView.findViewById(R.id.titlebar_tv_right);
        mImageViewRight = (ImageView) mView.findViewById(R.id.titlebar_iv_right);
        mEditTextSearch = (EditText) mView.findViewById(R.id.titlebar_et_search);
    }

    //used in Activity
    public TitleBuilder(Activity context) {
        mView = context.findViewById(R.id.rl_titlebar);
        mTextViewCenter = (TextView) mView.findViewById(R.id.titlebar_tv_center);
        mTextViewLeft = (TextView) mView.findViewById(R.id.titlebar_tv_left);
        mImageViewLeft = (ImageView) mView.findViewById(R.id.titlebar_iv_left);
        mTextViewRight = (TextView) mView.findViewById(R.id.titlebar_tv_right);
        mImageViewRight = (ImageView) mView.findViewById(R.id.titlebar_iv_right);
        mEditTextSearch = (EditText) mView.findViewById(R.id.titlebar_et_search);

    }

    //setting center titlebar
    public TitleBuilder setCenterText(String text) {
        mTextViewCenter.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTextViewCenter.setText(text);
        return this;
    }

    //setting left titlebar
    public TitleBuilder setLeftText(String text) {
        mTextViewLeft.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTextViewLeft.setText(text);
        return this;
    }

    public TitleBuilder setLeftImage(int resId) {
        mImageViewLeft.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mImageViewLeft.setImageResource(resId);
        return this;
    }

    public TitleBuilder setLeftOnclickListener(View.OnClickListener listener) {
        if (mTextViewLeft.getVisibility() == View.VISIBLE) {
            mTextViewLeft.setOnClickListener(listener);
        } else if (mImageViewLeft.getVisibility() == View.VISIBLE) {
            mImageViewLeft.setOnClickListener(listener);
        }
        return this;
    }

    //setting right titlebar
    public TitleBuilder setRightText(String text) {
        mTextViewRight.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        mTextViewRight.setText(text);
        return this;
    }

    public TitleBuilder setRightImage(int resId) {
        mImageViewRight.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mImageViewRight.setImageResource(resId);
        return this;
    }

    public TitleBuilder setRightOnclickListener(View.OnClickListener listener) {
        if (mTextViewRight.getVisibility() == View.VISIBLE) {
            mTextViewRight.setOnClickListener(listener);
        } else if (mImageViewRight.getVisibility() == View.VISIBLE) {
            mImageViewRight.setOnClickListener(listener);
        }
        return this;
    }

    public TitleBuilder setSearchText(int resId) {
        mEditTextSearch.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        return this;
    }

}
