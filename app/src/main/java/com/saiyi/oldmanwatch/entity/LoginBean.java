package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/10/17
 * @Describe
 */
public class LoginBean {
    private int uid;
    private String headUrl;
    private String name;
    private String token;
    private String mac;
    private String type;
    private String filiation;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getFiliation() {
        return filiation;
    }

    public void setFiliation(String filiation) {
        this.filiation = filiation;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "uid=" + uid +
                ", headUrl='" + headUrl + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", mac='" + mac + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
