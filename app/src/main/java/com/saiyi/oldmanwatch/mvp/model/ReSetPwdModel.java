package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.UpdatePWD;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class ReSetPwdModel extends BaseModel {
    @SuppressLint("NewApi")
    public static ReSetPwdModel getInstance() {
        return getPresent(ReSetPwdModel.class);
    }

    public void updatepwd(UpdatePWD data, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.updatePassword(data);
        toSubscribe(observable, observer);
    }

    public void getCode(String phone, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.getCode(phone);
        toSubscribe(observable, observer);
    }
}
