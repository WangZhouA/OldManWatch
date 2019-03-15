package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class AddSetupBean {
    private String mac;
    private List<SetupBean> setup;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public List<SetupBean> getData() {
        return setup;
    }

    public void setData(List<SetupBean> data) {
        this.setup = data;
    }

    @Override
    public String toString() {
        return "AddSetupBean{" +
                "mac='" + mac + '\'' +
                ", data=" + setup +
                '}';
    }
}
