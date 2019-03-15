package com.saiyi.oldmanwatch.mvp.presenter;

import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.RemindModel;
import com.saiyi.oldmanwatch.mvp.view.ReimndView;

/**
 * @Author liwenbo
 * @Date 18/11/1
 * @Describe
 */
public class ReimndPresenter extends BasePresenter<ReimndView<BaseResponse>> {

    public ReimndPresenter(ReimndView<BaseResponse> mView) {
        attachView(mView);
    }


    public void addReimnd(BaseImpl baseImpl) {
        RemindModel.getInstance().addReimnd(getView().getRemind(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
