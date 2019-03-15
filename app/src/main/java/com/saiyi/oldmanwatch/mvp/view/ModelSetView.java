package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.ModelSetBean;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public interface ModelSetView<T> extends BaseRequestContract<T> {

    ModelSetBean getData();

    String getToken();
}
