package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.QueryRemindList;
import com.saiyi.oldmanwatch.entity.ReimndList;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class RemindListModel extends BaseModel {
    @SuppressLint("NewApi")
    public static RemindListModel getInstance() {
        return getPresent(RemindListModel.class);
    }

    public void getRemindList(QueryRemindList data, String token, Observer<List<ReimndList>> observer) {
        Observable observable = mServletApi.queryReimndList(data, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void delRemind(int id, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.delRemind(id, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
