package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.RequestApplyBind;

/**
 * @Author liwenbo
 * @Date 18/10/23
 * @Describe
 */
public interface ApplyBindView<T> extends BaseRequestContract<T> {
    RequestApplyBind getBindData();

    String getToken();
}
