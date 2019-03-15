package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.entity.UserBean;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class MeModel extends BaseModel {
    @SuppressLint("NewApi")
    public static MeModel getInstance() {
        return getPresent(MeModel.class);
    }

    public void queryDeviceList(int uid, String token, Observer<List<QueryDeviceList>> observer) {
        Observable observable = mServletApi.queryDeviceList(uid, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void getUser(String uid, String token, Observer<UserBean> observer) {
        Observable observable = mServletApi.queryUser(uid, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
