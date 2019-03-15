package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.ReimndList;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.RemindListModel;
import com.saiyi.oldmanwatch.mvp.view.RemindListView;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class RemindListPresenter extends BasePresenter<RemindListView<List<ReimndList>>> {

    public RemindListPresenter(RemindListView<List<ReimndList>> mView) {
        attachView(mView);
    }


    public void getRemindList(BaseImpl baseImpl) {
        RemindListModel.getInstance().getRemindList(getView().getData(), getView().getToken(), new MyBaseObserver<List<ReimndList>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<ReimndList> data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

    public void delRemind(BaseImpl baseImpl) {
        RemindListModel.getInstance().delRemind(getView().getRid(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }
}
