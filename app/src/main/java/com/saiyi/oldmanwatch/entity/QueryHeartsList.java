package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/10/30
 * @Describe
 */
public class QueryHeartsList {
    private String mac;
    private String startDate;
    private String endDate;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
