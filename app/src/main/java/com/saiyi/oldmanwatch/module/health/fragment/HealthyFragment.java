package com.saiyi.oldmanwatch.module.health.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.module.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/10/5
 * @Describe
 */
public class HealthyFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_healthy, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initView();
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView() {
        initTabList();
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
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
//                    case 2:
//                        setSelect(false, false, true);
//                        break;
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

    public void setItem(int type) {
        if (type == 0)
            setSelect(true, false, false);
        else setSelect(false, true, false);
        mVp.setCurrentItem(type);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initTabList() {
        //处理数据
        mFragments = new ArrayList<>();
        StepFragment stepFragment = new StepFragment();
        BloodPressureFragment bloodPressureFragment = new BloodPressureFragment();
//        SleepFragment sleepFragment = new SleepFragment();

        mFragments.add(stepFragment);
        mFragments.add(bloodPressureFragment);
//        mFragments.add(sleepFragment);


    }

    private void setSelect(Boolean select1, Boolean select2, Boolean select3) {
        tvStep.setSelected(select1);
        tvHeartRate.setSelected(select2);
//        tvSleep.setSelected(select3);
    }

    private void initData() {

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
//            case R.id.tv_sleep:
//                mVp.setCurrentItem(2);
//                setSelect(false, false, true);
//                break;
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
