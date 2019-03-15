package com.saiyi.oldmanwatch.module.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.DeviceinfoList;
import com.saiyi.oldmanwatch.entity.UpdateDeviceState;
import com.saiyi.oldmanwatch.mvp.presenter.FamilyMemberPresenter;
import com.saiyi.oldmanwatch.mvp.view.FamilyMemberView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.SwipeItemLayout;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/11/3
 * @Describe
 */
public class FamilyMemberActivity extends BaseMvpAppCompatActivity<FamilyMemberPresenter> implements FamilyMemberView<List<DeviceinfoList>> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.rv_family)
    RecyclerView mRecyclerView;

    private Context mContext;
    private String mac;
    private String token;
    private CommonAdapter<DeviceinfoList> mAdapter;
    private List<DeviceinfoList> mDatas = new ArrayList<>();
    private String type;
    private int id;

    @Override
    protected int getLayoutId() {
        type = getIntent().getStringExtra("type");
        if ("2".equals(type))
            return R.layout.activity_family_member_position;
        else
            return R.layout.activity_family_member;

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(R.string.family_member);
    }

    @Override
    protected FamilyMemberPresenter createPresenter() {
        return new FamilyMemberPresenter(this);
    }

    @Override
    protected void initData() {
        mac = BaseApplication.mac;
        token = (String) SharedPreferencesHelper.get("token", "");
        mPresenter.getDeviceList(this);
    }

    @OnClick({R.id.tv_BarLeft})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;

        }
    }

    @Override
    public void getSuccess(String code) {
        if ("1000".equals(code)) {
            ToastUtils.show(mContext, "成功！", ToastUtils.LENGTH_LONG);
            finish();
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getMac() {
        return mac;
    }

    @Override
    public UpdateDeviceState getData() {
        UpdateDeviceState data = new UpdateDeviceState();
        data.setId(getId());
        data.setState("1");
        return data;
    }


    @Override
    public void onRequestSuccessData(List<DeviceinfoList> data) {
        mDatas.clear();
        mDatas.addAll(data);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
        mAdapter = new CommonAdapter<DeviceinfoList>(mContext, R.layout.item_family_member, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final DeviceinfoList dataBean, int position) {
                if ("1".equals(dataBean.getState()))
                    holder.setVisible(R.id.tv_confirm, false);
                else holder.setVisible(R.id.tv_confirm, true);
                holder.setText(R.id.tv_title, "请求添加" + dataBean.getDeviceName());
                holder.setText(R.id.tv_phone_num, dataBean.getPhone());
                holder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        id = dataBean.getId();
                        mPresenter.delDevice(FamilyMemberActivity.this);
                    }
                });
                holder.setOnClickListener(R.id.tv_confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ("0".equals(dataBean.getState())) {
                            id = dataBean.getId();
                            mPresenter.updateState(FamilyMemberActivity.this);
                        }
                    }
                });

            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }
}
