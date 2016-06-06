package com.bricechou.weiboclient.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bricechou.weiboclient.model.WeiboContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Just a example and copy from Internet
 *
 * @author BriceChou
 * @datetime 16-6-6 17:16
 * @TODO implements the WeiboDbHandler operation
 */

public class WeiboDbImpl {
        private WeiboDbHandler mWeiboDbHandler;

        public WeiboDbImpl(Context context) {
            this.mWeiboDbHandler = new WeiboDbHandler(context, "dbtest.db", null, 1);
        }

        public void save(WeiboContent weiboContent) {// 插入记录
            SQLiteDatabase db = mWeiboDbHandler.getWritableDatabase();// 取得数据库操作
            db.execSQL("insert into t_users (username,pass) values(?,?)", new Object[] { weiboContent.getUser(), weiboContent.getText()});
            db.close();// 记得关闭数据库操作
        }

        public void delete(Integer id) {// 删除纪录
            SQLiteDatabase db = mWeiboDbHandler.getWritableDatabase();
            db.execSQL("delete from t_users where id=?", new Object[] { id.toString() });
            db.close();
        }

        public void update(WeiboContent weiboContent) {// 修改纪录
            SQLiteDatabase db = mWeiboDbHandler.getWritableDatabase();
            db.execSQL("update t_users set username=?,pass=? where" + " id=?", new Object[] { weiboContent.getUser(), weiboContent.getText(), weiboContent.getId() });
            db.close();
        }

        public WeiboContent find(Integer id) {// 根据ID查找纪录
            WeiboContent WeiboContent = null;
            SQLiteDatabase db = mWeiboDbHandler.getReadableDatabase();
            // 用游标Cursor接收从数据库检索到的数据
            Cursor cursor = db.rawQuery("select * from t_users where id=?", new String[] { id.toString() });
            if (cursor.moveToFirst()) {// 依次取出数据
                WeiboContent = new WeiboContent();
            }
            db.close();
            return WeiboContent;
        }

        public List<WeiboContent> findAll() {// 查询所有记录
            List<WeiboContent> lists = new ArrayList<WeiboContent>();
            WeiboContent WeiboContent = null;
            SQLiteDatabase db = mWeiboDbHandler.getReadableDatabase();
            // Cursor cursor=db.rawQuery("select * from t_users limit ?,?", new
            // String[]{offset.toString(),maxLength.toString()});
            // //这里支持类型MYSQL的limit分页操作

            Cursor cursor = db.rawQuery("select * from t_users ", null);
            while (cursor.moveToNext()) {
                WeiboContent = new WeiboContent();
                lists.add(WeiboContent);
            }
            db.close();
            return lists;
        }

        public long getCount() {//统计所有记录数
            SQLiteDatabase db = mWeiboDbHandler.getReadableDatabase();
            Cursor cursor = db.rawQuery("select count(*) from t_users ", null);
            cursor.moveToFirst();
            db.close();
            return cursor.getLong(0);
        }
}
