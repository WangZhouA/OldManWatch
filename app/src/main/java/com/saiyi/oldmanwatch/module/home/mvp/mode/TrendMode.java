package com.saiyi.oldmanwatch.module.home.mvp.mode;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.QueryTrendBena;
import com.saiyi.oldmanwatch.entity.WeightTrends;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 最帅的男人 on 2019/2/22.
 */
public class TrendMode extends BaseModel {

    @SuppressLint("NewApi")
    public static TrendMode getInstance() {
        return getPresent(TrendMode.class);
    }

    public void queryDeviceDate(QueryTrendBena date, String token, MyBaseObserver<List<WeightTrends>> observer) {
        Observable observable = mServletApi.queryWeighetChart(date,token);
        toSubscribe(observable, observer);
    }


//    public void queryDeviceDate(QueryTrendBena date, String token,Observer<List<WeightTrends>> observer) {
//        Observable observable = mServletApi.queryWeighetChart(date,token);
//        toSubscribe(observable, observer);
//    }
}
