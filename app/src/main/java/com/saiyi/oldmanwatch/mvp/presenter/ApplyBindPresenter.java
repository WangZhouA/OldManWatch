package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.ApplyBindModel;
import com.saiyi.oldmanwatch.mvp.view.ApplyBindView;


/**
 * @Author liwenbo
 * @Date 18/10/23
 * @Describe
 */
public class ApplyBindPresenter extends BasePresenter<ApplyBindView<String>> {

    public ApplyBindPresenter(ApplyBindView<String> mView) {
        attachView(mView);
    }

    public void applyBind(BaseImpl baseImpl) {
        ApplyBindModel.getInstance().applyBind(getView().getBindData(), getView().getToken(),
                new MyBaseObserver<Void>(baseImpl, "正在加载...") {
                    @Override
                    protected void onBaseNext(Void data) {
                        getView().onRequestSuccessData("1001");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onRequestSuccessData("1002");
                    }
                });
    }
}
