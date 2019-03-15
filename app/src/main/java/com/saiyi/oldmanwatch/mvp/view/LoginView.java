package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.RequestLogin;

/**
 * @Author liwenbo
 * @Date 18/10/20
 * @Describe
 */
public interface LoginView<T> extends BaseRequestContract<T> {
    RequestLogin getData();
}
