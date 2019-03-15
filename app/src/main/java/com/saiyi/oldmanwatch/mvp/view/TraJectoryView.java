package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.QueryHeartsList;
import com.saiyi.oldmanwatch.entity.Trajectory;

/**
 * @Author liwenbo
 * @Date 18/11/16
 * @Describe
 */
public interface TraJectoryView<T> extends BaseRequestContract<T> {

    QueryHeartsList getData();

    String getToken();
}
