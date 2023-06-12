package com.gsandroid.moneybag.Bean;

/**
 * Created by LZC on 2023/5/23.
 */

public class Money {
    private int Bag_Id;
    private int type;
    private String type_name;
    private String year;
    private String month;
    private String date;
    private int money_num;

    public Money(int bag_Id, int type, String type_name, String year, String month, String date, int money_num) {
        Bag_Id = bag_Id;
        this.type = type;
        this.type_name = type_name;
        this.year = year;
        this.month = month;
        this.date = date;
        this.money_num = money_num;
    }

    public int getBag_Id() {
        return Bag_Id;
    }

    public void setBag_Id(int bag_Id) {
        Bag_Id = bag_Id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMoney_num() {
        return money_num;
    }

    public String getMoney_to(int type) {
        if(type == 0){
            return "-"+money_num;
        }
        if (type == 1) {
            return "+"+money_num;
        }
        return null;
    }

    public void setMoney_num(int money_num) {
        this.money_num = money_num;
    }
}
