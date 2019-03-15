package com.saiyi.oldmanwatch.mvp.view;

import com.saiyi.oldmanwatch.base.BaseRequestContract;
import com.saiyi.oldmanwatch.entity.ContactsBean;

/**
 * @Author liwenbo
 * @Date 18/10/24
 * @Describe
 */
public interface TelephoneBookView<T> extends BaseRequestContract<T> {
    void getSuccess(String code);

    ContactsBean getContacts();

    String getToken();

    String getMac();
}
