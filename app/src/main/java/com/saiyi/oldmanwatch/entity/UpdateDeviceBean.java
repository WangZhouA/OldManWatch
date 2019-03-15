package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public class UpdateDeviceBean {
    private int uid;
    private String mac;
    private String relationName;
    private String headUrl;
    private String filiation;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getFiliation() {
        return filiation;
    }

    public void setFiliation(String filiation) {
        this.filiation = filiation;
    }

}
