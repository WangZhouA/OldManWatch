package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.ReSetPwdModel;
import com.saiyi.oldmanwatch.mvp.view.ReSetPwdView;


/**
 * @Author liwenbo
 * @Date 18/10/31
 * @Describe
 */
public class ReSetPwdPresenter extends BasePresenter<ReSetPwdView<String>> {

    public ReSetPwdPresenter(ReSetPwdView<String> mView) {
        attachView(mView);
    }

    public void updatepwd(BaseImpl baseImpl) {
        ReSetPwdModel.getInstance().updatepwd(getView().getData(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().onRequestSuccessData(data.getCode());
            }
        });
    }

    public void getCode(BaseImpl baseImpl) {
        ReSetPwdModel.getInstance().getCode(getView().getPhone(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {

            }
        });
    }

}
