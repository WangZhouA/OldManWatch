package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.UpdateUserBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MultipartBody;

public class PerfectPersonalModel extends BaseModel {
    @SuppressLint("NewApi")
    public static PerfectPersonalModel getInstance() {
        return getPresent(PerfectPersonalModel.class);
    }

    public void upLoadImg(MultipartBody.Part parts, Observer<String> observer) {
        Observable observable = mServletApi.uploadImg(parts).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void updateUser(UpdateUserBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.updateUser(data, token);
        toSubscribe(observable, observer);
    }
}
