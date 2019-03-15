package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.SosPhoneBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.SosModel;
import com.saiyi.oldmanwatch.mvp.view.SosView;

import java.util.List;


/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public class SosPresenter extends BasePresenter<SosView<List<SosPhoneBean>>> {

    public SosPresenter(SosView<List<SosPhoneBean>> mView) {
        attachView(mView);
    }


    public void getSosList(BaseImpl baseImpl) {
        SosModel.getInstance().getSosList(getView().getMac(), getView().getToken(), new MyBaseObserver<List<SosPhoneBean>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<SosPhoneBean> data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

    public void setSos(BaseImpl baseImpl) {
        SosModel.getInstance().setSos(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }
}
