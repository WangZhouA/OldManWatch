package com.saiyi.oldmanwatch.base;

/**
 * Created by jack on 2017/6/20
 */

public interface BaseRequestContract<T> {

    void onRequestSuccessData(T data);

}