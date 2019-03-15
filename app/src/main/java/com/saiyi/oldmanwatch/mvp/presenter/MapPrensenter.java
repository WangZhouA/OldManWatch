package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.MapModel;
import com.saiyi.oldmanwatch.mvp.view.MyMapView;


/**
 * @Author liwenbo
 * @Date 18/11/12
 * @Describe
 */
public class MapPrensenter extends BasePresenter<MyMapView<String>> {

    public MapPrensenter(MyMapView<String> mView) {
        attachView(mView);
    }


    public void sendCmDevice(BaseImpl baseImpl) {
        MapModel.getInstance().sendCmDevice(getView().getData(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().onRequestSuccessData(data.getCode());
            }
        });
    }
}
