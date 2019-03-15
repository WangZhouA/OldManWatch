package com.saiyi.oldmanwatch.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/10/23
 * @Describe 绑定设备结果页面
 */
public class BoundResultActivity extends BaseAppCompatActivity {

    @BindView(R.id.img_back)
    ImageView ivBack;
    @BindView(R.id.ll_home)
    RelativeLayout llHome;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private String code;
    private Context mContext;
    private int type;
    private String mac;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bound_result;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
    }

    @Override
    protected void initData() {
        code = getIntent().getStringExtra("code");
        type = getIntent().getIntExtra("type", 0);
        mac = getIntent().getStringExtra("mac");
        if ("1002".equals(code)) {
            tvHint.setText(getResources().getString(R.string.hint_bind_fail));
            llHome.setBackgroundColor(getResources().getColor(R.color.red));
            ivPic.setImageResource(R.mipmap.prompt2);
            tvConfirm.setText(getResources().getString(R.string.re_binding));
        } else if ("1000".equals(code)) {
            tvHint.setText(getResources().getString(R.string.hint_bind_success));
            llHome.setBackgroundColor(getResources().getColor(R.color.appColor));
            ivPic.setImageResource(R.mipmap.prompt1);
            SharedPreferencesHelper.put("mac", mac);
            tvConfirm.setText(getResources().getString(R.string.start_to_use));
        } else {
            tvHint.setText(getResources().getString(R.string.hint_bind_fail));
            llHome.setBackgroundColor(getResources().getColor(R.color.red));
            ivPic.setImageResource(R.mipmap.prompt2);
            tvConfirm.setText(getResources().getString(R.string.re_binding));
        }
    }

    @OnClick({R.id.img_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_confirm:
                if ("1001".equals(code)) {
                    intent.setClass(mContext, BindingDeviceActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    if (type == 1) {
                        intent.setClass(mContext, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (type == 2) {
                        intent.setClass(mContext, PositionerMainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        intent.setClass(mContext, MainTitleActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
                break;
        }
    }
}
