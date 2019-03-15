package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.NoticeList;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.NoticeModel;
import com.saiyi.oldmanwatch.mvp.view.NoticeView;

import java.util.List;


/**
 * @Author liwenbo
 * @Date 18/11/5
 * @Describe
 */
public class NoticePresenter extends BasePresenter<NoticeView<List<NoticeList>>> {

    public NoticePresenter(NoticeView<List<NoticeList>> mView) {
        attachView(mView);
    }


    public void getNoticeList(BaseImpl baseImpl) {
        NoticeModel.getInstance().getNoticeList(getView().getMac(), getView().getToken(), new MyBaseObserver<List<NoticeList>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<NoticeList> data) {
                getView().onRequestSuccessData(data);
            }
        });
    }

    public void delNotice(BaseImpl baseImpl) {
        NoticeModel.getInstance().delNotice(getView().getToken(), getView().getNid(), new MyBaseObserver<BaseResponse>(baseImpl, "正在删除...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }


}
