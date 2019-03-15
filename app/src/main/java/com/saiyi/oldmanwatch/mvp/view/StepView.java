package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;

/**
 * @Author liwenbo
 * @Date 18/11/5
 * @Describe
 */
public interface StepView<T> extends BaseRequestContract<T> {
    String getMac();

    String getStartTime();

    String getEndTime();
}
