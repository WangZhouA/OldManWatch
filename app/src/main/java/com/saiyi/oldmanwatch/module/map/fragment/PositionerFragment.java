package com.saiyi.oldmanwatch.module.map.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.saiyi.oldmanwatch.base.BaseMvpFragment;
import com.saiyi.oldmanwatch.entity.AllDeviceBean;
import com.saiyi.oldmanwatch.entity.ControlDeviceBean;
import com.saiyi.oldmanwatch.entity.QueryDeviceList;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpRequestCallback;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpUtils;
import com.saiyi.oldmanwatch.listener.MyLocationListener;
import com.saiyi.oldmanwatch.manager.LocalBroadcastManager;
import com.saiyi.oldmanwatch.module.main.DeviceTypeActivity;
import com.saiyi.oldmanwatch.module.main.MainActivity;
import com.saiyi.oldmanwatch.module.main.PositionerMainActivity;
import com.saiyi.oldmanwatch.module.main.SwitchDevicePop;
import com.saiyi.oldmanwatch.module.map.activity.EnclosureActivity;
import com.saiyi.oldmanwatch.module.map.activity.MapPop;
import com.saiyi.oldmanwatch.module.map.activity.PositionStepActivity;
import com.saiyi.oldmanwatch.module.map.activity.TrajectoryActivity;
import com.saiyi.oldmanwatch.module.user.activity.PerfectPersonalAvtivity;
import com.saiyi.oldmanwatch.mvp.presenter.PositionPresenter;
import com.saiyi.oldmanwatch.mvp.view.PositionVIew;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.PopupUtilsList;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.utils.WXShare;
import com.saiyi.oldmanwatch.view.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;
import static com.saiyi.oldmanwatch.helper.RetrofitService.QUERY_ALL_DEVICE_LIST;
import static com.saiyi.oldmanwatch.helper.RetrofitService.USER_INFO;

/**
 * @Author liwenbo
 * @Date 18/11/10
 * @Describe
 */
