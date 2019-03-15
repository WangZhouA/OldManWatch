package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.Enclosures;
import com.saiyi.oldmanwatch.entity.UpdateEnclosureBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.EnclosureModel;
import com.saiyi.oldmanwatch.mvp.view.EnclosureView;


/**
 * @Author liwenbo
 * @Date 18/11/9
 * @Describe
 */
public class EnclosurePresenter extends BasePresenter<EnclosureView<Enclosures>> {


    public EnclosurePresenter(EnclosureView<Enclosures> mView) {
        attachView(mView);
    }


    public void updateEnclosure(BaseImpl baseImpl) {
        EnclosureModel.getInstance().updateEnclosure(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }

    public void getEnclosures(BaseImpl baseImpl) {
        EnclosureModel.getInstance().getEnclosures(getView().getMac(), new MyBaseObserver<Enclosures>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(Enclosures data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

}
