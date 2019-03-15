package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.AddSosBean;
import com.saiyi.oldmanwatch.entity.RequestApplyBind;
import com.saiyi.oldmanwatch.entity.SosPhoneBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class SosModel extends BaseModel {
    @SuppressLint("NewApi")
    public static SosModel getInstance() {
        return getPresent(SosModel.class);
    }

    public void getSosList(String mac, String token, Observer<List<SosPhoneBean>> observer) {
        Observable observable = mServletApi.getSosList(mac, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void setSos(AddSosBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.setSos(data, token);
        toSubscribe(observable, observer);
    }
}
