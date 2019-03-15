package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.UserMangerBean;

/**
 * Created by 最帅的男人 on 2019/2/13.
 */
public interface UserMangerView<T> extends BaseRequestContract<T> {

     UserMangerBean getDate();

}
