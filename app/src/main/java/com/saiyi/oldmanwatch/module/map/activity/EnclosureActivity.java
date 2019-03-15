package com.saiyi.oldmanwatch.module.map.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.Enclosures;
import com.saiyi.oldmanwatch.entity.UpdateEnclosureBean;
import com.saiyi.oldmanwatch.mvp.presenter.EnclosurePresenter;
import com.saiyi.oldmanwatch.mvp.view.EnclosureView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/10/12
 * @Describe 地理围栏
 */
public class EnclosureActivity extends BaseMvpAppCompatActivity<EnclosurePresenter> implements EnclosureView<Enclosures> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.cb_switch)
    CheckBox cbSwitch;

    private Context mContext;
    private BaiduMap mBaiduMap;
    private String mac;
    private String token;
    private double e;
    private double n;
    private String type;

    @Override
    protected int getLayoutId() {
        type = (String) SharedPreferencesHelper.get("type", "");
        if ("1".equals(type)) {
            return R.layout.activity_enclosure;
        } else {
            return R.layout.activity_position_enclosure;
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(R.string.set_safe_location);
        tvBarRight.setBackgroundResource(R.mipmap.add);
        mContext = this;
        cbSwitch.setChecked(true);
    }

    @Override
    protected EnclosurePresenter createPresenter() {
        return new EnclosurePresenter(this);
    }

    @Override
    protected void initData() {
        mac = CheckUtils.getMac();
        token = (String) SharedPreferencesHelper.get("token", "");
        cbSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(tvAddress.getText().toString())) {
                    Intent intent = new Intent();
                    ToastUtils.show(mContext, "请创建围栏地址", ToastUtils.LENGTH_LONG);
                    intent.setClass(mContext, CreateEnclosureActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getEnclosures(this);
    }

    private void initMap() {
        Marker marker;
        mBaiduMap = mMapView.getMap();
        //普通地图 ,mBaiduMap是地图控制器对象 MAP_TYPE_NORMAL 普通地图  MAP_TYPE_SATELLITE 卫星地图
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
        LatLng point = new LatLng(n, e);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.positioning1);

        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        mBaiduMap.addOverlay(option);
        OverlayOptions ooCircle = new CircleOptions().fillColor(0x302C9EFF).
                center(point).stroke(new Stroke(5, 0x000000FF)).
                radius(1200);//设置颜色和透明度，均使用16进制显示，0xAARRGGBB，如 0xAA000000 其中AA是透明度，000000为颜色

        mBaiduMap.addOverlay(ooCircle);
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(point)
                //放大地图到20倍
                .zoom(20)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_BarRight, R.id.tv_confirm})
    public void onViewClicked(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_BarRight:
                intent.setClass(mContext, CreateEnclosureActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_confirm:
                if ("".equals(tvAddress.getText().toString())) {
                    ToastUtils.show(mContext, "请创建围栏地址", ToastUtils.LENGTH_LONG);
                    intent.setClass(mContext, CreateEnclosureActivity.class);
                    startActivity(intent);
                    return;
                }
                mPresenter.updateEnclosure(this);
                break;
        }
    }

    @Override
    public void getSuccess(String code) {
        if ("1000".equals(code)) {
            ToastUtils.show(mContext, "设置成功！", ToastUtils.LENGTH_LONG);
            finish();
        }
    }

    @Override
    public UpdateEnclosureBean getData() {
        UpdateEnclosureBean data = new UpdateEnclosureBean();
        data.setMac(mac);
        data.setState(cbSwitch.isChecked() ? "1" : "0");
        return data;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public String getMac() {
        return mac;
    }


    @Override
    public void onRequestSuccessData(Enclosures data) {
        if (null == data) {
            tvAddress.setText(data.getName());
            cbSwitch.setChecked("1".equals(data.getState()) ? true : false);
            tvDistance.setText(data.getRange() + "米");
            e = Double.parseDouble(data.getE());
            n = Double.parseDouble(data.getN());
            initMap();
        } else {
            tvAddress.setText("");
            cbSwitch.setChecked(false);
            tvDistance.setText("");
            e = 39.963175;
            n = 116.400244;
            initMap();
        }
    }
}
