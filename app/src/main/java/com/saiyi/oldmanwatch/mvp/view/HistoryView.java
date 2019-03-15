package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;

/**
 * @Author liwenbo
 * @Date 18/10/31
 * @Describe
 */
public interface HistoryView<T> extends BaseRequestContract<T> {
    String getStartTime();

    String getEndTime();

    String getMac();
}
