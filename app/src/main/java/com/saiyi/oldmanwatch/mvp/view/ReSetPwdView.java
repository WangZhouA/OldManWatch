package com.saiyi.oldmanwatch.mvp.view;


import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.UpdatePWD;

/**
 * @Author liwenbo
 * @Date 18/10/31
 * @Describe
 */
public interface ReSetPwdView<T> extends BaseRequestContract<T> {

    UpdatePWD getData();

    String getPhone();
}
