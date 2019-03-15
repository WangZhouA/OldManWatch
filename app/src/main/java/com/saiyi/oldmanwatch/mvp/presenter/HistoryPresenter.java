package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.HeartsList;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.HistoryModel;
import com.saiyi.oldmanwatch.mvp.view.HistoryView;

import java.util.List;


/**
 * @Author liwenbo
 * @Date 18/10/31
 * @Describe
 */
public class HistoryPresenter extends BasePresenter<HistoryView<List<HeartsList>>> {
    public HistoryPresenter(HistoryView<List<HeartsList>> historyView) {
        attachView(historyView);
    }

    public void getHeartsList(BaseImpl baseImpl) {
        HistoryModel.getInstance().execute(getView().getMac(), getView().getStartTime(), getView().getEndTime(), new MyBaseObserver<List<HeartsList>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<HeartsList> data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
