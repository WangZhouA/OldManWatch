package com.saiyi.oldmanwatch.module.map.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;


import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpFragment;
import com.saiyi.oldmanwatch.entity.ControlDeviceBean;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.listener.MyLocationListener;
import com.saiyi.oldmanwatch.manager.LocalBroadcastManager;
import com.saiyi.oldmanwatch.module.main.MainActivity;
import com.saiyi.oldmanwatch.module.map.activity.EnclosureActivity;
import com.saiyi.oldmanwatch.module.map.activity.MapPop;
import com.saiyi.oldmanwatch.module.map.activity.TrajectoryActivity;
import com.saiyi.oldmanwatch.mvp.presenter.MapPrensenter;
import com.saiyi.oldmanwatch.mvp.view.MyMapView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.ExampleUtil;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.utils.WXShare;
import com.saiyi.oldmanwatch.view.CircleImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.crypto.spec.IvParameterSpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * @Author liwenbo
 * @Date 18/10/11
 * @Describe
 */

public class MapFragment extends BaseMvpFragment<MapPrensenter> implements MyMapView<String>, OnGetGeoCoderResultListener {

    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.civ_pic)
    CircleImageView civPic;
    @BindView(R.id.tv_BarTitle)
    TextView tvTitle;
    @BindView(R.id.iv_real_time)
    ImageView ivRealTime;
    @BindView(R.id.iv_map)
    ImageView ivMap;
    @BindView(R.id.iv_enclosure)
    ImageView ivEnclosure;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.iv_trajectory)
    ImageView ivTrajectory;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.positioning_share)
    ImageView ivShare;


    private Context mContext;
    private BaiduMap mBaiduMap;
    private static boolean isPermissionRequested = false;
    private int mMaptype = 1;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    LocationClientOption option = new LocationClientOption();
    private String mac;
    private WXShare wxShare;
    private double latitude;
    private double longitude;
    private static final int REQUEST_CODE_ASK_CALL_PHONE = 1;
    private String phone;
    private String address;
    TextView text;
    private String name;
    private String headUrl;
    private int Electric = 0;
    private boolean FLAG = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        mContext = getActivity();
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
        requestPermission();
        registerMessageReceiver();
        wxShare = new WXShare(mContext);
        wxShare.setListener(new WXShare.OnResponseListener() {
            @Override
            public void onSuccess() {
                // 分享成功
            }

            @Override
            public void onCancel() {
                // 分享取消
            }

            @Override
            public void onFail(String message) {
                // 分享失败
            }
        });
        initData();
    }

    @Override
    protected MapPrensenter createPresenter() {
        return new MapPrensenter(this);
    }

    private void initData() {
        mac = CheckUtils.getMac();
        latitude = BaseApplication.latitude;
        longitude = BaseApplication.longitude;
        name = (String) SharedPreferencesHelper.get("filiation", "");
        headUrl = (String) SharedPreferencesHelper.get("headUrl", "");
        tvTitle.setText(name);
        Glide.with(mContext).load(Constant.getRoot() + headUrl).error(Util.getRes(name)).into(civPic);
    }

    @OnClick({R.id.iv_map, R.id.iv_enclosure, R.id.iv_phone, R.id.iv_trajectory,
            R.id.iv_location, R.id.positioning_share, R.id.iv_real_time})
    public void onViewClicked(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.iv_real_time:
                if (FLAG) {

                    mPresenter.sendCmDevice(this);
                    FLAG = false;
                }
                break;
            case R.id.iv_map:
                MapPop mPop = new MapPop(mContext, mMaptype);
                mPop.show(ivMap);
                mPop.setTypeSelectListener(new MapPop.typeSelectListener() {
                    @Override
                    public void getType(int type) {
                        switch (type) {
                            case 1:
                                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                                mBaiduMap.setTrafficEnabled(false);
                                mMaptype = 1;
                                break;
                            case 2:
                                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                                mBaiduMap.setTrafficEnabled(false);
                                mMaptype = 2;
                                break;
                            case 3:
                                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                                mBaiduMap.setTrafficEnabled(true);
                                mMaptype = 3;
                                break;
                        }
                    }
                });
                break;
            case R.id.iv_enclosure:
                intent.setClass(mContext, EnclosureActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_phone:
                showDialog();
                break;
            case R.id.iv_trajectory:
                intent.setClass(mContext, TrajectoryActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_location:

                break;
            case R.id.positioning_share:
                wxShare.share("这是要分享的文字");
                break;
        }

    }


    /**
     * Android6.0之后需要动态申请权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {
            isPermissionRequested = true;
            ArrayList<String> permissions = new ArrayList<>();
            if (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissions.size() == 0) {
                return;
            } else {
                requestPermissions(permissions.toArray(new String[permissions.size()]), 0);
            }
        }
    }

    public void initMap(final double latitude, final double longitude) {
        getAddress(latitude, longitude);
        Marker marker;
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象 MAP_TYPE_NORMAL 普通地图  MAP_TYPE_SATELLITE 卫星地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通图
        //mBaiduMap.setTrafficEnabled(true);
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }

        // 不显示地图上比例尺
        mMapView.showScaleControl(false);

        // 不显示地图缩放控件（按钮控制栏）
        mMapView.showZoomControls(false);
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
        mBaiduMap.addOverlay(option);

        View contentView = inflater
                .inflate(
                        R.layout.layout_position_content,
                        null);
        TextView tvTime = contentView.findViewById(R.id.tv_time);
        ImageView ivElectric = contentView.findViewById(R.id.iv_quantity);
        TextView tvElectric = contentView.findViewById(R.id.tv_quantity);
        BaseApplication.Electric = Electric;
        if (0 < Electric && Electric <= 25) {
            ivElectric.setImageResource(R.mipmap.electricity4);
        } else if (Electric > 25 && Electric <= 50) {
            ivElectric.setImageResource(R.mipmap.electricity3);
        } else if (Electric > 50 && Electric <= 75) {
            ivElectric.setImageResource(R.mipmap.electricity2);
        } else {
            ivElectric.setImageResource(R.mipmap.electricity1);
        }
        tvElectric.setText(Electric == 0 ? "100%" : Electric + "%");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(Calendar.getInstance().getTime());
        tvTime.setText(time);
        text = contentView.findViewById(R.id.tv_text);
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToBaiduMap(latitude, longitude, getAddress(latitude, longitude));
            }
        });
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(contentView, point, 180);
        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);
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
        setCostomMsg(latitude, longitude);
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String V_LATITUDE = "latitude";
    public static final String V_LONGITUDE = "longitude";
    public static final String V_ELECTRIC = "Electric";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mMessageReceiver, filter);
    }

    public void showDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View textEntryView = inflater
                .inflate(
                        R.layout.dialog_relation,
                        null);
        final EditText etCustom = (EditText) textEntryView.findViewById(R.id.et_custom);
        TextView title = textEntryView.findViewById(R.id.tv_custom);
        title.setText("拨打电话");
        etCustom.setHint("输入手机号");
        final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(
                mContext, android.app.AlertDialog.THEME_HOLO_LIGHT);
        dialog.setView(textEntryView);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if ("".equals(etCustom.getText().toString()) || !CheckUtils.isPhone(etCustom.getText().toString())) {
                    ToastUtils.show(mContext, "手机号不正确！", ToastUtils.LENGTH_LONG);
                    return;
                }
                callPhone(etCustom.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        android.app.AlertDialog ad = dialog.create();
        ad.show();
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        phone = phoneNum;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.READ_LOGS}, REQUEST_CODE_ASK_CALL_PHONE);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            startActivity(intent);
        }
    }

    //动态权限申请后处理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                // Permission Granted callDirectly(mobile);

                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phone);
                intent.setData(data);
                startActivity(intent);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * 跳转百度地图进行导航
     */
    private void goToBaiduMap(double mLat, double mLng, String mAddressStr) {
        if (!CheckUtils.isInstalled(mContext, "com.baidu.BaiduMap")) {
            ToastUtils.show(mContext, "请先安装百度地图客户端", ToastUtils.LENGTH_LONG);
            return;
        }
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/direction?destination=latlng:"
                + mLat + ","
                + mLng + "|name:" + mAddressStr + // 终点
                "&mode=driving" + // 导航路线方式
                "&src=" + mContext.getPackageName()));
        startActivity(intent); // 启动调用
    }

    /**
     * 根据经纬度获取地址，不需要地图API
     *
     * @param latitude
     * @param longitude
     */
    public String getAddress(double latitude, double longitude) {
        LatLng point = new LatLng(latitude, longitude);
        GeoCoder mSearch = null;
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                .location(point));
        return address;
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        address = reverseGeoCodeResult.getAddress();

        text.setText(address);
    }

    @Override
    public ControlDeviceBean getData() {
        ControlDeviceBean data = new ControlDeviceBean();
        data.setMac(mac);
        data.setType("CR");
        return data;
    }

    @Override
    public void onRequestSuccessData(String data) {

    }


    /**
     * 广播
     */
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    String latitude = intent.getStringExtra(V_LATITUDE);
                    String longitude = intent.getStringExtra(V_LONGITUDE);
                    Electric = Integer.parseInt(intent.getStringExtra(V_ELECTRIC));
                    FLAG = true;
                    getAddress(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    initMap(Double.parseDouble(latitude), Double.parseDouble(longitude));

                }
            } catch (Exception e) {

            }
        }
    }

    private void setCostomMsg(double latitude, double longitude) {
        LatLng point = new LatLng(latitude, longitude);
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(point)
                //放大地图到10倍
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        wxShare.register();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        wxShare.unregister();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mMessageReceiver);
    }


}
