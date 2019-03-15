package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.Hearts;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.BloodPressureModel;
import com.saiyi.oldmanwatch.mvp.view.BloodPressureView;

/**
 * @Author liwenbo
 * @Date 18/11/6
 * @Describe
 */
public class BloodPressurePresenter extends BasePresenter<BloodPressureView<Hearts>> {

    public BloodPressurePresenter(BloodPressureView<Hearts> mView) {
        attachView(mView);
    }


    public void getHearts(BaseImpl baseImpl) {
        BloodPressureModel.getInstance().execute(getView().getMac(), new MyBaseObserver<Hearts>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(Hearts data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

}
