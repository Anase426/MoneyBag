package com.gsandroid.moneybag.DButils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gsandroid.moneybag.Bean.Money;
import com.gsandroid.moneybag.Bean.Month;
import com.gsandroid.moneybag.Bean.User;
import com.gsandroid.moneybag.HomeActivity;
import com.gsandroid.moneybag.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLiteDao {
    private MyDbHelper myDbHelper;

    private Context context;

    public MySQLiteDao(Context context) {
        this.myDbHelper = new MyDbHelper(context);
        this.context = context;
    }

    public User Login(String name, String pwd) {
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        String sql = "select * from Money_Login where name=? and pwd=?";
        Cursor groupCursor = db.rawQuery(sql, new String[]{name, pwd});
        User user = null;
        if (groupCursor != null && groupCursor.moveToFirst())
            user = new User(groupCursor.getInt(groupCursor.getColumnIndexOrThrow("ID")),
                    groupCursor.getString(groupCursor.getColumnIndexOrThrow("name")),
                    groupCursor.getString(groupCursor.getColumnIndexOrThrow("pwd")),
                    groupCursor.getInt(groupCursor.getColumnIndexOrThrow("imgUrl")),
                    0);
        db.close();
        return user;
    }

    public void removeUser() {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        String sql = "delete from user";
        db.execSQL(sql);
        db.close();
    }

    public void register(String name, String pwd, int imgUrl) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        String sql = "insert into Money_Login(name, pwd, imgUrl, money)\n" +
                " values(?,?,?,?)";
        db.execSQL(sql, new Object[]{name, pwd, imgUrl, 0});
        db.close();
    }

    public Map<String, Object> findBill(int id) {
        List<Month> monthList = new ArrayList<Month>();
        Map<Month, List<Money>> moneyMap = new HashMap<Month, List<Money>>();

        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        String sql = "select distinct year_month from view_Money where ID=? order by year_month desc";
        Cursor groupCursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        while (groupCursor.moveToNext()) {
            String yearMonth = groupCursor.getString(groupCursor.getColumnIndexOrThrow("year_month"));
            List<Money> list = new ArrayList<Money>();
            int inSum = 0, outSum = 0;
            sql = "select * from view_Money where ID=? and year_month=?";
            Cursor childCursor = db.rawQuery(sql, new String[]{String.valueOf(id), yearMonth});
            while (childCursor.moveToNext()) {
                int type = childCursor.getInt(childCursor.getColumnIndexOrThrow("type"));
                int moneyNum = childCursor.getInt(childCursor.getColumnIndexOrThrow("money"));
                String[] year_month = childCursor.getString(childCursor.getColumnIndexOrThrow("year_month")).split("-");

                Money money = new Money(childCursor.getInt(childCursor.getColumnIndexOrThrow("Bag_ID")),
                        type,
                        childCursor.getString(childCursor.getColumnIndexOrThrow("type_name")),
                        year_month[0],
                        year_month[1],
                        childCursor.getString(childCursor.getColumnIndexOrThrow("date")),
                        moneyNum);

                if (type == 1)
                    inSum += moneyNum;
                else if (type == 0)
                    outSum += moneyNum;

                list.add(money);
            }
            String[] year_month = yearMonth.split("-");
            Month month = new Month(year_month[0], year_month[1], inSum, outSum);
            monthList.add(month);
            moneyMap.put(month, list);
        }
        db.close();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("monthList", monthList);
        map.put("moneyMap", moneyMap);
        return map;
    }


    public void addMoneybag(int type, String type_name, int year, String month, String day, int money) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        String sql = "insert into Money_bag(User_ID,year_month,date,type,type_name,money)\n" +
                " values(?,?,?,?,?,?);";
        db.execSQL(sql, new Object[]{HomeActivity.LoginUser.getID(),
                year + "-" + month, day, type, type_name, money});
        db.close();
    }

    public void editMoneybag(Money moneybag, int type, String type_name, int year, String month, String day, int money) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        String sql = "update Money_bag set year_month = ?,date = ?,type = ?, type_name = ?, money = ? " +
                "where Bag_ID = ?;";
        db.execSQL(sql, new Object[]{year + "-" + month, day, type, type_name, money, moneybag.getBag_Id()});
        db.close();
    }

    public void editUser(User loginUser, String name, int imgUrl) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        String sql = "update Money_Login set name = ?,imgUrl = ? " +
                "where ID = ?;";
        db.execSQL(sql, new Object[]{name, imgUrl, loginUser.getID()});
        db.close();
    }

    public void editPwd(User loginUser, String password) {
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        String sql = "update Money_Login set pwd = ? " +
                "where ID = ?;";
        db.execSQL(sql, new Object[]{password, loginUser.getID()});
        db.close();
    }
}
