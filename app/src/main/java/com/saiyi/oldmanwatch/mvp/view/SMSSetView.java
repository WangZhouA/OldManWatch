package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.SwitchSetBean;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public interface SMSSetView<T> extends BaseRequestContract<T> {
    SwitchSetBean getData();

    String getToken();
}
