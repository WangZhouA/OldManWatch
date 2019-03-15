package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.NoticeList;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class NoticeModel extends BaseModel {
    @SuppressLint("NewApi")
    public static NoticeModel getInstance() {
        return getPresent(NoticeModel.class);
    }

    public void getNoticeList(String mac, String token, Observer<List<NoticeList>> observer) {
        Observable observable = mServletApi.getNoticeList(mac, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void delNotice(String token, int id, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.delNotice(token, id);
        toSubscribe(observable, observer);
    }
}
