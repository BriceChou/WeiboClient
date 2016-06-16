package com.bricechou.weiboclient.model;


import org.json.JSONArray;
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
        char[] jsonStringChar = jsonString.substring(0, 1).toCharArray();
        char firstChar = jsonStringChar[0];
        try {
            if (firstChar == '{') {
                JSONObject jsonObject = new JSONObject(jsonString);
                return UserCounts.parse(jsonObject);
            } else if (firstChar == '[') {
                JSONArray jsonArray = new JSONArray(jsonString);
                return UserCounts.parse(jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UserCounts parse(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }

        UserCounts userCounts = new UserCounts();
        userCounts.user_id = jsonObject.optString("id", "");
        userCounts.followers_count = jsonObject.optString("followers_count", "");
        userCounts.friends_count = jsonObject.optString("friends_count", "");
        userCounts.statuses_count = jsonObject.optString("statuses_count", "");
        return userCounts;
    }

    public static UserCounts parse(JSONArray jsonArray) {
        if (null == jsonArray) {
            return null;
        }
        UserCounts userCounts = new UserCounts();
        try {
            userCounts = UserCounts.parse(jsonArray.getJSONObject(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userCounts;
    }
}