package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.entity.UserBean;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.MeModel;
import com.saiyi.oldmanwatch.mvp.view.MeView;

import java.util.List;


/**
 * @Author liwenbo
 * @Date 18/10/24
 * @Describe
 */
public class MePresenter extends BasePresenter<MeView<UserBean>> {

    public MePresenter(MeView<UserBean> mView) {
        attachView(mView);
    }


    public void queryDeviceList(BaseImpl baseImpl) {
        MeModel.getInstance().queryDeviceList(getView().getUid(), getView().getToken(), new MyBaseObserver<List<QueryDeviceList>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<QueryDeviceList> data) {
                getView().getData(data);
            }
        });
    }

    public void getUser(BaseImpl baseImpl) {
        MeModel.getInstance().getUser(getView().getUid() + "", getView().getToken(), new MyBaseObserver<UserBean>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(UserBean data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
