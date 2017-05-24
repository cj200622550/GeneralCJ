package com.toolsclass.chenjun.general.generalcj.AppRobotChat.db;

/**
 * 作者：陈骏 on 2017/5/23 21:25
 * QQ：200622550
 * Sql语句
 */

public class Sql {
    /**
     * 聊天记录
     */
    public static final String SQL_MSG = "Create table IF NOT EXISTS " + DBcolumns.TABLE_MSG
            + "(" + DBcolumns.MSG_ID + " integer primary key autoincrement,"
            + DBcolumns.MSG_FROM + " text,"
            + DBcolumns.MSG_TO + " text,"
            + DBcolumns.MSG_TYPE + " text,"
            + DBcolumns.MSG_CONTENT + " text,"
            + DBcolumns.MSG_ISCOMING + " integer,"
            + DBcolumns.MSG_DATE + " text,"
            + DBcolumns.MSG_ISREADED + " text,"
            + DBcolumns.MSG_BAK1 + " text,"
            + DBcolumns.MSG_BAK2 + " text,"
            + DBcolumns.MSG_BAK3 + " text,"
            + DBcolumns.MSG_BAK4 + " text,"
            + DBcolumns.MSG_BAK5 + " text,"
            + DBcolumns.MSG_BAK6 + " text);";
}
