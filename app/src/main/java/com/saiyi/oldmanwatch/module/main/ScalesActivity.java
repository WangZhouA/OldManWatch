package com.saiyi.oldmanwatch.module.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.onFinshListener;
import com.saiyi.oldmanwatch.module.health.fragment.TrendFragment;
import com.saiyi.oldmanwatch.module.home.fragment.BodyFatScaleFragment;
import com.saiyi.oldmanwatch.module.user.fragment.ScalesMeFragment;
import com.saiyi.oldmanwatch.utils.PopupUtilsList;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.CustomViewPager;
import com.saiyi.oldmanwatch.view.ImageTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 体脂称主页
 */
public class ScalesActivity extends BaseAppCompatActivity implements onFinshListener {
    @BindView(R.id.main_view_pager)
    CustomViewPager mViewPager;
    @BindView(R.id.itv_scale)
    ImageTextView mItvScale;
    @BindView(R.id.itv_trend)
    ImageTextView mItvTrend;
    @BindView(R.id.itv_my)
    ImageTextView mItvMy;

    private List<Fragment> mFragments;//Fragment列表
    private MyPagerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scale;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mItvScale.setImageResource(R.drawable.navigation_home_selected);
        mItvScale.setText(getResources().getString(R.string.home_page));
        mItvScale.setTextSize(10);
        mItvScale.setTextColor(getResources().getColor(R.color.hint_color));
        mItvTrend.setImageResource(R.drawable.navigation_trend_selected);
        mItvTrend.setText(getResources().getString(R.string.trend));
        mItvTrend.setTextSize(10);
        mItvTrend.setTextColor(getResources().getColor(R.color.hint_color));
        mItvMy.setImageResource(R.drawable.navigation_me_selected);
        mItvMy.setText(getResources().getString(R.string.my));
        mItvMy.setTextSize(10);
        mItvMy.setTextColor(getResources().getColor(R.color.hint_color));
        initTabList();
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setScanScroll(false);




        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentPage = mViewPager.getCurrentItem();
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
        mViewPager.setCurrentItem(0);

        PopupUtilsList.getInstance(this).setOnFinshListener(this);



    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.itv_scale, R.id.itv_trend, R.id.itv_my})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.itv_scale:
                mViewPager.setCurrentItem(0);
                setSelect(true, false, false);
                break;
            case R.id.itv_trend:
                mViewPager.setCurrentItem(1);
                setSelect(false, true, false);
                break;
            case R.id.itv_my:
                mViewPager.setCurrentItem(2);
                setSelect(false, false, true);
                break;
        }
    }

    private void initTabList() {
        //处理数据
        mFragments = new ArrayList<>();
        BodyFatScaleFragment bodyFatScaleFragment = new BodyFatScaleFragment();
        TrendFragment trendFragment = new TrendFragment();
        ScalesMeFragment scalesMeFragment = new ScalesMeFragment();
        mFragments.add(bodyFatScaleFragment);
        mFragments.add(trendFragment);
        mFragments.add(scalesMeFragment);
    }

    private void setSelect(Boolean select1, Boolean select2, Boolean select3) {
        mItvScale.setSelected(select1);
        mItvTrend.setSelected(select2);
        mItvMy.setSelected(select3);

        if (select1)
            mItvScale.setTextColor(getResources().getColor(R.color.appColor));
        else
            mItvScale.setTextColor(getResources().getColor(R.color.hint_color));

        if (select2)
            mItvTrend.setTextColor(getResources().getColor(R.color.appColor));
        else
            mItvTrend.setTextColor(getResources().getColor(R.color.hint_color));
        if (select3)
            mItvMy.setTextColor(getResources().getColor(R.color.appColor));
        else
            mItvMy.setTextColor(getResources().getColor(R.color.hint_color));

    }

    @Override
    public void setFinshListener() {
       finish();
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

    long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - mExitTime < 2000) {
                System.exit(0);
            } else {
                mExitTime = currentTime;
                ToastUtils.show(this,"再按一次退出程序",ToastUtils.LENGTH_SHORT);
            }
        }
        return true;
    }

}
