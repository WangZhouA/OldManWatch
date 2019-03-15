package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.SwitchSetBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class SMSSetModel extends BaseModel {
    @SuppressLint("NewApi")
    public static SMSSetModel getInstance() {
        return getPresent(SMSSetModel.class);
    }

    public void updateSetup(SwitchSetBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.updateSetup(data, token);
        toSubscribe(observable, observer);
    }
}
