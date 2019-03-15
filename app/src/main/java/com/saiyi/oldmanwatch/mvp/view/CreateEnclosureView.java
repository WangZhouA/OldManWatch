package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.AddEnclosure;

/**
 * @Author liwenbo
 * @Date 18/11/9
 * @Describe
 */
public interface CreateEnclosureView<T> extends BaseRequestContract<T> {
    AddEnclosure getData();

    String getToken();
}
