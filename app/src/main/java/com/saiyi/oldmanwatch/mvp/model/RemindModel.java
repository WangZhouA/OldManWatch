package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.AddReimnd;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class RemindModel extends BaseModel {
    @SuppressLint("NewApi")
    public static RemindModel getInstance() {
        return getPresent(RemindModel.class);
    }

    public void addReimnd(AddReimnd data, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.addReimnd(data);
        toSubscribe(observable, observer);
    }
}
