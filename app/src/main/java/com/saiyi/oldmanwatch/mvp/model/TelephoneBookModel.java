package com.saiyi.oldmanwatch.mvp.model;

import android.annotation.SuppressLint;

import com.saiyi.oldmanwatch.base.BaseModel;
import com.saiyi.oldmanwatch.entity.ContactsBean;
import com.saiyi.oldmanwatch.entity.ContactsListBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.http.HttpFunction;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class TelephoneBookModel extends BaseModel {
    @SuppressLint("NewApi")
    public static TelephoneBookModel getInstance() {
        return getPresent(TelephoneBookModel.class);
    }

    public void getContactsList(String mac, String token, Observer<List<ContactsListBean>> observer) {
        Observable observable = mServletApi.getContactsList(mac, token).map(new HttpFunction());
        toSubscribe(observable, observer);
    }

    public void addContacts(ContactsBean data, String token, Observer<BaseResponse> observer) {
        Observable observable = mServletApi.addContacts(data, token);
        toSubscribe(observable, observer);
    }
}
