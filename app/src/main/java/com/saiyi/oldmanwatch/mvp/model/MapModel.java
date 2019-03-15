package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.ControlDeviceBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;


import io.reactivex.Observable;
import io.reactivex.Observer;

public class MapModel extends BaseModel {
    @SuppressLint("NewApi")
    public static MapModel getInstance() {
        return getPresent(MapModel.class);
    }

    public void sendCmDevice(ControlDeviceBean data, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.sendCmDevice(data);
        toSubscribe(observable, observer);
    }
}
