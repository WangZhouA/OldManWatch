package com.saiyi.oldmanwatch.mvp.presenter;

import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.RegisterModel;
import com.saiyi.oldmanwatch.mvp.view.RegisterView;


/**
 * @Author liwenbo
 * @Date 18/10/17
 * @Describe
 */
public class RegisterPresenter extends BasePresenter<RegisterView<BaseResponse>> {

    public RegisterPresenter(RegisterView<BaseResponse> mView) {
        attachView(mView);
    }


    public void register(BaseImpl baseImpl) {
        RegisterModel.getInstance().register(getView().getData(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

    public void queryPhone(BaseImpl baseImpl) {
        RegisterModel.getInstance().queryPhone(getView().getPhone(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().queryPhone(data.getCode());
            }
        });
    }

    public void getCode(BaseImpl baseImpl) {
        RegisterModel.getInstance().getCode(getView().getPhone(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
            }
        });
    }

}
