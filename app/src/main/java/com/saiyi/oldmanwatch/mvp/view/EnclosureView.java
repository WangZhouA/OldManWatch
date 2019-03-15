package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.UpdateEnclosureBean;

/**
 * @Author liwenbo
 * @Date 18/11/9
 * @Describe
 */
public interface EnclosureView<T> extends BaseRequestContract<T> {
    void getSuccess(String code);

    UpdateEnclosureBean getData();

    String getToken();

    String getMac();
}
