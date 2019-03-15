package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.AddSosBean;
import com.saiyi.oldmanwatch.entity.Enclosures;
import com.saiyi.oldmanwatch.entity.UpdateEnclosureBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class EnclosureModel extends BaseModel {
    @SuppressLint("NewApi")
    public static EnclosureModel getInstance() {
        return getPresent(EnclosureModel.class);
    }

    public void updateEnclosure(UpdateEnclosureBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.updateEnclosure(data, token);
        toSubscribe(observable, observer);
    }

    public void getEnclosures(String mac, Observer<Enclosures> observer) {
        Observable observable = mServletApi.getEnclosures(mac).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
