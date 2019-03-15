package com.saiyi.oldmanwatch.listener;

import com.saiyi.oldmanwatch.entity.bodyUserListBean;

import java.util.List;

/**
 * Created by 最帅的男人 on 2019/3/5.
 */
public interface OnSectorListener {

    void  setActivitySectorListener(List<bodyUserListBean> mData);

    void  setAdapterSectorListener();
}
