package com.saiyi.oldmanwatch.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.onFinshListener;
import com.saiyi.oldmanwatch.module.health.fragment.HealthyFragment;
import com.saiyi.oldmanwatch.module.home.fragment.HomeFragment;
import com.saiyi.oldmanwatch.module.map.fragment.MapFragment;
import com.saiyi.oldmanwatch.module.user.fragment.MeFragment;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.PopupUtilsList;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.view.CustomViewPager;
import com.saiyi.oldmanwatch.view.ImageTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MainActivity extends BaseAppCompatActivity  implements onFinshListener {

    @BindView(R.id.main_view_pager)
    CustomViewPager mVp;
    @BindView(R.id.itv_home_page)
    ImageTextView itvHomePage;
    @BindView(R.id.itv_map)
    ImageTextView itvMap;
    @BindView(R.id.itv_healthy)
    ImageTextView itvHealthy;
    @BindView(R.id.itv_my)
    ImageTextView itvMy;


    private List<Fragment> mFragments;//Fragment列表
    private MyPagerAdapter mAdapter;
    HomeFragment mHomeFragment;
    MapFragment mMapFragment;
    HealthyFragment mHealthyFragment;
    MeFragment meFragment;
    private int uid;
    private String phone;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                JPushInterface.setAlias(getApplicationContext(), uid, phone);
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        itvHomePage.setImageResource(R.drawable.navigation_home_selected);
        itvHomePage.setText(getResources().getString(R.string.home_page));
        itvHomePage.setTextSize(10);
        itvHomePage.setTextColor(getResources().getColor(R.color.appColor));
        itvMap.setImageResource(R.drawable.navigation_map_selected);
        itvMap.setText(getResources().getString(R.string.location));
        itvMap.setTextSize(10);
        itvMap.setTextColor(getResources().getColor(R.color.hint_color));
        itvHealthy.setImageResource(R.drawable.navigation_healthy_selected);
        itvHealthy.setText(getResources().getString(R.string.healthy));
        itvHealthy.setTextSize(10);
        itvHealthy.setTextColor(getResources().getColor(R.color.hint_color));
        itvMy.setImageResource(R.drawable.navigation_me_selected);
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
                Intent intent = new Intent();
                int currentPage = mVp.getCurrentItem();
                switch (currentPage) {
                    case 0:
                        setSelect(true, false, false, false);
                        break;
                    case 1:
                        setSelect(false, true, false, false);
                        break;
                    case 2:
                        setSelect(false, false, true, false);
                        break;
                    case 3:
                        setSelect(false, false, false, true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setSelect(true, false, false, false);
        mVp.setCurrentItem(0);

    }

    @Override
    protected void initData() {
        uid = (int) SharedPreferencesHelper.get("uid", 0);
        phone = (String) SharedPreferencesHelper.get("account", "");
        Util.setAlias(this, uid, phone);
    }

    private void initTabList() {
        //处理数据
        mFragments = new ArrayList<>();
        mHomeFragment = new HomeFragment();
        mMapFragment = new MapFragment();
        mHealthyFragment = new HealthyFragment();
        meFragment = new MeFragment();
        mFragments.add(mHomeFragment);
        mFragments.add(mMapFragment);
        mFragments.add(mHealthyFragment);
        mFragments.add(meFragment);

//        PopupUtils.getInstance(this).setOnFinshListener(this);
        PopupUtilsList.getInstance(this).setOnFinshListener(this);


    }

    public void gotoHealth(int type) {
        mVp.setCurrentItem(2);
        mHealthyFragment.setItem(type);
    }

    @OnClick({R.id.itv_home_page, R.id.itv_map, R.id.itv_healthy, R.id.itv_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itv_home_page:
                mVp.setCurrentItem(0);
                setSelect(true, false, false, false);
                break;
            case R.id.itv_map:
                mVp.setCurrentItem(1);
                setSelect(false, true, false, false);
                break;
            case R.id.itv_healthy:
                mVp.setCurrentItem(2);
                setSelect(false, false, true, false);
                break;
            case R.id.itv_my:
                mVp.setCurrentItem(3);
                setSelect(false, false, false, true);
                break;
        }
    }

    private void setSelect(Boolean select1, Boolean select2, Boolean select3, Boolean select4) {
        itvHomePage.setSelected(select1);
        itvMap.setSelected(select2);
        itvHealthy.setSelected(select3);
        itvMy.setSelected(select4);
        if (select1)
            itvHomePage.setTextColor(getResources().getColor(R.color.appColor));
        else
            itvHomePage.setTextColor(getResources().getColor(R.color.hint_color));

        if (select2)
            itvMap.setTextColor(getResources().getColor(R.color.appColor));
        else
            itvMap.setTextColor(getResources().getColor(R.color.hint_color));

        if (select3)
            itvHealthy.setTextColor(getResources().getColor(R.color.appColor));
        else
            itvHealthy.setTextColor(getResources().getColor(R.color.hint_color));

        if (select4)
            itvMy.setTextColor(getResources().getColor(R.color.appColor));
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

    private JPushMessageReceiver jPushMessageReceiver = new JPushMessageReceiver() {
        @Override
        public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
            super.onAliasOperatorResult(context, jPushMessage);
            if (jPushMessage.getErrorCode() == 6002) {//超时处理
                Message obtain = Message.obtain();
                obtain.what = 100;
                mHandler.sendMessageDelayed(obtain, 1000 * 60);//60秒后重新验证
            } else {
                Logger.e("onAliasOperatorResult: ", jPushMessage.getErrorCode() + "");
            }
        }
    };

    private long mExitTime;

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
