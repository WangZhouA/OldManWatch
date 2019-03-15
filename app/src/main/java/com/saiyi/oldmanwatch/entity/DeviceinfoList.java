package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public class DeviceinfoList {
    private int id;
    private String phone;
    private String deviceName;
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
