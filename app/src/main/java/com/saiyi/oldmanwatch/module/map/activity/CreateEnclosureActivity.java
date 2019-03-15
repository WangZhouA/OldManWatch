package com.saiyi.oldmanwatch.module.map.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.AddEnclosure;
import com.saiyi.oldmanwatch.http.BaseResponse;
import com.saiyi.oldmanwatch.module.main.SwitchDevicePop;
import com.saiyi.oldmanwatch.mvp.presenter.CreateEnclosurePresenter;
import com.saiyi.oldmanwatch.mvp.view.CreateEnclosureView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/11/8
 * @Describe
 */
public class CreateEnclosureActivity extends BaseMvpAppCompatActivity<CreateEnclosurePresenter> implements SeekBar.OnSeekBarChangeListener, CreateEnclosureView<BaseResponse> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.et_address)
    AutoCompleteTextView etAddress;
    @BindView(R.id.bmapView)
    MapView mMapView;
    @BindView(R.id.sb_safe_location)
    SeekBar mseekbar;
    @BindView(R.id.tv_safe_location)
    TextView tvSafeLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private Context mContext;
    private BaiduMap mBaiduMap;
    private SuggestionSearch mSuggestionSearch;
    private double latitude = 0;
    private double longitude = 0;
    private boolean FLAG = false;
    SwitchDevicePop mPop;
    ArrayAdapter<String> mAdapter;
    List<String> mAddress = new ArrayList<>();
    private String mac;
    private String token;
    private String type;

    @Override
    protected int getLayoutId() {

        if ("2".equals(type)) {
            return R.layout.activity_create_enclosure_position;
        } else {
            return R.layout.activity_create_enclosure;
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        mseekbar.setOnSeekBarChangeListener(this);
        mseekbar.setProgress(200);
        tvSafeLocation.setText("200米");
    }

    @Override
    protected CreateEnclosurePresenter createPresenter() {
        return new CreateEnclosurePresenter(this);
    }

    @Override
    protected void initData() {
        mac = CheckUtils.getMac();
        token = (String) SharedPreferencesHelper.get("token", "");
        type = (String) SharedPreferencesHelper.get("type", "");
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etAddress.getTag() == null) {
                    mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                            .city(BaseApplication.city)
                            .keyword(s.toString())
                    );
                } else {
                    etAddress.setTag(null);
                }


            }
        });
    }


    private void initMap() {
        Marker marker;
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

    @OnClick({R.id.tv_BarLeft, R.id.tv_confirm})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_confirm:
                if (longitude == 0 || latitude == 0) {
                    ToastUtils.show(mContext, "请选择有效地址", ToastUtils.LENGTH_LONG);
                    return;
                }

                mPresenter.addEnclosure(this);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (i <= 200) {
            seekBar.setProgress(200);
            tvSafeLocation.setText("200米");
        } else
            tvSafeLocation.setText(i + "米");
    }

    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(final SuggestionResult res) {
            if (etAddress.getTag() != null) return;
            if (res == null || res.getAllSuggestions() == null) {
                return;
                //未找到相关结果
            }
            mAddress.clear();
            String s = "";
            for (int i = 0; i < res.getAllSuggestions().size(); i++) {
                s = res.getAllSuggestions().get(i).getDistrict() + res.getAllSuggestions().get(i).getKey();
                mAddress.add(s);
            }

            //获取在线建议检索结果
            mAdapter = new ArrayAdapter<String>(mContext, R.layout.item_retrieval, mAddress);
            etAddress.setAdapter(mAdapter);
            etAddress.showDropDown();
            etAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    tvLocation.setText(res.getAllSuggestions().get(i).getCity() + res.getAllSuggestions().get(i).getDistrict() + res.getAllSuggestions().get(i).getKey());
                    etAddress.setTag(false);
                    etAddress.dismissDropDown();
                    longitude = res.getAllSuggestions().get(i).getPt().longitude;
                    latitude = res.getAllSuggestions().get(i).getPt().latitude;
                    initMap();
                }
            });
        }
    };

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }


    @Override
    public AddEnclosure getData() {
        AddEnclosure data = new AddEnclosure();
        data.setMac(mac);
        data.setName(tvLocation.getText().toString());
        data.setRange(tvSafeLocation.getText().toString().substring(0, tvSafeLocation.getText().toString().length() - 1));
        data.setN(latitude + "");
        data.setE(longitude + "");
        return data;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void onRequestSuccessData(BaseResponse data) {
        if ("1000".equals(data.getCode())) {
            ToastUtils.show(mContext, "设置成功！", ToastUtils.LENGTH_LONG);
            finish();
        }
    }
}
