package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.SetupBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.SetupModel;
import com.saiyi.oldmanwatch.mvp.view.SetupView;

import java.util.List;


/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class SetupPresenter extends BasePresenter<SetupView<List<SetupBean>>> {

    public SetupPresenter(SetupView<List<SetupBean>> mView) {
        attachView(mView);
    }


    public void getSetupList(BaseImpl baseImpl) {
        SetupModel.getInstance().getSetupList(getView().getMac(), getView().getToken(), new MyBaseObserver<List<SetupBean>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<SetupBean> data) {
                getView().onRequestSuccessData(data);
            }
        });
    }


    public void addSetup(BaseImpl baseImpl) {
        SetupModel.getInstance().addSetup(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }
}
