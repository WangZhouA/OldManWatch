package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;

/**
 * @Author liwenbo
 * @Date 18/11/5
 * @Describe
 */
public interface NoticeView<T> extends BaseRequestContract<T> {
    void getSuccess(String code);

    int getNid();

    String getToken();

    String getMac();
}
