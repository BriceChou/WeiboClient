package com.bricechou.weiboclient.model;

import com.sina.weibo.sdk.openapi.models.Status;

/**
 * This is a Model for current login user.
 *
 * @author BriceChou
 * @datetime 16-6-17 11:31
 * @TODO To record user related information.
 */
public class LoginUser {
    private final static String TAG = "LoginUser";
    public String name;
    public String screenName;
    public String description;
    public String location;
    public String profileImageUrl;
    public String profileUrl;
    public String domain;
    public String weihao;
    public String gender;
    public int statusesCount;
    public int followersCount;
    public int friendsCount;
    public int favouritesCount;
    public String createdAt;
    public Status status;

    public LoginUser() {
    }
}
