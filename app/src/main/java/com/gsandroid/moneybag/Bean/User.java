package com.gsandroid.moneybag.Bean;

/**
 * Created by LZC on 2023/5/23.
 */

public class User {
    private int ID;
    private String name;
    private String pwd;
    private int imgUrl;

    private int money;

    public User(int ID, String name, String pwd, int imgUrl, int money) {
        this.ID = ID;
        this.name = name;
        this.pwd = pwd;
        this.imgUrl = imgUrl;
        this.money = money;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
