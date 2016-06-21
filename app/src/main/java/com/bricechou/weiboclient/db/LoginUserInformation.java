package com.bricechou.weiboclient.db;

import com.bricechou.weiboclient.model.LoginUser;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * To save the current login user personal information
 * into the local database.
 *
 * @author BriceChou
 * @datetime 16-6-17 11:15
 * @FIXME: can't save the LoginUser instance.
 */
public class LoginUserInformation {
    public LoginUser loginUser;

    public LoginUserInformation() {
    }

    public void setLoginUser(LoginUserInformation loginUserInformation,User mUser) {
        loginUserInformation.loginUser.createdAt = mUser.created_at;
        loginUserInformation.loginUser.description = mUser.description;
        loginUserInformation.loginUser.domain = mUser.domain;
        loginUserInformation.loginUser.favouritesCount = mUser.favourites_count;
        loginUserInformation.loginUser.followersCount = mUser.followers_count;
        loginUserInformation.loginUser.friendsCount = mUser.friends_count;
        if (mUser.gender.equals("n")) {
            loginUserInformation.loginUser.gender = "火星人";
        } else {
            loginUserInformation.loginUser.gender = mUser.gender == "m" ? "男" : "女";
        }
        loginUserInformation.loginUser.location = mUser.location;
        loginUserInformation.loginUser.name = mUser.name;
        loginUserInformation.loginUser.profileImageUrl = mUser.profile_image_url;
        loginUserInformation.loginUser.profileUrl = mUser.profile_url;
        loginUserInformation.loginUser.screenName = mUser.screen_name;
        loginUserInformation.loginUser.status = mUser.status;
        loginUserInformation.loginUser.statusesCount = mUser.statuses_count;
        loginUserInformation.loginUser.weihao = mUser.weihao;
    }
}
