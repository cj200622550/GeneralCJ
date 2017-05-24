package com.toolsclass.chenjun.general.generalcj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.toolsclass.chenjun.general.generalcj.AppRobotChat.db.DBcolumns;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.db.Sql;

/**
 * 作者：陈骏 on 2017/5/23 21:21
 * QQ：200622550
 * 数据库帮助类
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "qrobot_cj";
    private static final int DB_VERSION = 1;
    private static DBHelper mInstance;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public synchronized static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
        return mInstance;
    }

    ;

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** 聊天记录 */
        db.execSQL(Sql.SQL_MSG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
