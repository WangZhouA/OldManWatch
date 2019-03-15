package com.saiyi.oldmanwatch.mvp.presenter;

import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.UserMangerBean;
import com.saiyi.oldmanwatch.mvp.view.UserMangerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 最帅的男人 on 2019/2/13.
 */
public class UserMangerPresent extends BasePresenter<UserMangerView<List<UserMangerBean>>> {

    private  List<UserMangerBean> mDate;


    public UserMangerPresent(UserMangerView<List<UserMangerBean>> mView) {
        attachView(mView);
    }


    public List<UserMangerBean> getDateList(){
        mDate = new ArrayList<>();
        mDate.add(new UserMangerBean("张三",1,"123"));
        mDate.add(new UserMangerBean("李四",2,"234"));
        mDate.add(new UserMangerBean("王五",2,"456"));
        mDate.add(new UserMangerBean("赵麻子",2,"789"));
        return mDate;

    }
}
