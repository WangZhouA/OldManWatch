package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.QueryDevice;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.BindingDeviceModel;
import com.saiyi.oldmanwatch.mvp.view.BindingDeviceView;
import com.saiyi.oldmanwatch.utils.Logger;

/**
 * @Author liwenbo
 * @Date 18/10/22
 * @Describe
 */
public class BindingDevicePresenter extends BasePresenter<BindingDeviceView<QueryDevice>> {


    public BindingDevicePresenter(BindingDeviceView<QueryDevice> mView) {
        attachView(mView);
    }

    public void addDevice(BaseImpl baseImpl) {
        BindingDeviceModel.getInstance().addDevice(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }

    public void queryDevice(BaseImpl baseImpl) {
        BindingDeviceModel.getInstance().queryDevice(getView().getMac(), getView().getToken(), new MyBaseObserver<QueryDevice>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(QueryDevice data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
