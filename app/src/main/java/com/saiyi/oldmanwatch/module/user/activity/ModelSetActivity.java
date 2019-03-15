package com.saiyi.oldmanwatch.module.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.ModelSetBean;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.mvp.presenter.ModelSetPresenter;
import com.saiyi.oldmanwatch.mvp.view.ModelSetView;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class ModelSetActivity extends BaseMvpAppCompatActivity<ModelSetPresenter> implements ModelSetView<BaseResponse> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.rd_normal)
    RadioButton rdNormal;
    @BindView(R.id.rd_power_saving)
    RadioButton rdPowerSaving;
    @BindView(R.id.rd_following)
    RadioButton rdFollowing;
    @BindView(R.id.rd_ring_bell)
    RadioButton rdRingBell;
    @BindView(R.id.rd_ring_bell_and_shock)
    RadioButton rdRBAS;
    @BindView(R.id.rd_shock)
    RadioButton rdShock;
    @BindView(R.id.rd_Mute)
    RadioButton rdMute;

    private Context mContext;
    private String mac;
    private String token;
    private String workPattern;
    private String scenePattern;
    private String type;

    @Override
    protected int getLayoutId() {
        type = getIntent().getStringExtra("type");
        if ("2".equals(type))
            return R.layout.activity_position_model_set;
        else
            return R.layout.activity_model_set;

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(R.string.situational_mode);
        tvBarRight.setText(R.string.save);
        mContext = this;
    }

    @Override
    protected ModelSetPresenter createPresenter() {
        return new ModelSetPresenter(this);
    }

    @Override
    protected void initData() {
        mac = BaseApplication.mac;
        token = (String) SharedPreferencesHelper.get("token", "");
        workPattern = (String) SharedPreferencesHelper.get("work", "1");
        scenePattern = (String) SharedPreferencesHelper.get("scene", "1");
        switch (workPattern) {
            case "1":
                rdNormal.setChecked(true);
                break;
            case "2":
                rdPowerSaving.setChecked(true);
                break;
            case "3":
                rdFollowing.setChecked(true);
                break;
        }
        switch (scenePattern) {
            case "1":
                rdRingBell.setChecked(true);
                break;
            case "2":
                rdRBAS.setChecked(true);
                break;
            case "3":
                rdShock.setChecked(true);
                break;
            case "4":
                rdMute.setChecked(true);
                break;
        }


    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_BarRight, R.id.rd_normal, R.id.rd_power_saving, R.id.rd_following,
            R.id.rd_ring_bell, R.id.rd_ring_bell_and_shock, R.id.rd_shock, R.id.rd_Mute})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_BarRight:
                mPresenter.updateModelSet(this);
                break;
            case R.id.rd_normal:
                workPattern = "1";
                break;
            case R.id.rd_power_saving:
                workPattern = "2";
                break;
            case R.id.rd_following:
                workPattern = "3";
                break;
            case R.id.rd_ring_bell:
                scenePattern = "1";
                break;
            case R.id.rd_ring_bell_and_shock:
                scenePattern = "2";
                break;
            case R.id.rd_shock:
                scenePattern = "3";
                break;
            case R.id.rd_Mute:
                scenePattern = "4";
                break;
        }
    }

    @Override
    public ModelSetBean getData() {
        ModelSetBean data = new ModelSetBean();
        data.setMac(mac);
        data.setWorkPattern(workPattern);
        data.setScenePattern(scenePattern);
        return data;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void onRequestSuccessData(BaseResponse data) {
        if ("1000".equals(data.getCode())) {
            ToastUtils.show(mContext, "设置成功", ToastUtils.LENGTH_LONG);
            finish();
        }
    }
}