public class PositionerFragment extends BaseMvpFragment<PositionPresenter> implements PositionVIew<List<QueryDeviceList>>, OnGetGeoCoderResultListener ,HttpRequestCallback{
    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.civ_pic)
    CircleImageView civPic;
    @BindView(R.id.tv_BarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
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
    private String heardUrl;
    private String filiation;
    private String token;
    private int uid;
    private WXShare wxShare;
    private String address;
    TextView text;
    private static final int REQUEST_CODE_ASK_CALL_PHONE = 1;
    private String phone;
    private int Electric = 0;
    private boolean FLAG = true;



    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case 2:
                    if (Util.ifInclude(queryInformation,"0")==true){
                        Intent intent =new Intent();
                        intent.setClass(mContext, PerfectPersonalAvtivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("age", age);
                        intent.putExtra("sex", sex);
                        intent.putExtra("phone",phones);
                        intent.putExtra("headUrl",headUrl);
                        intent.putExtra("birthday", birthday);
                        intent.putExtra("height", height);
                        mContext.startActivity(intent);

                    }else {

                    }
                    break;
            }
        }
    };



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_position_map;
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
        initView();
        initData();

        HttpUtils.getInstance(getActivity()).getSyncHttp(0x03,USER_INFO +  (int) SharedPreferencesHelper.get("uid", 0),this);


        HttpUtils.getInstance(getActivity()).getSyncHttp(0x04,QUERY_ALL_DEVICE_LIST +  (int) SharedPreferencesHelper.get("uid", 0),this);




    }

    @Override
    protected PositionPresenter createPresenter() {
        return new PositionPresenter(this);
    }

    private void initView() {
        tvBarRight.setBackgroundResource(R.mipmap.add);
    }

    private void initData() {
        mac = CheckUtils.getMac();
        token = (String) SharedPreferencesHelper.get("token", "");
        uid = (int) SharedPreferencesHelper.get("uid", -1);
        heardUrl = (String) SharedPreferencesHelper.get("heardUrl", "");
        filiation = (String) SharedPreferencesHelper.get("filiation", "");
        Glide.with(mContext).load(Constant.getRoot() + heardUrl).error(Util.getRes(filiation)).into(civPic);
        tvTitle.setText(filiation);

    }


    @OnClick({R.id.iv_map, R.id.tv_BarRight, R.id.iv_enclosure, R.id.iv_phone, R.id.iv_trajectory,
            R.id.iv_location, R.id.positioning_share, R.id.iv_real_time, R.id.civ_pic})
    public void onViewClicked(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.civ_pic:
//                mPresenter.queryDeviceList(this);

//                PopupUtils.getInstance(getActivity()).showAsDrop( civPic);

                if (allDeviceBeans.size()>0){

                    PopupUtilsList.getInstance(getActivity()).initDate(getActivity(),allDeviceBeans);
                    PopupUtilsList.getInstance(getActivity()).showAsDrop(civPic);
                }

                break;
            case R.id.tv_BarRight:
                intent.setClass(mContext, DeviceTypeActivity.class);
                startActivity(intent);
                break;
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
                intent.setClass(mContext, PositionStepActivity.class);
                startActivity(intent);
                break;
            case R.id.positioning_share:
                wxShare.share("这是要分享的文字");
                break;
        }

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

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(Calendar.getInstance().getTime());
        ImageView ivElectric = contentView.findViewById(R.id.iv_quantity);
        TextView tvElectric = contentView.findViewById(R.id.tv_quantity);
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
        setCostomMsg(latitude, longitude);
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
        Logger.e("ssss", "address......." + address);
        text.setText(address);
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

    @Override
    public ControlDeviceBean getData() {
        ControlDeviceBean data = new ControlDeviceBean();
        data.setMac(mac);
        data.setType("CR");
        return data;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public int getUid() {
        return uid;
    }

    @Override
    public void onRequestSuccessData(List<QueryDeviceList> data) {
        SwitchDevicePop mPop = new SwitchDevicePop(mContext, data, civPic);
        mPop.show(civPic);
        mPop.setItemSelectListener(new SwitchDevicePop.itemSelectListener() {
            @Override
            public void getDevice(QueryDeviceList info) {
                Intent intent = new Intent();
                switch (info.getType()) {
                    case "1":
                        intent.setClass(mContext, MainActivity.class);
                        SharedPreferencesHelper.put("mac", info.getMac());
                        SharedPreferencesHelper.put("heardUrl", info.getHeadUrl() == null ? "" : info.getHeadUrl());
                        SharedPreferencesHelper.put("filiation", info.getFiliation());
                        SharedPreferencesHelper.put("type", info.getType());
                        startActivity(intent);
                        getActivity().finish();
                        break;
                    case "2":
                        intent.setClass(mContext, PositionerMainActivity.class);
                        SharedPreferencesHelper.put("mac", info.getMac());
                        SharedPreferencesHelper.put("heardUrl", info.getHeadUrl() == null ? "" : info.getHeadUrl());
                        SharedPreferencesHelper.put("filiation", info.getFiliation());
                        SharedPreferencesHelper.put("type", info.getType());
                        startActivity(intent);
                        getActivity().finish();
                        break;
                }
            }
        });
    }

    List<String> queryInformation = new ArrayList<>(); // 查询用户信息是否完整。1，代表 完整  0 代表不完整。


    String name;
    String age;
    String sex;
    String phones;
    String headUrl;
    String birthday;
    String height;

    private List<AllDeviceBean> allDeviceBeans=new ArrayList<>();
    @Override
    public void onResponse(String sequest, int type) {

        switch (type){

            case  0x03:
                queryInformation.clear();
                try {
                    JSONObject object = new JSONObject(sequest);
                    JSONObject itemOj = object.getJSONObject("data");

                    Log.e("http", itemOj.getString("name"));

                    name=itemOj.getString("name");
                    age=itemOj.getString("age");
                    sex=itemOj.getString("sex");
                    phones=itemOj.getString("phone");
                    headUrl=itemOj.getString("headUrl");
                    birthday=itemOj.getString("birthday");
                    height=itemOj.getString("height");


                    if (TextUtils.isEmpty(sex)){
                        queryInformation.add("0");
                    }else {
                        queryInformation.add("1");
                    }
                    if (TextUtils.isEmpty(birthday)){
                        queryInformation.add("0");
                    }else {
                        queryInformation.add("1");
                    }
                    if (TextUtils.isEmpty(height)){
                        queryInformation.add("0");
                    }else {
                        queryInformation.add("1");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessageDelayed(2,100);
                break;


            case 0x04:

                allDeviceBeans.clear();
                try {
                    JSONObject object = new JSONObject(sequest);
                    JSONArray array = object.getJSONArray("data");
                    for (int i=0;i<array.length();i++){

                        AllDeviceBean allDeviceBean =new AllDeviceBean();
                        JSONObject itemOj = array.getJSONObject(i);
                        allDeviceBean.setFiliation(itemOj.getString("filiation"));
                        allDeviceBean.setHeadUrl(itemOj.getString("headUrl"));
                        allDeviceBean.setType(itemOj.getString("type"));
                        allDeviceBean.setMac(itemOj.getString("mac"));
                        allDeviceBeans.add(allDeviceBean);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("---->allDeviceBeans",allDeviceBeans.get(0).getFiliation());
                mHandler.sendEmptyMessageDelayed(3,100);

                break;

        }

    }

    @Override
    public void onFailure(String exp) {

    }

    /**
     * 广播
     */
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
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

    public void setCostomMsg(double latitude, double longitude) {
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
        SharedPreferencesHelper.put(SharedPreferencesHelper.DEVICETYPE,1);
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
    }

}
