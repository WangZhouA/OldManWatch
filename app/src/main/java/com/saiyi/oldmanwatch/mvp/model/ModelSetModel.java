package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.ModelSetBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class ModelSetModel extends BaseModel {
    @SuppressLint("NewApi")
    public static ModelSetModel getInstance() {
        return getPresent(ModelSetModel.class);
    }

    public void updateModelSet(ModelSetBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.updateModelSet(data, token);
        toSubscribe(observable, observer);
    }
}
