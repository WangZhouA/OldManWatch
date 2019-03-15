package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.UpdateDeviceState;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public interface FamilyMemberView<T> extends BaseRequestContract<T> {
    void getSuccess(String code);

    int getId();

    String getToken();

    String getMac();

    UpdateDeviceState getData();
}
