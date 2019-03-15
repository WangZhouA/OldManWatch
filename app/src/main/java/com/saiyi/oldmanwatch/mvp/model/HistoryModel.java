package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.HeartsList;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class HistoryModel extends BaseModel {
    @SuppressLint("NewApi")
    public static HistoryModel getInstance() {
        return getPresent(HistoryModel.class);
    }

    public void execute(String mac, String startTime, String endTime, Observer<List<HeartsList>> observer) {

        Observable observable = mServletApi.queryHeartsList(mac, startTime, endTime).map(new HttpFunction());

        toSubscribe(observable, observer);
    }
}
