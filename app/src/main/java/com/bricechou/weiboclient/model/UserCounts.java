package com.bricechou.weiboclient.model;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Resolving the JSON type data about current all counts list.
 *
 * @author BriceChou
 * @datetime 16-6-16 14:49
 */

public class UserCounts {

    /**
     * Show the all current user counts list.
     *
     * @author BriceChou
     * @datetime 16-6-16 10:05
     */
    public String user_id;
    public String followers_count;
    public String friends_count;
    public String statuses_count;

    public static UserCounts parse(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return UserCounts.parse(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserCounts parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        UserCounts UserCounts = new UserCounts();
        UserCounts.user_id          = jsonObject.optString("id", "");
        UserCounts.followers_count  = jsonObject.optString("followers_count", "");
        UserCounts.friends_count    = jsonObject.optString("friends_count", "");
        UserCounts.statuses_count   = jsonObject.optString("statuses_count", "");
        return UserCounts;
    }
}