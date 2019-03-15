package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.AddDeviceBean;
import com.saiyi.oldmanwatch.entity.QueryDevice;

/**
 * @Author liwenbo
 * @Date 18/10/22
 * @Describe
 */
public interface BindingDeviceView<T> extends BaseRequestContract<T> {
    void getSuccess(String code);

    String getMac();

    String getToken();

    AddDeviceBean getData();
}
