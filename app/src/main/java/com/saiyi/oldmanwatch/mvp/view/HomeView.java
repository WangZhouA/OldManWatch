package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.QueryDeviceDetailsBean;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public interface HomeView<T> extends BaseRequestContract<T> {

    void getDevices(List<QueryDeviceList> list);

    QueryDeviceDetailsBean getQueryDeviceDetails();

    int getUid();

    String getToken();
}
