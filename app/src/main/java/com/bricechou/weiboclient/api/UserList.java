package com.bricechou.weiboclient.api;


import android.text.TextUtils;

import com.sina.weibo.sdk.openapi.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Resolving the JSON type data about friendship list.
 * To show the all current user friendship list.
 *
 * @author BriceChou
 * @datetime 16-6-16 9:49
 */

public class UserList {
    public int total_number;
    public String next_cursor;
    public String previous_cursor;
    public ArrayList<User> userList;

    public static UserList parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        UserList usersList = new UserList();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            usersList.previous_cursor = jsonObject.optString("previous_cursor", "0");
            usersList.next_cursor = jsonObject.optString("next_cursor", "0");
            usersList.total_number = jsonObject.optInt("total_number", 0);
            JSONArray jsonArray = jsonObject.optJSONArray("users");
            if (jsonArray != null && jsonArray.length() > 0) {
                int length = jsonArray.length();
                usersList.userList = new ArrayList<User>(length);
                for (int ix = 0; ix < length; ix++) {
                    usersList.userList.add(User.parse(jsonArray.getJSONObject(ix)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usersList;
    }
}