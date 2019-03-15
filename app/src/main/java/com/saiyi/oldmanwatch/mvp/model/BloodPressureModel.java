package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.Hearts;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class BloodPressureModel extends BaseModel {
    @SuppressLint("NewApi")
    public static BloodPressureModel getInstance() {
        return getPresent(BloodPressureModel.class);
    }

    public void execute(String mac, Observer<Hearts> observer) {
        Observable observable = mServletApi.getHearts(mac).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
