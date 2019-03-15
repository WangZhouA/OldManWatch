package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.SMSSetModel;
import com.saiyi.oldmanwatch.mvp.view.SMSSetView;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class SMSSetPresenter extends BasePresenter<SMSSetView<String>> {

    public SMSSetPresenter(SMSSetView<String> mView) {
        attachView(mView);
    }

    public void updateSetup(BaseImpl baseImpl) {
        SMSSetModel.getInstance().updateSetup(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().onRequestSuccessData(data.getCode());
            }
        });
    }
}
