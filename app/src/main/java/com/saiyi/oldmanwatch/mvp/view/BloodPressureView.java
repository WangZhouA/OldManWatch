package com.saiyi.oldmanwatch.mvp.view;


import com.saiyi.oldmanwatch.base.BaseRequestContract;

/**
 * @Author liwenbo
 * @Date 18/11/6
 * @Describe
 */
public interface BloodPressureView<T> extends BaseRequestContract<T> {
    String getMac();
}
