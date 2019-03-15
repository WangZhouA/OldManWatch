package com.saiyi.oldmanwatch.module.home.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.BodyTrend;

/**
 * Created by 最帅的男人 on 2019/2/21.
 */
public interface BobyFatView<T> extends BaseRequestContract<T> {

    int uid();

    void getDevices(BodyTrend list);

    String token();

}
