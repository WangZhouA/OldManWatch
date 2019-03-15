package com.saiyi.oldmanwatch.module.map.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.QueryHeartsList;
import com.saiyi.oldmanwatch.entity.Trajectory;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.mvp.presenter.TrajectoryPresenter;
import com.saiyi.oldmanwatch.mvp.view.TraJectoryView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.view.wheel.OptionsPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/10/12
 * @Describe 轨迹
 */
public class TrajectoryActivity extends BaseMvpAppCompatActivity<TrajectoryPresenter> implements TraJectoryView<List<Trajectory>> {
    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.trajectory_map)
    MapView mMapView;
    @BindView(R.id.tv_playback)
    TextView tvPlayBack;
    @BindView(R.id.tv_trajectory)
    TextView tvTrajectory;
    @BindView(R.id.cb_switch)
    CheckBox cbSwitch;
    @BindView(R.id.tv_time_left)
    TextView tvTimeLeft;
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;

    private Context mContext;
    private BaiduMap mBaiduMap;
    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    private ArrayList<String> hours = new ArrayList<>();
    private ArrayList<String> minutes = new ArrayList<>();
    private OptionsPickerView mPickerView;
    private String mac;
    private String token;
    private double latitude;
    private double longitude;
    Marker marker;
    private String imageUrl;
    private String filiation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trajectory;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = TrajectoryActivity.this;
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        years.add((year - 3) + "年");
        years.add((year - 2) + "年");
        years.add((year - 1) + "年");
        years.add(year + "年");
        months.addAll(Arrays.asList(getResources().getStringArray(R.array.month)));
        days.addAll(Arrays.asList(getResources().getStringArray(R.array.days)));
        hours.addAll(Arrays.asList(getResources().getStringArray(R.array.hour)));
        minutes.addAll(Arrays.asList(getResources().getStringArray(R.array.minute)));
        tvBarTitle.setText(year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
        tvBarRight.setText("今天");
    }

    @Override
    protected TrajectoryPresenter createPresenter() {
        return new TrajectoryPresenter(this);
    }

    @Override
    protected void initData() {
        mac = CheckUtils.getMac();
        token = (String) SharedPreferencesHelper.get("token", "");
        imageUrl = (String) SharedPreferencesHelper.get("headUrl", "");
        filiation = (String) SharedPreferencesHelper.get("filiation", "");
        latitude = BaseApplication.latitude;
        longitude = BaseApplication.longitude;
        initMap();

    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_BarTitle, R.id.tv_playback, R.id.tv_trajectory
            , R.id.cb_switch, R.id.tv_time_left, R.id.tv_time_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_BarTitle:
                mPickerView = new OptionsPickerView(mContext);
                mPickerView.setPicker(years, months, days, false);
                mPickerView.setItemsVisible(7);
                mPickerView.setCyclic(false, true, true);
                mPickerView.show();
                mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(String t1, String t2, String t3) {
                        int month = Integer.parseInt(t2.substring(0, t2.length() - 1));
                        int day = Integer.parseInt(t3.substring(0, t3.length() - 1));
                        tvBarTitle.setText(t1.substring(0, t1.length() - 1) + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
                        queryData();
                    }
                });

                break;
            case R.id.tv_playback:
                break;
            case R.id.tv_trajectory:
                break;
            case R.id.cb_switch:
                cbSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                        } else {

                        }
                    }
                });
                break;
            case R.id.tv_time_left:
                mPickerView = new OptionsPickerView(mContext);
                mPickerView.setPicker(hours, minutes, false);
                mPickerView.setItemsVisible(7);
                mPickerView.setCyclic(true, true, false);
                mPickerView.show();
                mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(String t1, String t2, String t3) {
                        tvTimeLeft.setText(t1 + ":" + t2);
                        queryData();
                    }
                });

                break;
            case R.id.tv_time_right:
                mPickerView = new OptionsPickerView(mContext);
                mPickerView.setPicker(hours, minutes, false);
                mPickerView.setItemsVisible(7);
                mPickerView.setCyclic(true, true, false);
                mPickerView.show();
                mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(String t1, String t2, String t3) {
                        tvTimeRight.setText(t1 + ":" + t2);
                        queryData();
                    }
                });

                break;
        }
    }

    private void queryData() {
        if (!"".equals(tvTimeLeft.getText().toString()) && !"".equals(tvTimeRight.getText().toString())) {
            mPresenter.getTraJectory(this);
        }
    }

    private void initMap() {
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象 MAP_TYPE_NORMAL 普通地图  MAP_TYPE_SATELLITE 微信地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        // 不显示地图上比例尺
        mMapView.showScaleControl(false);

        // 不显示地图缩放控件（按钮控制栏）
        mMapView.showZoomControls(false);
        //开启交通图
        //mBaiduMap.setTrafficEnabled(true);
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater
                .inflate(
                        R.layout.layout_position_icon,
                        null);
        ImageView icon = view.findViewById(R.id.iv_icon);
        Glide.with(mContext).load(Constant.getRoot() + imageUrl).error(Util.getRes(filiation)).into(icon);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        marker = (Marker) mBaiduMap.addOverlay(option);
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(point)
                //放大地图到10倍
                .zoom(10)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }


    @Override
    public QueryHeartsList getData() {
        QueryHeartsList data = new QueryHeartsList();
        data.setMac(mac);
        data.setStartDate(tvBarTitle.getText().toString() + " " + tvTimeLeft.getText().toString() + ":00");
        data.setEndDate(tvBarTitle.getText().toString() + " " + tvTimeRight.getText().toString() + ":00");
        return data;
    }

    @Override
    public String getToken() {
        return token;
    }


    @Override
    public void onRequestSuccessData(List<Trajectory> data) {
        marker.remove();
        //构建折线点坐标
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < data.size(); i++) {
            LatLng point = new LatLng(Double.parseDouble(data.get(i).getN()), Double.parseDouble(data.get(i).getE()));
            points.add(point);
        }
        latitude = points.get(points.size() - 1).latitude;
        longitude = points.get(points.size() - 1).longitude;
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater
                .inflate(
                        R.layout.layout_position_icon,
                        null);
        ImageView icon = view.findViewById(R.id.iv_icon);
        icon.setImageResource(R.mipmap.call4);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        marker = (Marker) mBaiduMap.addOverlay(option);
        //绘制折线
        OverlayOptions ooPolyline = new PolylineOptions().width(10)
                .color(getResources().getColor(R.color.appColor)).points(points);
        mBaiduMap.addOverlay(ooPolyline);
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(points.get(points.size() - 1))
                //放大地图到10倍
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将 要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.animateMapStatus(mMapStatusUpdate);
    }
}
