package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.LoginBean;
import com.saiyi.oldmanwatch.entity.RequestLogin;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class LoginModel extends BaseModel {
    @SuppressLint("NewApi")
    public static LoginModel getInstance() {
        return getPresent(LoginModel.class);
    }

    public void login(RequestLogin data, Observer<LoginBean> observer) {
        Observable observable = mServletApi.login(data).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
