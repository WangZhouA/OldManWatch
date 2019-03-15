package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.DeviceinfoList;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.FamilyMemberModel;
import com.saiyi.oldmanwatch.mvp.view.FamilyMemberView;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public class FamilyMemberPresenter extends BasePresenter<FamilyMemberView<List<DeviceinfoList>>> {


    public FamilyMemberPresenter(FamilyMemberView<List<DeviceinfoList>> mView) {
        attachView(mView);
    }


    public void getDeviceList(BaseImpl baseImpl) {
        FamilyMemberModel.getInstance().getDeviceList(getView().getMac(), getView().getToken(), new MyBaseObserver<List<DeviceinfoList>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<DeviceinfoList> data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

    public void updateState(BaseImpl baseImpl) {
        FamilyMemberModel.getInstance().updateState(getView().getData(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }

    public void delDevice(BaseImpl baseImpl) {
        FamilyMemberModel.getInstance().delDevice(getView().getToken(), getView().getId(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }

}
