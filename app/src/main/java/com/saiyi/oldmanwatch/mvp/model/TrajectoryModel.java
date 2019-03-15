package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.QueryHeartsList;
import com.saiyi.oldmanwatch.entity.Trajectory;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class TrajectoryModel extends BaseModel {
    @SuppressLint("NewApi")
    public static TrajectoryModel getInstance() {
        return getPresent(TrajectoryModel.class);
    }

    public void getTraJectory(QueryHeartsList data, String token, Observer<List<Trajectory>> observer) {
        Observable observable = mServletApi.getTraJectory(data, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
