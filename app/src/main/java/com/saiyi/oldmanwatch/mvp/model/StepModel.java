package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.QueryStepBean;
import com.saiyi.oldmanwatch.entity.StepBean;
import com.saiyi.oldmanwatch.http.HttpFunction;
import com.saiyi.oldmanwatch.utils.Logger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class StepModel extends BaseModel {
    @SuppressLint("NewApi")
    public static StepModel getInstance() {
        return getPresent(StepModel.class);
    }

    public void execute(String mac, String startTime, String endTime, Observer<StepBean> observer) {
        addParams("mac", mac);
        addParams("startDate", startTime);
        addParams("endDate", endTime);
        QueryStepBean data = new QueryStepBean();
        data.setMac(mac);
        data.setStartDate(startTime);
        data.setEndDate(endTime);
        Observable observable = mServletApi.getStep(data).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
