package com.saiyi.oldmanwatch.module.scale;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.adapter.UserMangerAdapter;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.UserMangerMacBean;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpRequestCallback;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpUtils;
import com.saiyi.oldmanwatch.listener.MyOnSetItemListeners;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.saiyi.oldmanwatch.helper.RetrofitService.QUERY_DEVICE_USER_LIST;

/**
 * Created by 最帅的男人 on 2019/2/13.
 */
public class UserMangerActivity extends BaseMvpAppCompatActivity implements HttpRequestCallback,MyOnSetItemListeners {


    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.ll_toolbar)
    LinearLayout llToolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    UserMangerAdapter userMangerAdapter ;

    String mac="";

    private  boolean flag; // 是哪个界面进来的

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case 1:

                    userMangerAdapter.setListData(mDateList);

                    break;
            }

        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_management;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(getResources().getString(R.string.people_manger));

    }

    @Override
    protected void initData() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        userMangerAdapter = new UserMangerAdapter(this);
        userMangerAdapter.setMyOnSetItemListeners(this);
        recyclerView.setAdapter(userMangerAdapter);
        mac = getIntent().getStringExtra("mac");
        if (getIntent().getIntExtra("T",-1)!=1){
            flag=false;
        }else {
            flag=true;
        }


        okHttpList();



    }

    private void okHttpList() {
        HttpUtils.getInstance(this).getSyncHttp(0x01,QUERY_DEVICE_USER_LIST+ mac,this);
    }


    @OnClick(R.id.tv_BarLeft)
    public void onViewClicked() {
        finish();
    }


    private List<UserMangerMacBean.DataBean> mDateList = new ArrayList<>();
    UserMangerMacBean bean ;
    @Override
    public void onResponse(String sequest, int type) {
        switch (type){

            case 0x01:
                Gson gson =new Gson();
                mDateList.clear();
                bean = gson.fromJson(sequest,UserMangerMacBean.class);
                mDateList.addAll(bean.getData());
                mHandler.sendEmptyMessageDelayed(1,0);
                break;
        }
    }

    @Override
    public void onFailure(String exp) {

    }

    @Override
    public void MyOnSetItemListeners(UserMangerMacBean.DataBean dataBean, int position) {
        if (flag) {
            Intent intent = new Intent();
            intent.putExtra("name", dataBean.getName()); //将计算的值回传
            intent.putExtra("id",dataBean.getUid()+"");
            setResult(2, intent);
            finish(); //结束当前的activity的生命周期
        }
    }
}
