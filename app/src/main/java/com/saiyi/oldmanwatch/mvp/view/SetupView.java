package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.AddSetupBean;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public interface SetupView<T> extends BaseRequestContract<T> {
    void getSuccess(String code);

    AddSetupBean getData();

    String getToken();

    String getMac();
}
