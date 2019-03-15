package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public class AddSosBean {
    private String mac;
    private List<SosPhoneBean> contacts;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }


    public List<SosPhoneBean> getContacts() {
        return contacts;
    }

    public void setContacts(List<SosPhoneBean> contacts) {
        this.contacts = contacts;
    }
}
