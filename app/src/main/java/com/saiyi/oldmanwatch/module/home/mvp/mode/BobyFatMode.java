package com.saiyi.oldmanwatch.module.home.mvp.mode;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.BodyTrend;
import com.saiyi.oldmanwatch.entity.bodyUserListBean;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by 最帅的男人 on 2019/2/21.
 */
public class BobyFatMode extends BaseModel {

    @SuppressLint("NewApi")
    public static BobyFatMode getInstance() {
        return getPresent(BobyFatMode.class);
    }

    public void queryDeviceUserInfoDate(int uid, MyBaseObserver<BodyTrend> observer) {
        Observable observable = mServletApi.queryBodyTrend(uid);
        toSubscribe(observable, observer);
    }


    public void queryUserList(int uid, String token, Observer<List<bodyUserListBean>> observer) {
        Observable observable = mServletApi.queryUserList(uid, token);
        toSubscribe(observable, observer);
    }
}
