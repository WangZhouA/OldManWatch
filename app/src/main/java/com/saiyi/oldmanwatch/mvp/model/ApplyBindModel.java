package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.RequestApplyBind;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;
import com.saiyi.oldmanwatch.http.convert.CustomGsonRequestBodyConverter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.RequestBody;

public class ApplyBindModel extends BaseModel {
    @SuppressLint("NewApi")
    public static ApplyBindModel getInstance() {
        return getPresent(ApplyBindModel.class);
    }

    public void applyBind(RequestApplyBind data, String token, Observer<Void> observer) {
        Observable observable = mServletApi.applyBind(data, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
