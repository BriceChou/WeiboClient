package com.bricechou.weiboclient.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Public utils tool to deal with SQL database or table operation.
 *
 * @author BriceChou
 * @datetime 16-6-17 13:57
 */
public class SQLiteUtil {
    public static final String DATABASE_NAME = "weiboclient.db";
    private static final String TAG = "weiboclient.utils.SQLiteUtil";
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private ArrayList<String> mColums;
    private Class<?> mAdpater;

    public SQLiteUtil(Context context, Class<?> adpater) {
        mSQLiteOpenHelper = new SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            }
        };
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mColums = new ArrayList<String>();
        this.mAdpater = adpater;
    }

    /**
     * To close the database.
     *
     * @author BriceChou
     * @datetime 16-6-17 14:13
     */
    public void closeDatabse() {
        mSQLiteDatabase.close();
        mSQLiteOpenHelper.close();
    }

    public SQLiteDatabase getmSQLiteDatabase() {
        return mSQLiteDatabase;
    }

    /**
     * To judge the table exist or not.
     *
     * @author BriceChou
     * @datetime 16-6-17 15:28
     */
    public boolean hasTable(String tableName) {
        boolean flag = false;
        Cursor mCursor;
        StringBuffer mStringBuffer = new StringBuffer("select count(*) as c from Sqlite_master where type='table' and name ='");
        try {
            mStringBuffer.append(tableName.trim() + "'");
            mCursor = mSQLiteDatabase.rawQuery(mStringBuffer.toString(), null);
            if (mCursor.moveToNext()) {
                int count = mCursor.getInt(0);
                if (count > 0) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            return flag;
        }
        return flag;
    }

    /**
     * To create the table with tableName.
     * In addition,if this table is exist and add the table parameter.
     *
     * @param tableName  table name
     * @param primaryKey if not ,NULL
     * @author BriceChou
     * @datetime 16-6-17 15:31
     */
    public void createTable(String tableName, String primaryKey) {
        Field[] fields = mAdpater.getDeclaredFields();
        if (!hasTable(tableName)) {
            StringBuffer createTableSql = new StringBuffer("CREATE TABLE " + tableName + "(");
            int length = fields.length,
                    i;
            for (i = 1; i < length; i++) {
                Field f = fields[i];
                String fieldName = f.getName();
                Log.d(TAG, "createTable: " + fieldName);
                String fieldType = typeConversion(f.getType().getSimpleName());
                createTableSql.append(" " + fieldName + " " + fieldType + " ");
                if (fieldName.equals(primaryKey)) {
                    createTableSql.append(" PRIMARY KEY  AUTOINCREMENT  NOT NULL ");
                }
                if (i < length - 1) {
                    createTableSql.append(",");
                }
            }
            createTableSql.append(")");
            Log.i(TAG, "createTable: Successed create the SQL table." + createTableSql.toString());
            mSQLiteDatabase.execSQL(createTableSql.toString());
        } else {
            int length = fields.length,
                    i;
            for (i = 0; i < length; i++) {
                mColums.add(fields[i].getName());
            }
        }
    }

    /**
     * To delete the database table by table_name.
     *
     * @author BriceChou
     * @datetime 16-6-17 下午3:30
     */
    public void deleteTable(String tableName) {
        if (hasTable(tableName)) {
            mSQLiteDatabase.execSQL("DROP FORM " + tableName);
        }
    }

    /**
     * To delete all the table data.
     *
     * @param tableName
     * @author BriceChou
     * @datetime 16-6-17 15:37
     */
    public void clearTableData(String tableName) {
        if (hasTable(tableName)) {
            mSQLiteDatabase.execSQL("DELETE FORM " + tableName);
        }
    }

    public void save(Object obj, String tableName) {
        StringBuffer sql = new StringBuffer("INSERT INTO " + tableName + " (");
        Field[] fields = mAdpater.getDeclaredFields();
        if (hasTable(tableName)) {
            int length = fields.length,
                    i;
            for (i = 0; i < length; i++) {
                Field f = fields[i];
                String fieldName = f.getName();
                sql.append(fieldName);
                if (i < length - 1) {
                    sql.append(",");
                }
            }
            sql.append(" values(");
            for (i = 0; i < length; i++) {
                sql.append("?");
                if (i < length - 1) {
                    sql.append(",");
                }
            }
        } else {
            createTable(tableName, "id");
        }
        sql.append(")");
        mSQLiteDatabase.execSQL(sql.toString(), new Object[]{fields});
        Log.d(TAG, "save: complete to save this instance.");
        closeDatabse();
    }

    public String typeConversion(String nameType) {
        String str = "";
        if (nameType == null) {
            return null;
        } else if (nameType.equals("Integer")) {//判断变量的类型
            str = "Integer";
        } else if (nameType.equals("String")) {
            str = "varchar";
        } else if (nameType.equals("Double")) {
            str = "Decimal";
        } else if (nameType.equals("Float")) {
            str = "Decimal";
        } else if (nameType.equals("Long")) {
            str = "varchar";
        } else if (nameType.equals("Boolean")) {
            str = "TINYINT";
        }
        return str;
    }
}
