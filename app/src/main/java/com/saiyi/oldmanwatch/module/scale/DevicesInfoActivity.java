package com.saiyi.oldmanwatch.module.scale;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;

import butterknife.BindView;

public class DevicesInfoActivity extends BaseMvpAppCompatActivity implements View.OnClickListener{
    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_software_version)
    TextView tvSoftwareVersion;
    @BindView(R.id.tv_hardware_version)
    TextView tvHardwareVersion;

    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_devices_info;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarleft.setBackgroundResource(R.mipmap.back);
        tvBarleft.setOnClickListener(this);
        tvBarTitle.setText(R.string.devices_info);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tv_BarLeft:
                finish();
        }
    }
}
