package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.AddOpinionBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MultipartBody;

public class FeedbackModel extends BaseModel {
    @SuppressLint("NewApi")
    public static FeedbackModel getInstance() {
        return getPresent(FeedbackModel.class);
    }

    public void upLoadImg(MultipartBody.Part parts, Observer<String> observer) {
        Observable observable = mServletApi.uploadImg(parts).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void addOpinion(AddOpinionBean data, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.addOpinion(data);
        toSubscribe(observable, observer);
    }
}
