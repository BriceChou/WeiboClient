package com.bricechou.weiboclient.model;

import android.text.TextUtils;

import org.json.JSONArray;

import java.io.Serializable;

public class Counts implements Serializable {
    public Integer comments;
    public Integer reposts;
    public Integer attitudes;

    public static Counts parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        Counts counts = new Counts();
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            counts.comments = jsonArray.getJSONObject(0).optInt("comments", 0);
            counts.reposts = jsonArray.getJSONObject(0).optInt("reposts", 0);
            counts.attitudes = jsonArray.getJSONObject(0).optInt("attitudes", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counts;
    }
}
