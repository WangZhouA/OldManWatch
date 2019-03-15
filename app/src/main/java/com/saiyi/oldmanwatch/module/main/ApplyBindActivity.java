package com.saiyi.oldmanwatch.module.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.RequestApplyBind;
import com.saiyi.oldmanwatch.mvp.presenter.ApplyBindPresenter;
import com.saiyi.oldmanwatch.mvp.view.ApplyBindView;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/10/23
 * @Describe 申请绑定设备
 */
public class ApplyBindActivity extends BaseMvpAppCompatActivity<ApplyBindPresenter> implements ApplyBindView<String> {

    @BindView(R.id.tv_bound_user)
    TextView tvBoundUser;
    @BindView(R.id.civ_pic)
    CircleImageView civPic;
    @BindView(R.id.tv_device_id)
    TextView tvDeviceID;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_apply)
    TextView tvApply;

    private Context mContext;
    private String token;
    private int uid;
    private String mac;
    private String relationName;
    private String filiation;
    private String phone;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_bind;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
    }

    @Override
    protected ApplyBindPresenter createPresenter() {
        return new ApplyBindPresenter(this);
    }

    @Override
    protected void initData() {
        token = (String) SharedPreferencesHelper.get("token", "");
        uid = (int) SharedPreferencesHelper.get("uid", -1);
        phone = getIntent().getStringExtra("phone");
        mac = getIntent().getStringExtra("mac");
        filiation = getIntent().getStringExtra("filiation");
        relationName = getIntent().getStringExtra("relationName");
        tvDeviceID.setText(mac);





    }

    @OnClick({R.id.tv_cancel, R.id.tv_apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_apply:
                mPresenter.applyBind(this);
                break;
        }
    }


    @Override
    public RequestApplyBind getBindData() {
        RequestApplyBind data = new RequestApplyBind();
        data.setFiliation(filiation);
        data.setMac(mac);
        data.setRelationName(relationName);
        data.setUid(uid);
        data.setType(type);
        return data;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void onRequestSuccessData(String data) {
        Logger.e("ssss", "data....." + data);
        switch (data) {
            case "1000":
                ToastUtils.show(mContext, "操作成功", ToastUtils.LENGTH_LONG);
                finish();
                break;
            case "1002":
                ToastUtils.show(mContext, "操作失败", ToastUtils.LENGTH_LONG);
                finish();
                break;
        }
    }
}
