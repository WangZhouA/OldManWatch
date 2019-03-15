package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.ControlDeviceBean;

/**
 * @Author liwenbo
 * @Date 18/11/12
 * @Describe
 */
public interface MyMapView<T> extends BaseRequestContract<T> {
    ControlDeviceBean getData();
}
