package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.AddDeviceBean;
import com.saiyi.oldmanwatch.entity.QueryDevice;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class BindingDeviceModel extends BaseModel {
    @SuppressLint("NewApi")
    public static BindingDeviceModel getInstance() {
        return getPresent(BindingDeviceModel.class);
    }

    public void addDevice(AddDeviceBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.addDevice(data, token);
        toSubscribe(observable, observer);
    }

    public void queryDevice(String mac, String token, Observer<QueryDevice> observer) {
        Observable observable = mServletApi.queryDevice(mac, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
