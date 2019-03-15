package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.StepBean;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.StepModel;
import com.saiyi.oldmanwatch.mvp.view.StepView;


/**
 * @Author liwenbo
 * @Date 18/11/5
 * @Describe
 */
public class StepPresenter extends BasePresenter<StepView<StepBean>> {


    public StepPresenter(StepView<StepBean> mView) {
        attachView(mView);
    }


    public void getStep(BaseImpl baseImpl) {
        StepModel.getInstance().execute(getView().getMac(), getView().getStartTime(), getView().getEndTime(), new MyBaseObserver<StepBean>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(StepBean data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
