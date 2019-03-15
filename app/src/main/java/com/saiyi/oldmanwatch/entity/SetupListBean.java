package com.saiyi.oldmanwatch.entity;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class SetupListBean {
    private String code;
    private String message;
    private boolean success;
    private List<SetupBean> data;

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

    public List<SetupBean> getData() {
        return data;
    }

    public void setData(List<SetupBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SetupListBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
