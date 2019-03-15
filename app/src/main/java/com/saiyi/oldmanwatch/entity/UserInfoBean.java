package com.saiyi.oldmanwatch.entity;

/**
 * Created by 最帅的男人 on 2019/3/4.
 */
public class UserInfoBean {

    private String url;
    private String name;

    public UserInfoBean(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
