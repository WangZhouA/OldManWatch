package com.saiyi.oldmanwatch.module.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.AddDeviceBean;
import com.saiyi.oldmanwatch.entity.QueryDevice;
import com.saiyi.oldmanwatch.mvp.presenter.BindingDevicePresenter;
import com.saiyi.oldmanwatch.mvp.view.BindingDeviceView;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.view.CircleImageView;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/21
 * @Describe 二维码扫描绑定设备
 */
public class BindingDeviceActivity extends BaseMvpAppCompatActivity<BindingDevicePresenter> implements BindingDeviceView<QueryDevice> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.et_device_id)
    EditText etDeviceID;
    @BindView(R.id.civ_pic)
    CircleImageView civPic;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_select_relation)
    TextView tvSelectRelation;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private Context mContext;
    private static final int REQUEST_CODE_SCAN = 1;
    private static final int SELECT = 2;
    private static boolean isPermissionRequested = false;
    private String token;
    private String devicesid;
    private int uid;
    private String filiation;
    private String phone;
    private boolean ISBIND = false;
    private int type = 0;
    private String mac;


    private String deviceType ="1"; //绑定的是那一个设备类型

    @Override
    protected int getLayoutId() {
        return R.layout.activity_binding_device;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        requestPermission();
        String title = getIntent().getStringExtra("title");
        if ("老人手表".equals(title)) {
            civPic.setImageResource(R.mipmap.devices_type_watch);
            deviceType ="1";
        } else if ("定位器".equals(title)) {
            civPic.setImageResource(R.mipmap.devices_type_position);
            deviceType ="2";
        } else if ("体脂称".equals(title)) {
            civPic.setImageResource(R.mipmap.devices_type_body_fat);
            deviceType ="3";
        } else {

        }
        etDeviceID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 15) {
                    mPresenter.queryDevice(BindingDeviceActivity.this);
                }
                getEditText();
            }
        });
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getEditText();
            }
        });
    }

    @Override
    protected BindingDevicePresenter createPresenter() {
        return new BindingDevicePresenter(this);
    }

    @Override
    protected void initData() {
        token = (String) SharedPreferencesHelper.get("token", "");
        uid = (int) SharedPreferencesHelper.get("uid", -1);
    }

    @OnClick({R.id.tv_BarLeft, R.id.iv_scan, R.id.tv_select_relation, R.id.tv_confirm})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.iv_scan:
                intent.setClass(mContext, CaptureActivity.class);
                /*ZxingConfig是配置类
                 *可以设置是否显示底部布局，闪光灯，相册，
                 * 是否播放提示音  震动
                 * 设置扫描框颜色等
                 * 也可以不传这个参数
                 * */
                ZxingConfig config = new ZxingConfig();
                config.setPlayBeep(true);//是否播放扫描声音 默认为true
                config.setShake(true);//是否震动  默认为true
                config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为淡蓝色
                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
                config.setShowbottomLayout(false);
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.tv_select_relation:
                intent.setClass(mContext, RelationActivity.class);
                startActivityForResult(intent, SELECT);
                break;
            case R.id.tv_confirm:
                if (!ISBIND) {
                    mPresenter.addDevice(this);
                } else {
                    intent.setClass(mContext, ApplyBindActivity.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("mac", mac);
                    intent.putExtra("filiation", filiation);
                    intent.putExtra("relationName", etName.getText().toString());
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    public void getEditText() {
        String deviceid = etDeviceID.getText().toString();
        String name = etName.getText().toString();
        String relation = tvSelectRelation.getText().toString();
        if ("".equals(deviceid) || "".equals(name) || "".equals(relation)) {
            tvConfirm.setBackgroundResource(R.drawable.btn_gray_shape);
            tvConfirm.setClickable(false);
        } else {
            tvConfirm.setBackgroundResource(R.drawable.btn_blue_shape);
            tvConfirm.setClickable(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                etDeviceID.setText(content);
                devicesid = content;
                getEditText();
                mPresenter.queryDevice(this);
            }
        }

        if (requestCode == SELECT) {
            filiation = data.getStringExtra("name");
            tvSelectRelation.setText(getResources().getString(R.string.hint_relation) + filiation);
            getEditText();
        }
    }

    @Override
    public void getSuccess(String code) {
        Logger.e("ssss", "code....." + code);
        Intent intent = new Intent(mContext, BoundResultActivity.class);
        intent.putExtra("code", code);
        intent.putExtra("type", type);
        intent.putExtra("mac", mac);
        startActivity(intent);
        finish();
    }

    @Override
    public String getMac() {
        return etDeviceID.getText().toString();
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public AddDeviceBean getData() {
        AddDeviceBean data = new AddDeviceBean();
        data.setMac(mac);
        data.setRelationName(etName.getText().toString());
        data.setUid(uid);
        data.setFiliation(filiation);
        data.setType(deviceType);
        return data;
    }


    /**
     * Android6.0之后需要动态申请权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {
            isPermissionRequested = true;
            ArrayList<String> permissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (permissions.size() == 0) {
                return;
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            }
        }
    }


    @Override
    public void onRequestSuccessData(QueryDevice data) {
        mac = data.getMac();
        Logger.e("ssss", "data....." + data.toString());
        if (null == data.getPhone()) {
            ISBIND = false;
            type = data.getType();
        } else {
            ISBIND = true;
            phone = data.getPhone();
            type = data.getType();
            Logger.e("ssss", "type...." + type);

        }
    }
}
