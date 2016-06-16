package com.bricechou.weiboclient.db;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 6/15/16.
 */
public class UidList {

    public Long[] uidList;

    public int total_number;

    public static UidList parse(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        UidList uid = new UidList();

        try {
            JSONObject jsonObject  = new JSONObject(jsonString);
            uid.total_number = jsonObject.optInt("total_number");
            JSONArray jsonArray = jsonObject.optJSONArray("ids");
            uid.uidList = new Long[jsonArray.length()];
            if (jsonArray != null && jsonArray.length() > 0) {
                int length = jsonArray.length();
                for (int ix = 0; ix < length; ix++) {
                    Log.d("UidList", "parse: "+jsonArray.get(ix));
                    uid.uidList[ix] = Long.parseLong(jsonArray.get(ix).toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return uid;
    }
}
