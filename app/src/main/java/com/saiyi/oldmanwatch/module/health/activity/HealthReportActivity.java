package com.saiyi.oldmanwatch.module.health.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.module.health.fragment.BloodPressureFragment;
import com.saiyi.oldmanwatch.module.health.fragment.HealthyFragment;
import com.saiyi.oldmanwatch.module.health.fragment.ReportHeartFragment;
import com.saiyi.oldmanwatch.module.health.fragment.SleepFragment;
import com.saiyi.oldmanwatch.module.health.fragment.StepFragment;
import com.saiyi.oldmanwatch.module.health.fragment.StepHistoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/11/6
 * @Describe
 */
public class HealthReportActivity extends BaseAppCompatActivity {
    /**
     *
     */
    @BindView(R.id.tv_step)
    TextView tvStep;
    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;
    @BindView(R.id.tv_sleep)
    TextView tvSleep;
    @BindView(R.id.vp_healthy)
    ViewPager mVp;

    private List<Fragment> mFragments;//Fragment列表
    private MyPagerAdapter mAdapter;

    private Context mContext;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_healthy;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
    }

    @Override
    protected void initData() {
        initTabList();
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(mAdapter);
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentPage = mVp.getCurrentItem();
                switch (currentPage) {
                    case 0:
                        setSelect(true, false, false);
                        break;
                    case 1:
                        setSelect(false, true, false);
                        break;
                    case 2:
                        setSelect(false, false, true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setSelect(true, false, false);
        mVp.setCurrentItem(0);
    }

    private void initTabList() {
        //处理数据
        mFragments = new ArrayList<>();
        StepHistoryFragment stepFragment = new StepHistoryFragment();
        ReportHeartFragment bloodPressureFragment = new ReportHeartFragment();
        SleepFragment sleepFragment = new SleepFragment();

        mFragments.add(stepFragment);
        mFragments.add(bloodPressureFragment);
        mFragments.add(sleepFragment);


    }

    /**
     * 更新选择模块背景
     * @param select1
     * @param select2
     * @param select3
     */
    private void setSelect(Boolean select1, Boolean select2, Boolean select3) {
        tvStep.setSelected(select1);
        tvHeartRate.setSelected(select2);
        tvSleep.setSelected(select3);
    }

    @OnClick({R.id.tv_step, R.id.tv_heart_rate, R.id.tv_sleep})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_step:
                mVp.setCurrentItem(0);
                setSelect(true, false, false);
                break;
            case R.id.tv_heart_rate:
                mVp.setCurrentItem(1);
                setSelect(false, true, false);
                break;
            case R.id.tv_sleep:
                mVp.setCurrentItem(2);
                setSelect(false, false, true);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
