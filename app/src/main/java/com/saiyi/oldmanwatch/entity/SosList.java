package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public class SosList {
    private String code;
    private String message;
    private boolean success;
    private List<SosPhoneBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<SosPhoneBean> getData() {
        return data;
    }

    public void setData(List<SosPhoneBean> data) {
        this.data = data;
    }
}
