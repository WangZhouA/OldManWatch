package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.entity.UserBean;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/10/24
 * @Describe
 */
public interface MeView<T> extends BaseRequestContract<T> {
    void getData(List<QueryDeviceList> data);

    int getUid();

    String getToken();
}
