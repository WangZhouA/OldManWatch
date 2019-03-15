package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.DeviceinfoList;
import com.saiyi.oldmanwatch.entity.UpdateDeviceState;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class FamilyMemberModel extends BaseModel {
    @SuppressLint("NewApi")
    public static FamilyMemberModel getInstance() {
        return getPresent(FamilyMemberModel.class);
    }

    public void getDeviceList(String mac, String token, Observer<List<DeviceinfoList>> observer) {
        Observable observable = mServletApi.getDeviceList(mac, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void updateState(UpdateDeviceState data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.updateState(data, token);
        toSubscribe(observable, observer);
    }

    public void delDevice(String token, int id, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.delDevice(token, id);
        toSubscribe(observable, observer);
    }
}
