package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.Trajectory;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.TrajectoryModel;
import com.saiyi.oldmanwatch.mvp.view.TraJectoryView;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/16
 * @Describe
 */
public class TrajectoryPresenter extends BasePresenter<TraJectoryView<List<Trajectory>>> {

    public TrajectoryPresenter(TraJectoryView<List<Trajectory>> mView) {
        attachView(mView);
    }


    public void getTraJectory(BaseImpl baseImpl) {
        TrajectoryModel.getInstance().getTraJectory(getView().getData(), getView().getToken(), new MyBaseObserver<List<Trajectory>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<Trajectory> data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
