package com.bricechou.weiboclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bricechou.weiboclient.activity.MainActivity;
import com.bricechou.weiboclient.model.WeiboContent;
import com.bricechou.weiboclient.utils.SQLiteUtil;

/**
 * save the Weibo content into the SQLiteDatabase
 *
 * @author BriceChou
 * @datetime 16-6-6 16:29
 * @TODO make user read weibo content offline
 */
public class WeiboDbHandler extends SQLiteOpenHelper {

    public WeiboDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table t2m_weibo_content(" +
                "id varchar(100)," +
                "mid varchar(100)" +
                "created_at varchar(200)," +
                "pass varchar(200), " +
                ")");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
