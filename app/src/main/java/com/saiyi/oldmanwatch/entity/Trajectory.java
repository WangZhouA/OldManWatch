package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/16
 * @Describe
 */
public class Trajectory {
    private String date;
    private String e;
    private String n;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "date='" + date + '\'' +
                ", e='" + e + '\'' +
                ", n='" + n + '\'' +
                '}';
    }
}
