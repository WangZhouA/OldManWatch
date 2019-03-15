package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.DeviceDetailsBean;
import com.saiyi.oldmanwatch.entity.QueryDeviceDetailsBean;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class HomeModel extends BaseModel {
    @SuppressLint("NewApi")
    public static HomeModel getInstance() {
        return getPresent(HomeModel.class);
    }

    public void queryDeviceList(int uid, String token, Observer<List<QueryDeviceList>> observer) {
        Observable observable = mServletApi.queryDeviceList(uid, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void getDeviceDetails(QueryDeviceDetailsBean data, String token, Observer<DeviceDetailsBean> observer) {
        Observable observable = mServletApi.queryDeviceDetails(data, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
