package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.AddSosBean;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public interface SosView<T> extends BaseRequestContract<T> {
    void getSuccess(String code);

    String getMac();

    String getToken();

    AddSosBean getData();
}
