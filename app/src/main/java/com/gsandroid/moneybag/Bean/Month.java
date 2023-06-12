package com.gsandroid.moneybag.Bean;

/**
 * Created by LZC on 2023/5/23.
 */

public class Month {

    private String year;

    private String month;
    private int in;
    private int out;

    public Month(String year, String month, int in, int out) {
        this.year = year;
        this.month = month;
        this.in = in;
        this.out = out;
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

    public int getIn() {
        return in;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }
}
