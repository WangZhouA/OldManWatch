package com.saiyi.oldmanwatch.module.health.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpFragment;
import com.saiyi.oldmanwatch.entity.QueryStepBean;
import com.saiyi.oldmanwatch.entity.StepBean;
import com.saiyi.oldmanwatch.mvp.presenter.StepPresenter;
import com.saiyi.oldmanwatch.mvp.view.StepView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.TimeUtils;
import com.saiyi.oldmanwatch.view.DHealthyProgressView;
import com.saiyi.oldmanwatch.view.wheel.OptionsPickerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author liwenbo
 * @Date 18/10/5
 * @Describe
 */
public class StepFragment extends BaseMvpFragment<StepPresenter> implements StepView<StepBean> {

    @BindView(R.id.ll_left)
    LinearLayout llLeft;
    @BindView(R.id.iv_time)
    ImageView ivTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.simple)
    DHealthyProgressView dHPView;
    @BindView(R.id.tv_step_num)
    TextView tvStepNum;
    @BindView(R.id.barchart)
    BarChart mChart;

    private Context mContext;
    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    private OptionsPickerView mPickerView;
    private String mac;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_step;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        mContext = getActivity();
        dHPView.setmValue(100);
        initData();
        mPresenter.getStep(this);
    }


    private void initData() {
        initBarChart(mChart);
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
        tvTime.setText(year + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));

    }

    @Override
    protected StepPresenter createPresenter() {
        return new StepPresenter(this);
    }

    @OnClick({R.id.ll_left, R.id.iv_time, R.id.tv_time, R.id.ll_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.iv_time:
            case R.id.tv_time:
                mPickerView = new OptionsPickerView(mContext);
                mPickerView.setPicker(years, months, days, false);
                mPickerView.setItemsVisible(7);
                mPickerView.setCyclic(false, false, false);
                mPickerView.show();
                mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(String t1, String t2, String t3) {
                        int month = Integer.parseInt(t2.substring(0, t2.length() - 1));
                        int day = Integer.parseInt(t3.substring(0, t3.length() - 1));
                        tvTime.setText(t1.substring(0, t1.length() - 1) + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
                    }
                });
                mPresenter.getStep(this);
                break;
            case R.id.ll_left:
                tvTime.setText(TimeUtils.getDay(tvTime.getText().toString(), -1));
                mPresenter.getStep(this);
                break;
            case R.id.ll_right:
                tvTime.setText(TimeUtils.getDay(tvTime.getText().toString(), 1));
                mPresenter.getStep(this);
                break;
        }
    }

    /**
     * 初始化BarChart图表
     */
    private void initBarChart(BarChart barChart) {
        /***图表设置***/
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        // 启用/禁用与图表的所有可能的触摸交互。
        barChart.setTouchEnabled(false);
        //设置图表的描述文字 默认会显示在图表的右下角
        barChart.getDescription().setEnabled(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        barChart.setPinchZoom(false);//挤压缩放
        //双击缩放
        barChart.setDoubleTapToZoomEnabled(false);
        //显示边框
        barChart.setDrawBorders(false);
        //设置动画效果
        barChart.animateY(1000, Easing.EasingOption.Linear);
        barChart.animateX(1000, Easing.EasingOption.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setGranularity(1f);

//      不绘制网格线
        xAxis.setDrawGridLines(false);

        leftAxis = barChart.getAxisLeft();
        rightAxis = barChart.getAxisRight();
        leftAxis.enableGridDashedLine(10f, 15f, 0f);
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setEnabled(false);
        leftAxis.setDrawAxisLine(false);
        /***折线图例 标签 设置***/
        legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //不显示图例
        legend.setForm(Legend.LegendForm.NONE);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
    }

    // 柱状图展示
    public void showBarChart(List<StepBean.StepsBean> dateValueList, String name, int color) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        String[] xValues = new String[dateValueList.size()];
        for (int i = 0; i < dateValueList.size(); i++) {
            /**
             * 此处还可传入Drawable对象 BarEntry(float x, float y, Drawable icon)
             * 即可设置柱状图顶部的 icon展示
             */
            BarEntry barEntry = new BarEntry(i, Float.parseFloat(dateValueList.get(i).getSteps()));
            String date = dateValueList.get(i).getDate();
            xValues[i] = date.substring(date.length() - 2, date.length()) + "日";
            entries.add(barEntry);
        }
        // 每一个BarDataSet代表一类柱状图
        BarDataSet barDataSet = new BarDataSet(entries, name);
        initBarDataSet(barDataSet, color);

        IAxisValueFormatter xAxisFormatter = new MyIndexAxisValueFormatter(xValues);
        xAxis.setLabelCount(xValues.length);
        xAxis.setValueFormatter(xAxisFormatter);
//        // 添加多个BarDataSet时
//        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//        dataSets.add(barDataSet);
//        BarData data = new BarData(dataSets);
        BarData data = new BarData(barDataSet);
        mChart.setData(data);
    }

    /**
     * 柱状图始化设置 一个BarDataSet 代表一列柱状图
     *
     * @param barDataSet 柱状图
     * @param color      柱状图颜色
     */
    private void initBarDataSet(BarDataSet barDataSet, int color) {
        barDataSet.setColor(color);
        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.f);
        //不显示柱状图顶部值
        barDataSet.setDrawValues(false);
        barDataSet.setValueTextSize(10f);
        barDataSet.setValueTextColor(color);
    }


    @Override
    public String getMac() {
        return mac;
    }

    @Override
    public String getStartTime() {
        return tvTime.getText().toString();
    }

    @Override
    public String getEndTime() {
        return null;
    }

    @Override
    public void onRequestSuccessData(StepBean data) {
        tvStepNum.setText(data.getDayStep());
        showBarChart(data.getSteps(), null, getResources().getColor(R.color.blue));
    }

    public class MyIndexAxisValueFormatter extends IndexAxisValueFormatter {
        private String[] xValues;

        public MyIndexAxisValueFormatter(String[] values) {
            super(values);
            this.xValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int index = Math.round(value);

            if (index == 0 || index == xValues.length - 1)
                return xValues[(int) (value % xValues.length)];
            else return "";
        }
    }


}
