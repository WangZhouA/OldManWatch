package com.saiyi.oldmanwatch.module.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.SwitchSetBean;
import com.saiyi.oldmanwatch.mvp.presenter.SMSSetPresenter;
import com.saiyi.oldmanwatch.mvp.view.SMSSetView;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class SMSSettingActivity extends BaseMvpAppCompatActivity<SMSSetPresenter> implements SMSSetView<String> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvTitle;
    @BindView(R.id.cb_sms_set)
    CheckBox cbSMSSet;
    @BindView(R.id.cb_Battery_set)
    CheckBox cbBatterySet;

    private Context mContext;
    private String mac;
    private String token;
    private int electricState;
    private int sosState;
    private String SwitchSetType = "";
    private String SwitchSetState = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sms_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvTitle.setText(R.string.set_sms);
        mContext = this;
    }

    @Override
    protected SMSSetPresenter createPresenter() {
        return new SMSSetPresenter(this);
    }

    @Override
    protected void initData() {
        mac = BaseApplication.mac;
        token = (String) SharedPreferencesHelper.get("token", "");
        electricState = (int) SharedPreferencesHelper.get(" elect", 0);
        sosState = (int) SharedPreferencesHelper.get("sos", 0);
        cbBatterySet.setChecked(electricState == 1 ? true : false);
        cbSMSSet.setChecked(sosState == 1 ? true : false);
    }

    @OnClick({R.id.tv_BarLeft, R.id.cb_sms_set, R.id.cb_Battery_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.cb_sms_set:
                if (cbSMSSet.isChecked())
                    SwitchSetState = "1";
                else SwitchSetState = "0";
                SwitchSetType = "SOSSMS";
                mPresenter.updateSetup(this);
                break;
            case R.id.cb_Battery_set:
                if (cbBatterySet.isChecked())
                    SwitchSetState = "1";
                else SwitchSetState = "0";
                SwitchSetType = "LOWBAT";
                mPresenter.updateSetup(this);
                break;
        }
    }


    @Override
    public SwitchSetBean getData() {
        SwitchSetBean data = new SwitchSetBean();
        data.setMac(mac);
        data.setState(SwitchSetState);
        data.setType(SwitchSetType);
        return data;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void onRequestSuccessData(String data) {
        if ("1000".equals(data))
            ToastUtils.show(mContext, "设置成功", ToastUtils.LENGTH_LONG);
        else
            ToastUtils.show(mContext, "设置失败", ToastUtils.LENGTH_LONG);
    }
}
