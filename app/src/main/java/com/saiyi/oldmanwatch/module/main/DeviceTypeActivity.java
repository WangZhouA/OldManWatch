package com.saiyi.oldmanwatch.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.DevicesTypeBean;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 设备类型页面
 */
public class DeviceTypeActivity extends BaseMvpAppCompatActivity {
    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.rv_device_type)
    RecyclerView mRecyclerView;

    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_type;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(R.string.select_device_type);
        mContext = this;

        tvBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void initData() {
        List<DevicesTypeBean> mDatas = new ArrayList<>();
        DevicesTypeBean data = null;
        for (int i = 0; i < 3; i++) {
            data = new DevicesTypeBean();
            if (i == 0) {
                data.setResId(R.mipmap.equipment_watch1);
                data.setTitle("老人手表");
            } else if (i == 1) {
                data.setResId(R.mipmap.equipment_locator1);
                data.setTitle("定位器");
            } else if (i == 2) {
                data.setResId(R.mipmap.equipment_scale1);
                data.setTitle("体脂称");
            }
            mDatas.add(data);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(new CommonAdapter<DevicesTypeBean>(mContext, R.layout.item_device_type, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final DevicesTypeBean devicesTypeBean, int position) {
                holder.setImageResource(R.id.iv_type, devicesTypeBean.getResId());
                holder.setText(R.id.tv_type, devicesTypeBean.getTitle());
                holder.setOnClickListener(R.id.item_home, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, BindingDeviceActivity.class);
                        intent.putExtra("title", devicesTypeBean.getTitle());
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
