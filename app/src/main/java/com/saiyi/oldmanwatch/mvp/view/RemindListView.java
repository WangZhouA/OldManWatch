package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.QueryRemindList;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public interface RemindListView<T> extends BaseRequestContract<T> {

    void getSuccess(String code);

    QueryRemindList getData();

    String getToken();

    int getRid();
}
