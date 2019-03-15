package com.saiyi.oldmanwatch.module.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/9/26
 * @Describe 选择提醒周期
 */
public class SelectCycleActivity extends BaseAppCompatActivity {
    @BindView(R.id.tv_BarLeft)
    TextView tvBarleft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.ll_select_cycle)
    LinearLayout llSelectCycle;
    @BindView(R.id.ll_reminding)
    LinearLayout llReminding;
    @BindView(R.id.ll_every_day)
    LinearLayout llEveryDay;
    @BindView(R.id.ll_weekly)
    LinearLayout llWeekly;
    @BindView(R.id.ll_custom)
    LinearLayout llCustom;
    @BindView(R.id.ll_custom_day)
    LinearLayout llCustomDay;
    @BindView(R.id.ll_every_sunday)
    LinearLayout llEverySunday;
    @BindView(R.id.ll_every_monday)
    LinearLayout llEveryMonday;
    @BindView(R.id.ll_every_tuesday)
    LinearLayout llEveryTuesday;
    @BindView(R.id.ll_every_wednesday)
    LinearLayout llEveryWednesday;
    @BindView(R.id.ll_every_thursday)
    LinearLayout llEveryThursday;
    @BindView(R.id.ll_every_friday)
    LinearLayout llEveryFriday;
    @BindView(R.id.ll_every_saturday)
    LinearLayout llEverySaturday;
    @BindView(R.id.iv_reminding)
    ImageView ivReminding;
    @BindView(R.id.iv_every_day)
    ImageView ivEveryDay;
    @BindView(R.id.iv_weekly)
    ImageView ivWeekly;
    @BindView(R.id.tv_custom)
    TextView tvCustom;
    @BindView(R.id.iv_every_sunday)
    ImageView ivEverySunday;
    @BindView(R.id.iv_every_monday)
    ImageView ivEveryMonday;
    @BindView(R.id.iv_every_tuesday)
    ImageView ivEveryTuesday;
    @BindView(R.id.iv_every_wednesday)
    ImageView ivEveryWednesday;
    @BindView(R.id.iv_every_thursday)
    ImageView ivEveryThursday;
    @BindView(R.id.iv_every_friday)
    ImageView ivEveryFriday;
    @BindView(R.id.iv_every_saturday)
    ImageView ivEverySaturday;

    private boolean FLAG = false;
    private Context mContext;
    private int cycle = 0;
    private Map<String, String> mMap = new LinkedHashMap<>();
    private static final int SELECTCYCLE = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_cycle;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvBarleft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(getResources().getString(R.string.cycle));
        tvBarRight.setText(getResources().getString(R.string.save));
        mContext = this;
    }

    @Override
    protected void initData() {
        mMap.put("每周日", "0");
        mMap.put("每周一", "0");
        mMap.put("每周二", "0");
        mMap.put("每周三", "0");
        mMap.put("每周四", "0");
        mMap.put("每周五", "0");
        mMap.put("每周六", "0");
    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_BarRight, R.id.ll_reminding, R.id.ll_every_day, R.id.ll_weekly, R.id.ll_custom
            , R.id.ll_every_sunday, R.id.ll_every_monday, R.id.ll_every_tuesday, R.id.ll_every_wednesday,
            R.id.ll_every_thursday, R.id.ll_every_friday, R.id.ll_every_saturday})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_BarRight:
                String custom = "";
                String customNum = "";
                for (Map.Entry<String, String> entry : mMap.entrySet()) {
                    if ("1".equals(entry.getValue()))
                        custom += entry.getKey() + ",";
                    customNum += entry.getValue();
                }
                if (!FLAG) {
                    Intent intent = new Intent();
                    intent.putExtra("cycle", cycle);
                    intent.putExtra("data", custom.length() > 1 ? custom.substring(0, custom.length() - 1) : custom);
                    intent.putExtra("num", customNum);
                    setResult(SELECTCYCLE, intent);
                    finish();
                } else {
                    llSelectCycle.setVisibility(View.VISIBLE);
                    llCustomDay.setVisibility(View.GONE);
                    tvCustom.setText(custom.substring(0, custom.length() - 1));
                    ivReminding.setVisibility(View.INVISIBLE);
                    ivEveryDay.setVisibility(View.INVISIBLE);
                    ivWeekly.setVisibility(View.INVISIBLE);
                    FLAG = false;
                }
                break;
            case R.id.ll_reminding:
                ivReminding.setVisibility(View.VISIBLE);
                ivEveryDay.setVisibility(View.INVISIBLE);
                ivWeekly.setVisibility(View.INVISIBLE);
                cycle = 1;
                break;
            case R.id.ll_every_day:
                ivReminding.setVisibility(View.INVISIBLE);
                ivEveryDay.setVisibility(View.VISIBLE);
                ivWeekly.setVisibility(View.INVISIBLE);
                cycle = 2;
                break;
            case R.id.ll_weekly:
                ivReminding.setVisibility(View.INVISIBLE);
                ivEveryDay.setVisibility(View.INVISIBLE);
                ivWeekly.setVisibility(View.VISIBLE);
                cycle = 3;
                break;
            case R.id.ll_custom:
                llSelectCycle.setVisibility(View.GONE);
                llCustom.setVisibility(View.VISIBLE);
                FLAG = true;
                cycle = 4;
                break;
            case R.id.ll_every_sunday:
                if ("0".equals(mMap.get("每周日"))) {
                    mMap.put("每周日", "1");
                    ivEverySunday.setVisibility(View.VISIBLE);
                } else {
                    mMap.put("每周日", "0");
                    ivEverySunday.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_every_monday:
                if ("0".equals(mMap.get("每周一"))) {
                    mMap.put("每周一", "1");
                    ivEveryMonday.setVisibility(View.VISIBLE);
                } else {
                    mMap.put("每周一", "0");
                    ivEveryMonday.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_every_tuesday:
                if ("0".equals(mMap.get("每周二"))) {
                    mMap.put("每周二", "1");
                    ivEveryTuesday.setVisibility(View.VISIBLE);
                } else {
                    mMap.put("每周二", "0");
                    ivEveryTuesday.setVisibility(View.GONE);
                }

                break;
            case R.id.ll_every_wednesday:
                if ("0".equals(mMap.get("每周三"))) {
                    mMap.put("每周三", "1");
                    ivEveryWednesday.setVisibility(View.VISIBLE);
                } else {
                    mMap.put("每周三", "0");
                    ivEveryWednesday.setVisibility(View.GONE);
                }

                break;
            case R.id.ll_every_thursday:
                if ("0".equals(mMap.get("每周四"))) {
                    mMap.put("每周四", "1");
                    ivEveryThursday.setVisibility(View.VISIBLE);
                } else {
                    mMap.put("每周四", "0");
                    ivEveryThursday.setVisibility(View.GONE);
                }

                break;
            case R.id.ll_every_friday:
                if ("0".equals(mMap.get("每周五"))) {
                    mMap.put("每周五", "1");
                    ivEveryFriday.setVisibility(View.VISIBLE);
                } else {
                    mMap.put("每周五", "0");
                    ivEveryFriday.setVisibility(View.GONE);
                }

                break;
            case R.id.ll_every_saturday:
                if ("0".equals(mMap.get("每周六"))) {
                    mMap.put("每周六", "1");
                    ivEverySaturday.setVisibility(View.VISIBLE);
                } else {
                    mMap.put("每周六", "0");
                    ivEverySaturday.setVisibility(View.GONE);
                }
                break;

        }
    }

}
