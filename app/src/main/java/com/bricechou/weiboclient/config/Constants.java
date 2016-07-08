package com.bricechou.weiboclient.config;

/**
 * To save the Weibo configuration constant parameters.
 * <p/>
 * Created by BriceChou on 16-5-30.
 */
public class Constants {
    public static final String APP_KEY = "2006309463";
    // When user cancel to login weibo,this URL will be called.
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    // Show the weibo content counts number.
    public static final int SHOW_STATUS_COUNTS = 10;
    /**
     * @param all                            To request all SCOPE permission.
     * @param email                          user email permission
     * @param direct_messages_write          post messages
     * @param direct_messages_read           receive messages
     * @param invitation_write               post invitation request
     * @param friendships_groups_read        load the friendships group
     * @param friendships_groups_write       write the friendships group
     * @param statuses_to_me_read            read about me weibo content
     * @param follow_app_official_microblog  the app official micro blog
     */
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}
