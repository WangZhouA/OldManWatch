package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.LoginBean;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.http.util.ToastUtils;
import com.saiyi.oldmanwatch.mvp.model.LoginModel;
import com.saiyi.oldmanwatch.mvp.view.LoginView;
import com.saiyi.oldmanwatch.utils.Logger;


/**
 * @Author liwenbo
 * @Date 18/10/20
 * @Describe
 */
public class LoginPresenter extends BasePresenter<LoginView<LoginBean>> {


    public LoginPresenter(LoginView<LoginBean> mView) {
        attachView(mView);
    }


    public void login(BaseImpl baseImpl) {
        LoginModel.getInstance().login(getView().getData(), new MyBaseObserver<LoginBean>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(LoginBean data) {
                getView().onRequestSuccessData(data);
            }
        });
    }
}
