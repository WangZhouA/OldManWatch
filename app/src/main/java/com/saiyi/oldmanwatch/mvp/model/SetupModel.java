package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.AddSetupBean;
import com.saiyi.oldmanwatch.entity.SetupBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class SetupModel extends BaseModel {
    @SuppressLint("NewApi")
    public static SetupModel getInstance() {
        return getPresent(SetupModel.class);
    }

    public void getSetupList(String mac, String token, Observer<List<SetupBean>> observer) {
        Observable observable = mServletApi.querySetupList(mac, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void addSetup(AddSetupBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.addSetup(data, token);
        toSubscribe(observable, observer);
    }
}
