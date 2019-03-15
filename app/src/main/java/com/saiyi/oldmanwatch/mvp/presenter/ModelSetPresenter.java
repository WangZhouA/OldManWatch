package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.ModelSetModel;
import com.saiyi.oldmanwatch.mvp.view.ModelSetView;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public class ModelSetPresenter extends BasePresenter<ModelSetView<BaseResponse>> {

    public ModelSetPresenter(ModelSetView<BaseResponse> mView) {
        attachView(mView);
    }


    public void updateModelSet(BaseImpl baseImpl) {
        ModelSetModel.getInstance().updateModelSet(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
