package com.saiyi.oldmanwatch.module.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.AddReimnd;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.mvp.presenter.ReimndPresenter;
import com.saiyi.oldmanwatch.mvp.view.ReimndView;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.wheel.WheelView;
import com.saiyi.oldmanwatch.view.wheel.adapter.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/26
 * @Describe 添加用药提醒页面
 */
public class CreateDrugRemindingActivity extends BaseMvpAppCompatActivity<ReimndPresenter> implements ReimndView<BaseResponse> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_cycle)
    TextView tvCycle;
    @BindView(R.id.wv_hour)
    WheelView wvHour;
    @BindView(R.id.wv_minute)
    WheelView wvMinute;

    private Context mContext;
    private int cycle = 0;
    private String cycleStr = "";
    private String mac;
    private String period = "";
    private static final int SELECTCYCLE = 1;
    private ArrayList<String> hours = new ArrayList<>();
    private ArrayList<String> minutes = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_drug_reminding;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        wvHour.setItemsVisible(7);
        wvMinute.setItemsVisible(7);
        wvHour.setCurrentItem(0);
        wvMinute.setCurrentItem(0);
        mContext = this;
        tvBarleft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(getResources().getString(R.string.set_alarm));
        tvBarRight.setText(getResources().getString(R.string.save));
        hours.addAll(Arrays.asList(getResources().getStringArray(R.array.hour)));
        minutes.addAll(Arrays.asList(getResources().getStringArray(R.array.minute)));
        wvHour.setAdapter(new ArrayWheelAdapter(hours));
        wvMinute.setAdapter(new ArrayWheelAdapter(minutes));
    }

    @Override
    protected ReimndPresenter createPresenter() {
        return new ReimndPresenter(this);
    }

    @Override
    protected void initData() {
        mac = BaseApplication.mac;
    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_BarRight, R.id.tv_cycle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_BarRight:
                mPresenter.addReimnd(this);
                break;
            case R.id.tv_cycle:
                Intent intent = new Intent();
                intent.setClass(mContext, SelectCycleActivity.class);
                startActivityForResult(intent, SELECTCYCLE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (requestCode == SELECTCYCLE) {
            cycle = data.getIntExtra("cycle", 0);
            cycleStr = data.getStringExtra("data");
            period = data.getStringExtra("num");
            switch (cycle) {
                case 1:
                    tvCycle.setText("只提醒一次");
                    break;
                case 2:
                    tvCycle.setText("每天");
                    break;
                case 3:
                    tvCycle.setText("周一至周五");
                    break;
                case 4:
                    tvCycle.setText(cycleStr);
                    break;
            }
        }
    }


    @Override
    public AddReimnd getRemind() {
        String hour = hours.get(wvHour.getCurrentItem());
        String minute = minutes.get(wvMinute.getCurrentItem());
        String time = hour + ":" + minute;
        AddReimnd data = new AddReimnd();
        data.setMac(mac);
        data.setCycle(cycle + "");
        if (cycle == 4)
            data.setPeriod(period);
        else data.setPeriod("");
        data.setTitle(etTitle.getText().toString());
        data.setType("2");
        data.setRemindTime(time);
        return data;
    }

    @Override
    public void onRequestSuccessData(BaseResponse data) {
        if ("1000".equals(data.getCode())) {
            ToastUtils.show(mContext, "添加成功", ToastUtils.LENGTH_LONG);
            finish();
        }
    }
}
