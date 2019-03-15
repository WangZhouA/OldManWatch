package com.saiyi.oldmanwatch.module.scale;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.adapter.AbsBaseAdapter;
import com.saiyi.oldmanwatch.adapter.BobyUserListAdapter;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.entity.bodyUserListBean;
import com.saiyi.oldmanwatch.helper.RetrofitService;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpRequestCallback;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpUtils;
import com.saiyi.oldmanwatch.module.main.BindAndUnBindActivity;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.view.recyclerview.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 最帅的男人 on 2019/2/14.
 */
public class ScanInfoActivity extends BaseAppCompatActivity implements HttpRequestCallback{


    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    @BindView(R.id.rl_user_manger)
    RelativeLayout rlUserManger;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.lcyview)
    RecyclerView lcyview;
    @BindView(R.id.rl_add_user)
    RelativeLayout rlAddUser;
    @BindView(R.id.rl_device_info)
    RelativeLayout rlDeviceInfo;
    @BindView(R.id.rl_bind_device)
    RelativeLayout rlBindDevice;

    private BobyUserListAdapter bobyUserListAdapter;


    private String  mac ="";

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case 1:

                    bobyUserListAdapter.setListData(dateUserBean);

                    break;

            }

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_user;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {


        mac =getIntent().getStringExtra("mac");

        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarRight.setBackgroundResource(R.mipmap.bianji);
        tvBarTitle.setText(getResources().getString(R.string.Body_fat_said));


        bobyUserListAdapter =new BobyUserListAdapter(this,R.layout.item_user_list);
        lcyview.setLayoutManager(new LinearLayoutManager(this));
        lcyview.addItemDecoration(new SpacesItemDecoration(0, 1, Color.parseColor("#CBCBCB")));
        lcyview.setAdapter(bobyUserListAdapter);

        HttpUtils.getInstance(this).getSyncHttp(0X01, RetrofitService.QUERY_USER_LIST+ SharedPreferencesHelper.get("uid", -1),this);


        bobyUserListAdapter.setOnItemClickListener(new AbsBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object bean, int position) {

                Intent intent =new Intent(ScanInfoActivity.this,UserScanDetails.class);
                intent.putExtra("name",dateUserBean.get(position).getName());
                intent.putExtra("sex",dateUserBean.get(position).getSex());
                intent.putExtra("birthday",dateUserBean.get(position).getBirthday());
                intent.putExtra("height",dateUserBean.get(position).getHeight());
                intent.putExtra("headUrl",dateUserBean.get(position).getHeadUrl());
                startActivity(intent);


            }
        });


    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_BarLeft, R.id.tv_BarRight, R.id.rl_user_manger, R.id.rl_add_user, R.id.rl_device_info, R.id.rl_bind_device})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_BarRight:
                break;
            case R.id.rl_user_manger:
                startActivity(new Intent(this, UserMangerActivity.class).putExtra("mac",mac));
                break;
            case R.id.rl_add_user:
                startActivity(new Intent(this, AddUserActivity.class));
                break;
            case R.id.rl_device_info:
                startActivity(new Intent(this, DevicesInfoActivity.class));
                break;
            case R.id.rl_bind_device:
                startActivity(new Intent(this, BindAndUnBindActivity.class).putExtra("mac",mac));
                break;
        }
    }


    private List<bodyUserListBean>dateUserBean = new ArrayList<>();
    private  bodyUserListBean bean;
    @Override
    public void onResponse(String sequest, int type) {
        switch (type) {
            case 0x01:

                try {
                    JSONObject object = new JSONObject(sequest);
                    JSONArray array = object.getJSONArray("data");
                    dateUserBean.clear();
                    for (int i = 0; i < array.length(); i++) {
                        bean = new bodyUserListBean();
                        JSONObject ob = array.getJSONObject(i);
                        bean.setName(ob.getString("name"));
                        Log.e("http name", ob.getString("name"));
                        Log.e("http url", ob.getString("headUrl"));
                        bean.setHeadUrl(ob.getString("headUrl"));
                        bean.setSex(ob.getString("sex"));
                        bean.setBirthday(ob.getString("birthday"));
                        bean.setHeight(ob.getString("height"));
                        dateUserBean.add(bean);
                    }

                    mHandler.sendEmptyMessageDelayed(1, 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        }

    @Override
    public void onFailure(String exp) {

    }
}
