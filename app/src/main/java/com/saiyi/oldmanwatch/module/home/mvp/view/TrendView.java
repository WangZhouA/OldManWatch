package com.saiyi.oldmanwatch.module.home.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.QueryTrendBena;

/**
 * Created by 最帅的男人 on 2019/2/22.
 */
public interface TrendView<T> extends BaseRequestContract<T> {

    QueryTrendBena getData(String startDate, String type, int uid );

    String getToken();

}
