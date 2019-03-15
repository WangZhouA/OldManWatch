package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class ReimndList {
    private int id;
    private String cycle;
    private String period;
    private String remindTime;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "id=" + id +
                ", cycle='" + cycle + '\'' +
                ", period='" + period + '\'' +
                ", remindTime='" + remindTime + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
