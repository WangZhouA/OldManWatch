package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.DelDevice;
import com.saiyi.oldmanwatch.entity.DeviceState;
import com.saiyi.oldmanwatch.entity.SwitchSetBean;
import com.saiyi.oldmanwatch.entity.UpdateDeviceBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MultipartBody;

public class MyDeviceModel extends BaseModel {
    @SuppressLint("NewApi")
    public static MyDeviceModel getInstance() {
        return getPresent(MyDeviceModel.class);
    }

    public void upLoadImg(MultipartBody.Part parts, Observer<String> observer) {
        Observable observable = mServletApi.uploadImg(parts).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void delMyDevice(DelDevice data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.delMyDevice(data, token);
        toSubscribe(observable, observer);
    }

    public void updateSetup(SwitchSetBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.updateSetup(data, token);
        toSubscribe(observable, observer);
    }

    public void updateDevice(UpdateDeviceBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.updateDevice(data, token);
        toSubscribe(observable, observer);
    }

    public void getDeviceState(String mac, Observer<DeviceState> observer) {
        Observable observable = mServletApi.getDeviceState(mac).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

}
