package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.DeviceDetailsBean;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.HomeModel;
import com.saiyi.oldmanwatch.mvp.view.HomeView;

import java.util.List;


/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public class HomePresenter extends BasePresenter<HomeView<DeviceDetailsBean>> {

    public HomePresenter(HomeView<DeviceDetailsBean> mView) {
        attachView(mView);
    }

    public void getDeviceDetails(BaseImpl baseImpl) {
        HomeModel.getInstance().getDeviceDetails(getView().getQueryDeviceDetails(), getView().getToken(), new MyBaseObserver<DeviceDetailsBean>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(DeviceDetailsBean data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

    public void queryDeviceList(BaseImpl baseImpl) {
        HomeModel.getInstance().queryDeviceList(getView().getUid(), getView().getToken(), new MyBaseObserver<List<QueryDeviceList>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<QueryDeviceList> data) {
                getView().getDevices(data);
            }
        });
    }
}
