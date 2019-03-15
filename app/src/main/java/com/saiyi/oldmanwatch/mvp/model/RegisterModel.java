package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.RequestRegister;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class RegisterModel extends BaseModel {
    @SuppressLint("NewApi")
    public static RegisterModel getInstance() {
        return getPresent(RegisterModel.class);
    }

    public void register(RequestRegister data, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.register(data);
        toSubscribe(observable, observer);
    }

    public void queryPhone(String phone, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.queryPhone(phone);
        toSubscribe(observable, observer);
    }

    public void getCode(String phone, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.getCode(phone);
        toSubscribe(observable, observer);
    }

}
