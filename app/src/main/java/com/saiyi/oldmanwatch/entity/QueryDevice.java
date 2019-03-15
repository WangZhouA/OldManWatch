package com.saiyi.oldmanwatch.entity;

/**
 * @Author liwenbo
 * @Date 18/10/23
 * @Describe
 */
public class QueryDevice {
    private String phone;
    private int type;
    private String mac;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "QueryDevice{" +
                "phone='" + phone + '\'' +
                ", type=" + type +
                ", mac='" + mac + '\'' +
                '}';
    }
}
