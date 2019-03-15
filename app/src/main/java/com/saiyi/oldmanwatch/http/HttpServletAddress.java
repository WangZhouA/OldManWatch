package com.saiyi.oldmanwatch.http;

import android.text.TextUtils;

/**
 *
 */
public class HttpServletAddress {

    private String onlineAddress = "";   //线上地址
    private String offlineAddress = "http://58.250.30.13:8952/";  //线下地址

    private static final class SingletonHolder {
        private static final HttpServletAddress INSTANCE = new HttpServletAddress();
    }

    public static HttpServletAddress getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private HttpServletAddress() {
    }

    public String getOnlineAddress() {
        return onlineAddress;
    }

    public void setOnlineAddress(String onlineAddress) {
        this.onlineAddress = onlineAddress;
    }

    public String getOfflineAddress() {
        return offlineAddress;
    }

    public void setOfflineAddress(String offlineAddress) {
        this.offlineAddress = offlineAddress;
    }

    public String getServletAddress() {
        return TextUtils.isEmpty(onlineAddress) ? offlineAddress : onlineAddress;
    }
}