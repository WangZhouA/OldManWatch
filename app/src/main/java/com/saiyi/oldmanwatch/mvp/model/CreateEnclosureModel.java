package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.AddEnclosure;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class CreateEnclosureModel extends BaseModel {
    @SuppressLint("NewApi")
    public static CreateEnclosureModel getInstance() {
        return getPresent(CreateEnclosureModel.class);
    }

    public void addEnclosure(AddEnclosure data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.addEnclosure(data, token);
        toSubscribe(observable, observer);
    }

}
