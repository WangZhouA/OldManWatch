package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.PositionModel;
import com.saiyi.oldmanwatch.mvp.view.PositionVIew;
import com.saiyi.oldmanwatch.utils.Logger;

import java.util.List;


/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public class PositionPresenter extends BasePresenter<PositionVIew<List<QueryDeviceList>>> {


    public PositionPresenter(PositionVIew<List<QueryDeviceList>> mView) {
        attachView(mView);
    }

    public void queryDeviceList(BaseImpl baseImpl) {
        PositionModel.getInstance().queryDeviceList(getView().getUid(), getView().getToken(), new MyBaseObserver<List<QueryDeviceList>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<QueryDeviceList> data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

    public void sendCmDevice(BaseImpl baseImpl) {
        PositionModel.getInstance().sendCmDevice(getView().getData(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {

            }
        });
    }
}
