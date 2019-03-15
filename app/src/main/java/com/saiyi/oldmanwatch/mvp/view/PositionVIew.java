package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.ControlDeviceBean;

/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public interface PositionVIew<T> extends BaseRequestContract<T> {

    ControlDeviceBean getData();

    String getToken();

    int getUid();
}
