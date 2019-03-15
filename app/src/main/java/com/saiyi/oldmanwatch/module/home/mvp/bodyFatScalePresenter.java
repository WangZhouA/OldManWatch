package com.saiyi.oldmanwatch.module.home.mvp;

import android.util.Log;

import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.BodyTrend;
import com.saiyi.oldmanwatch.entity.bodyUserListBean;
import com.saiyi.oldmanwatch.http.callback.BaseImpl;
import com.saiyi.oldmanwatch.http.callback.MyBaseObserver;
import com.saiyi.oldmanwatch.module.home.mvp.mode.BobyFatMode;
import com.saiyi.oldmanwatch.module.home.mvp.view.BobyFatView;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/21.
 */
public class bodyFatScalePresenter extends BasePresenter<BobyFatView<BodyTrend>> {

    public bodyFatScalePresenter(BobyFatView<BodyTrend> mView) {
        attachView(mView);
    }

    public void querybodyFatScalePresenter(BaseImpl baseImpl,int uid) {
        BobyFatMode.getInstance().queryDeviceUserInfoDate(uid, new MyBaseObserver<BodyTrend>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(BodyTrend data) {
                if (data.isSuccess()==true) {
                    getView().getDevices(data);
                }else {
                    Log.e("http","空的");
                }
            }
        });
    }


    public void queryUserList(BaseImpl baseImpl){
        BobyFatMode.getInstance().queryUserList(getView().uid(),getView().token(), new MyBaseObserver<List<bodyUserListBean>>(baseImpl, "正在加载...") {
            @Override
            protected void onBaseNext(List<bodyUserListBean> data) {
                Log.e("http","data"+data.get(0).getName());


            }
        });
    }

}
