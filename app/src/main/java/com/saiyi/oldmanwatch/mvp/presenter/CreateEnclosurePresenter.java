package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.CreateEnclosureModel;
import com.saiyi.oldmanwatch.mvp.view.CreateEnclosureView;

/**
 * @Author liwenbo
 * @Date 18/11/9
 * @Describe
 */
public class CreateEnclosurePresenter extends BasePresenter<CreateEnclosureView<BaseResponse>> {

    public CreateEnclosurePresenter(CreateEnclosureView<BaseResponse> mView) {
        attachView(mView);
    }

    public void addEnclosure(BaseImpl baseImpl) {
        CreateEnclosureModel.getInstance().addEnclosure(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
