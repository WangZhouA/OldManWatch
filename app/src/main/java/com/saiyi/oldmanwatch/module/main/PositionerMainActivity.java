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
import com.saiyi.oldmanwatch.module.map.fragment.PositionerFragment;
import com.saiyi.oldmanwatch.module.user.fragment.MeFragment;
import com.saiyi.oldmanwatch.utils.PopupUtilsList;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.CustomViewPager;
import com.saiyi.oldmanwatch.view.ImageTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public class PositionerMainActivity extends BaseAppCompatActivity implements onFinshListener {

    @BindView(R.id.main_view_pager)
    CustomViewPager mVp;
    @BindView(R.id.itv_map)
    ImageTextView itvMap;
    @BindView(R.id.itv_my)
    ImageTextView itvMy;


    private List<Fragment> mFragments;//Fragment列表
    private MyPagerAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_positioner_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        itvMap.setImageResource(R.drawable.navigation_map_position_selected);
        itvMap.setText(getResources().getString(R.string.location));
        itvMap.setTextSize(10);
        itvMap.setTextColor(getResources().getColor(R.color.hint_color));
        itvMy.setImageResource(R.drawable.navigation_me_position_selected);
        itvMy.setText(getResources().getString(R.string.my));
        itvMy.setTextSize(10);
        itvMy.setTextColor(getResources().getColor(R.color.hint_color));
        initTabList();
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(mAdapter);
        mVp.setScanScroll(false);
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentPage = mVp.getCurrentItem();
                switch (currentPage) {
                    case 0:
                        setSelect(true, false);
                        break;
                    case 1:
                        setSelect(false, true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setSelect(true, false);
        mVp.setCurrentItem(0);


        PopupUtilsList.getInstance(this).setOnFinshListener(this);





    }


    private void initTabList() {
        //处理数据
        mFragments = new ArrayList<>();
        PositionerFragment mMapFragment = new PositionerFragment();
        MeFragment meFragment = new MeFragment();
        mFragments.add(mMapFragment);
        mFragments.add(meFragment);

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.itv_map, R.id.itv_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itv_map:
                mVp.setCurrentItem(0);
                setSelect(true, false);
                break;
            case R.id.itv_my:
                mVp.setCurrentItem(1);
                setSelect(false, true);
                break;
        }
    }

    private void setSelect(Boolean select2, Boolean select4) {
        itvMap.setSelected(select2);
        itvMy.setSelected(select4);

        if (select2)
            itvMap.setTextColor(getResources().getColor(R.color.appColor_position));
        else
            itvMap.setTextColor(getResources().getColor(R.color.hint_color));

        if (select4)
            itvMy.setTextColor(getResources().getColor(R.color.appColor_position));
        else
            itvMy.setTextColor(getResources().getColor(R.color.hint_color));

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
