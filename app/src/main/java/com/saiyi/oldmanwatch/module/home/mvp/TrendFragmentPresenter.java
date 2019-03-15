package com.saiyi.oldmanwatch.module.home.mvp;

import android.util.Log;

import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.WeightTrends;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.module.home.mvp.mode.TrendMode;
import com.saiyi.oldmanwatch.module.home.mvp.view.TrendView;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/22.
 */
public class TrendFragmentPresenter extends BasePresenter<TrendView<List<WeightTrends>>> {

    public TrendFragmentPresenter(TrendView<List<WeightTrends>> mView) {
        attachView(mView);
    }

    public void queryWeightTrendPresenter(BaseImpl baseImpl,String startDate,String type,int uid) {
        TrendMode.getInstance().queryDeviceDate(getView().getData(startDate,type,uid), getView().getToken(),new MyBaseObserver<List<WeightTrends>>(baseImpl, "正在加载...") {
            @Override
                protected void onBaseNext(List<WeightTrends> data) {
                    if (data!=null) {
                        getView().onRequestSuccessData(data);
                    }else {
                        Log.e("http","空的");
                    }
            }
        });
    }

}
