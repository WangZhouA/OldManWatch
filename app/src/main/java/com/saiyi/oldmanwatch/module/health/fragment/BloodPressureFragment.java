package com.saiyi.oldmanwatch.module.health.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpFragment;
import com.saiyi.oldmanwatch.entity.Hearts;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.module.health.activity.HistoryActivity;
import com.saiyi.oldmanwatch.module.health.activity.TestingActivity;
import com.saiyi.oldmanwatch.mvp.presenter.BloodPressurePresenter;
import com.saiyi.oldmanwatch.mvp.view.BloodPressureView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.view.DHealthyProgressView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/10/6
 * @Describe 血压心率检测
 */
public class BloodPressureFragment extends BaseMvpFragment<BloodPressurePresenter> implements BloodPressureView<Hearts> {
    @BindView(R.id.iv_history)
    ImageView ivHistory;
    @BindView(R.id.simple)
    DHealthyProgressView mSimple;
    @BindView(R.id.tv_bmp)
    TextView tvBmp;
    @BindView(R.id.tv_hint_test)
    TextView tvHintTest;
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;
    @BindView(R.id.tv_heart_rate_range)
    TextView tvHeartRateRange;
    @BindView(R.id.tv_hypertension_range)
    TextView tvHypertensionRange;
    @BindView(R.id.tv_hypotension_range)
    TextView tvHypotensionRange;
    @BindView(R.id.tv_testing)
    TextView tvTesting;

    private Context mContext;
    private String mac;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_blood_pressure;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getHearts(BloodPressureFragment.this);
    }

    @Override
    protected void onViewReallyCreated(View view) {
        mContext = getActivity();
        mac = CheckUtils.getMac();
        mSimple.setmValue(100);
    }

    @Override
    protected BloodPressurePresenter createPresenter() {
        return new BloodPressurePresenter(this);
    }

    @OnClick({R.id.iv_history, R.id.tv_testing})
    public void onViewClicked(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_history:
                intent.setClass(mContext, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_testing:
                intent.setClass(mContext, TestingActivity.class);
                startActivityForResult(intent, Constant.TESTCODE);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (requestCode == Constant.TESTCODE) {
            String high = data.getStringExtra("high");
            String low = data.getStringExtra("low");
            String heart = data.getStringExtra("heart");
            tvBmp.setText(heart);
            tvHeartRate.setText(high + "/" + low);
            tvHintTest.setText("检测数据");
        }
    }

    @Override
    public String getMac() {
        return mac;
    }

    @Override
    public void onRequestSuccessData(Hearts data) {
        tvBmp.setText(data.getHeart());
        tvHeartRate.setText(data.getPressure());
        tvHintTest.setText("上次检测数据");
    }
}
