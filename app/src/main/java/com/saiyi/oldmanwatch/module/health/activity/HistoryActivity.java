package com.saiyi.oldmanwatch.module.health.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseAppCompatActivity;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.base.BasePresenter;
import com.saiyi.oldmanwatch.entity.HeartsList;
import com.saiyi.oldmanwatch.entity.QueryHeartsList;
import com.saiyi.oldmanwatch.mvp.presenter.HistoryPresenter;
import com.saiyi.oldmanwatch.mvp.view.HistoryView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
import com.saiyi.oldmanwatch.view.recyclerview.base.ViewHolder;
import com.saiyi.oldmanwatch.view.wheel.OptionsPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/10/30
 * @Describe
 */
public class HistoryActivity extends BaseMvpAppCompatActivity<HistoryPresenter> implements HistoryView<List<HeartsList>> {
    @BindView(R.id.tv_BarLeft)
    TextView tvBarLeft;
    @BindView(R.id.tv_BarTitle)
    TextView tvTitle;
    @BindView(R.id.tv_time_left)
    TextView tvTimeLeft;
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;
    @BindView(R.id.rv_history)
    LRecyclerView mRecyclerView;


    private Context mContext;
    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    private OptionsPickerView mPickerView;
    private String mac;
    private CommonAdapter<HeartsList> mAdapter;
    private List<HeartsList> mData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mContext = this;
        tvBarLeft.setBackgroundResource(R.mipmap.back);
        tvTitle.setText(getResources().getString(R.string.testing_record));
    }

    @Override
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter(this);
    }

    @Override
    protected void initData() {
        mac = CheckUtils.getMac();
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
        tvTimeLeft.setText(year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
        tvTimeRight.setText(year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
        mPresenter.getHeartsList(this);
    }

    @OnClick({R.id.tv_BarLeft, R.id.tv_time_left, R.id.tv_time_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_BarLeft:
                finish();
                break;
            case R.id.tv_time_left:
                mPickerView = new OptionsPickerView(mContext);
                mPickerView.setPicker(years, months, days, false);
                mPickerView.setCyclic(false, false, false);
                mPickerView.show();
                mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(String t1, String t2, String t3) {
                        int month = Integer.parseInt(t2.substring(0, t2.length() - 1));
                        int day = Integer.parseInt(t3.substring(0, t3.length() - 1));
                        tvTimeLeft.setText(t1.substring(0, t1.length() - 1) + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
                        if (tvTimeLeft.getText().toString().compareTo(tvTimeRight.getText().toString()) == -1) {
                            mPresenter.getHeartsList(HistoryActivity.this);
                        } else {
                            ToastUtils.show(mContext, "开始日期不能大于结束日期！", ToastUtils.LENGTH_LONG);
                        }

                    }
                });
                break;
            case R.id.tv_time_right:
                mPickerView = new OptionsPickerView(mContext);
                mPickerView.setPicker(years, months, days, false);
                mPickerView.setCyclic(false, false, false);
                mPickerView.show();
                mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(String t1, String t2, String t3) {
                        int month = Integer.parseInt(t2.substring(0, t2.length() - 1));
                        int day = Integer.parseInt(t3.substring(0, t3.length() - 1));
                        tvTimeRight.setText(t1.substring(0, t1.length() - 1) + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
                        if (tvTimeRight.getText().toString().compareTo(tvTimeLeft.getText().toString()) == 1) {
                            mPresenter.getHeartsList(HistoryActivity.this);
                        } else {
                            ToastUtils.show(mContext, "开始日期不能大于结束日期！", ToastUtils.LENGTH_LONG);
                        }
                    }
                });
                break;
        }
    }


    @Override
    public String getStartTime() {
        return tvTimeLeft.getText().toString();
    }

    @Override
    public String getEndTime() {
        return tvTimeRight.getText().toString();
    }

    @Override
    public String getMac() {
        return mac;
    }

    @Override
    public void onRequestSuccessData(List<HeartsList> data) {
        mData.clear();
        mData.addAll(data);
        mAdapter = new CommonAdapter<HeartsList>(mContext, R.layout.item_history, mData) {
            @Override
            protected void convert(ViewHolder holder, HeartsList dataBean, int position) {
                holder.setText(R.id.tv_time, dataBean.getDate());
                holder.setText(R.id.tv_heart_rate, dataBean.getHeart());
                holder.setText(R.id.tv_high_pressure, dataBean.getHighPressure());
                holder.setText(R.id.tv_low_pressure, dataBean.getLowPressure());
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
