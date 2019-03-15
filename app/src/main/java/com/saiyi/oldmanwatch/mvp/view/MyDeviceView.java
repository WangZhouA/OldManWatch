package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.DelDevice;
import com.saiyi.oldmanwatch.entity.DeviceState;
import com.saiyi.oldmanwatch.entity.SwitchSetBean;
import com.saiyi.oldmanwatch.entity.UpdateDeviceBean;
import com.saiyi.oldmanwatch.entity.UploadImgBean;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public interface MyDeviceView<T> extends BaseRequestContract<T> {
    void getSuccess(String code);

    void getCode(String code);

    void getImgUrl(String url);

    String getUrl();

    String getToken();

    String getMac();

    SwitchSetBean getSwitchData();

    DelDevice getDevice();

    UpdateDeviceBean getData();
}
