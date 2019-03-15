package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.ControlDeviceBean;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class PositionModel extends BaseModel {
    @SuppressLint("NewApi")
    public static PositionModel getInstance() {
        return getPresent(PositionModel.class);
    }

    public void queryDeviceList(int uid, String token, Observer<List<QueryDeviceList>> observer) {
        Observable observable = mServletApi.queryDeviceList(uid, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void sendCmDevice(ControlDeviceBean data, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.sendCmDevice(data);
        toSubscribe(observable, observer);
    }
}
