package com.gsandroid.moneybag.DButils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LZC on 2023/5/23.
 */

public class MyDbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "MoneyBag";
    public static final int DB_VER = 1;
    private Context context;

    public MyDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE [Money_Login]( " +
                "  [ID] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  " +    // 用户id
                "  [name] VARCHAR NOT NULL,  " +                            // 用户名
                "  [pwd] VARCHAR NOT NULL,  " +                             // 密码
                "  [imgUrl] INTEGER NOT NULL," +                            // 头像
                "  [money] INTEGER);";                                      // 余额
        db.execSQL(sql);
        sql = "CREATE TABLE [Money_bag]( " +
                "  [Bag_ID] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,  " +// 钱包id
                "  [User_ID] INTEGER  NOT NULL,  " +                        // 用户id
                "  [year_month] VARCHAR NOT NULL,  " +                      // 年-月
                "  [date] VARCHAR NOT NULL,  " +                            // 日期
                "  [type] INTEGER NOT NULL,  " +                            // 收入或支出
                "  [type_name] VARCHAR NOT NULL,  " +                       // 类型名称
                "  [money] INTEGER NOT NULL);";                             // 金额
        db.execSQL(sql);
        sql = "CREATE VIEW [view_Money] AS " +
                "SELECT [u].[ID], " +                                       // 用户id
                "       [c].[Bag_ID]," +                                    // 钱包id
                "       [c].[year_month]," +                                // 年-月
                "       [c].[date]," +                                      // 日期
                "       [c].[type]," +                                      // 收入或支出
                "       [c].[type_name]," +                                 // 类型名称
                "       [c].[money] " +                                     // 金额
                "FROM   [Money_Login] [u] " +
                "JOIN [Money_bag] [c] ON [c].[User_ID] = [u].[ID];";
        db.execSQL(sql);
        initData(db);
    }

    private void initData(SQLiteDatabase db) {
        String sql = "insert into Money_Login(ID,name,pwd,imgUrl,money)\n" +
                " values(?,?,?,?,?);";
        db.execSQL(sql, new Object[]{1001, "linzecheng", "123456", 1, 0});
        sql = "insert into Money_bag(User_ID,year_month,date,type,type_name,money)\n" +
                " values(?,?,?,?,?,?);";
        db.execSQL(sql, new Object[]{1001, "2023-04", "05", 1, "工资", 200});
        sql = "insert into Money_bag(User_ID,year_month,date,type,type_name,money)\n" +
                " values(?,?,?,?,?,?);";
        db.execSQL(sql, new Object[]{1001, "2023-03", "07", 0, "游戏", 500});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
