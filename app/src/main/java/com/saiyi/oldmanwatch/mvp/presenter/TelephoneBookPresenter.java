package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.BasicBean;
import com.saiyi.oldmanwatch.entity.ContactsBean;
import com.saiyi.oldmanwatch.entity.ContactsListBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.mvp.model.TelephoneBookModel;
import com.saiyi.oldmanwatch.mvp.view.IView;
import com.saiyi.oldmanwatch.mvp.view.TelephoneBookView;
import com.saiyi.oldmanwatch.utils.Logger;

import java.util.List;

/**
 * @Author liwenbo
 * @Date 18/10/24
 * @Describe
 */
public class TelephoneBookPresenter extends BasePresenter<TelephoneBookView<List<ContactsListBean>>> {

    public TelephoneBookPresenter(TelephoneBookView<List<ContactsListBean>> mView) {
        attachView(mView);
    }

    public void getContactsList(BaseImpl baseImpl) {
        TelephoneBookModel.getInstance().getContactsList(getView().getMac(), getView().getToken(), new MyBaseObserver<List<ContactsListBean>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<ContactsListBean> data) {
                getView().onRequestSuccessData(data);
            }

            @Override
            public void onError(Throwable e) {
                getView().onRequestSuccessData(null);
                super.onError(e);
            }
        });
    }

    public void addContacts(BaseImpl baseImpl) {
        TelephoneBookModel.getInstance().addContacts(getView().getContacts(), getView().getToken(), new MyBaseObserver<BaseResponse>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BaseResponse data) {
                getView().getSuccess(data.getCode());
            }
        });
    }


}
