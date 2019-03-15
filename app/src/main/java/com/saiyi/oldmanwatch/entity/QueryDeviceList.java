package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/10/24
 * @Describe
 */
public class QueryDeviceList {
    private String type;
    private String filiation;
    private String headUrl;
    private String mac;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFiliation() {
        return filiation;
    }

    public void setFiliation(String filiation) {
        this.filiation = filiation;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "QueryDeviceList{" +
                "type='" + type + '\'' +
                ", filiation='" + filiation + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
