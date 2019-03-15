package com.saiyi.oldmanwatch.module.user.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseApplication;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.AddSetupBean;
import com.saiyi.oldmanwatch.entity.SetupBean;
import com.saiyi.oldmanwatch.mvp.presenter.SetupPresenter;
import com.saiyi.oldmanwatch.mvp.view.SetupView;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;
import com.saiyi.oldmanwatch.view.wheel.OptionsPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/11/2
 * @Describe
 */
public class DisturbActivity extends BaseMvpAppCompatActivity<SetupPresenter> implements SetupView<List<SetupBean>> {

    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_BarRight)
    TextView tvBarRight;
    @BindView(R.id.rv_disturb)
    RecyclerView mRecyclerView;

    private Context mContext;
    private String token;
    private String mac;
    private OptionsPickerView mPickerView;
    private List<SetupBean> mDatas;
    private CommonAdapter<SetupBean> mAdapter;
    private ArrayList<String> hours = new ArrayList<>();
    private ArrayList<String> minute = new ArrayList<>();
    private String type;

    @Override
    protected int getLayoutId() {
        type = getIntent().getStringExtra("type");
        if ("2".equals(type))
            return R.layout.activity_disturb_position;
        else
            return R.layout.activity_disturb;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvTitle.setText(R.string.set_disturb);
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvBarRight.setText(R.string.save);
    }


    @Override
    protected SetupPresenter createPresenter() {
        return new SetupPresenter(this);
    }

    @Override
    protected void initData() {
        mac = BaseApplication.mac;
        token = (String) SharedPreferencesHelper.get("token", "");

        hours.addAll(Arrays.asList(getResources().getStringArray(R.array.hour)));
        minute.addAll(Arrays.asList(getResources().getStringArray(R.array.minute)));
        mPresenter.getSetupList(this);
    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_BarRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_BarRight:
                mPresenter.addSetup(this);
                break;
        }
    }


    @Override
    public void getSuccess(String code) {
        if ("1000".equals(code)) {
            ToastUtils.show(mContext, "设置成功", ToastUtils.LENGTH_LONG);
            finish();
        }
    }

    @Override
    public AddSetupBean getData() {
        AddSetupBean data = new AddSetupBean();
        data.setMac(getMac());
        data.setData(mDatas);
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
    public void onRequestSuccessData(List<SetupBean> data) {
        mDatas = new ArrayList<>();
        SetupBean setup = null;
        if (data == null || data.size() == 0) {
            for (int i = 1; i < 4; i++) {
                setup = new SetupBean();
                setup.setStartDate("");
                setup.setEndDate("");
                setup.setTypeName("时段" + i);
                mDatas.add(setup);
            }
        } else {
            for (int i = 0; i < data.size(); i++) {
                setup = new SetupBean();
                setup.setStartDate(data.get(i).getStartDate());
                setup.setEndDate(data.get(i).getEndDate());
                setup.setTypeName(data.get(i).getTypeName());
                mDatas.add(setup);
            }
        }
        mAdapter = new CommonAdapter<SetupBean>(mContext, R.layout.item_disturb, mDatas) {
            @Override
            protected void convert(ViewHolder holder, SetupBean setupBean, final int position) {
                holder.setText(R.id.tv_title, "时段" + setupBean.getTypeName());
                holder.setText(R.id.tv_start_time, ("".equals(setupBean.getStartDate())) ? getResources().getString(R.string.not_set) : setupBean.getStartDate());
                holder.setText(R.id.tv_end_time, ("".equals(setupBean.getEndDate())) ? getResources().getString(R.string.not_set) : setupBean.getEndDate());
                holder.setOnClickListener(R.id.tv_start_time, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPickerView = new OptionsPickerView(mContext);
                        mPickerView.setPicker(hours, minute, false);
                        mPickerView.setCyclic(true, true, false);
                        mPickerView.setItemsVisible(7);
                        mPickerView.show();
                        mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(String t1, String t2, String t3) {
                                mDatas.get(position).setStartDate(t1 + ":" + t2);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });
                holder.setOnClickListener(R.id.tv_end_time, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPickerView = new OptionsPickerView(mContext);
                        mPickerView.setPicker(hours, minute, false);
                        mPickerView.setCyclic(true, true, false);
                        mPickerView.setItemsVisible(7);
                        mPickerView.show();
                        mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                            @Override
                            public void onOptionsSelect(String t1, String t2, String t3) {
                                mDatas.get(position).setEndDate(t1 + ":" + t2);
                                notifyDataSetChanged();
                            }
                        });
                    }
                });

            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }
}
