package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.UpdateUserBean;

/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public interface PerfectPersonalView<T> extends BaseRequestContract<T> {
    void getSuccess(String code);

    String getUrl();

    UpdateUserBean getData();

    String getToken();
}
