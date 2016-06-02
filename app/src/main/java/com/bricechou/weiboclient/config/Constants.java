package com.bricechou.weiboclient.config;

/**
 * 用于配置微博的基础参数, 使得开发者能够通过微博官方的授权, 填写app key/app secret
 *
 * Created by BriceChou on 16-5-30.
 */
public class Constants {

    /**
     * @TODO 后期考虑引入MD5将这个地址进行加密算法
     * @author BriceChou
     */
    public static final String APP_KEY = "2006309463";

    public static final String APP_SECRET = "94f3b81802a3999b25bf57720c32d98e";
    // 通过 code 获取 Token 的 URL
    public static final String OAUTH2_ACCESS_TOKEN_URL = "https://open.weibo.cn/oauth2/access_token";
    // 微博默认取消后的回调页,必须填写
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    /**
     * all  请求下列所有scope权限
     * email    用户的联系邮箱，接口文档
     * direct_messages_write    私信发送接口，接口文档
     * direct_messages_read     私信读取接口，接口文档
     * invitation_write     邀请发送接口，接口文档
     * friendships_groups_read  好友分组读取接口组，接口文档
     * friendships_groups_write     好友分组写入接口组，接口文档
     * statuses_to_me_read  定向微博读取接口组，接口文档
     * follow_app_official_microblog
     * 关注应用官方微博，该参数不对应具体接口，只需在应用控制台填写官方帐号即可。
     * 填写的路径：我的应用-选择自己的应用-应用信息-基本信息-官方运营账号（默认值是应用开发者帐号）
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}