package com.saiyi.oldmanwatch.http;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 服务器返回的json基类
 */
public class BaseResponse<T> implements Serializable {

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private T data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    /**
     * API是否请求成功
     *
     * @return 成功返回true, 失败返回false
     */
    public boolean isRequestSuccess() {
        return isSuccess();
    }
}
