package com.saiyi.oldmanwatch.mvp.presenter;


import com.saiyi.oldmanwatch.mvp.view.IView;

/**
 * @Author LiWenBo
 * @Date 2018/7/27
 * @Describe MVP Presenter基接口
 * @Version 1.0
 */
public interface IPresenter {

    void onCreate();

    void onStop();

    void attachView(IView view);
}
