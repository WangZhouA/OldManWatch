package com.saiyi.oldmanwatch.module.health.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @Author liwenbo
 * @Date 18/11/6
 * @Describe
 */
public class SelectFamilyActivity extends BaseAppCompatActivity {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvBarTitle;
    @BindView(R.id.rv_family)
    RecyclerView mRecyclerView;


    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_family;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarTitle.setText(R.string.select_family);
        mContext = this;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_BarLeft})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
        }
    }
}
