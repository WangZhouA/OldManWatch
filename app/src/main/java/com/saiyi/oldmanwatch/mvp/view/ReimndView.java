package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.AddReimnd;

/**
 * @Author liwenbo
 * @Date 18/11/1
 * @Describe
 */
public interface ReimndView<T> extends BaseRequestContract<T> {
    AddReimnd getRemind();
}
