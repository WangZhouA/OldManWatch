package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.RequestRegister;

/**
 * @Author liwenbo
 * @Date 18/10/17
 * @Describe
 */
public interface RegisterView<T> extends BaseRequestContract<T> {
    RequestRegister getData();

    String getPhone();

    void queryPhone(String code);
}
