package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/11/5
 * @Describe
 */
public class QueryStepBean {
    private String startDate;
    private String mac;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "QueryStepBean{" +
                "startDate='" + startDate + '\'' +
                ", mac='" + mac + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
