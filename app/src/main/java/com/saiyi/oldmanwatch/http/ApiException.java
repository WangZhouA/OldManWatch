package com.saiyi.oldmanwatch.http;

/**
 * 异常处理的一个类
 */
public class ApiException extends RuntimeException {

    private String mErrorCode;

    public ApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */
    public boolean isTokenExpried() {
        return Integer.parseInt(mErrorCode) == ConstantCode.EXCEPTION_TOKEN;
    }
}
