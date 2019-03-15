package com.saiyi.oldmanwatch.module.health.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.saiyi.oldmanwatch.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author liwenbo
 * @Date 18/10/6
 * @Describe
 */
public class SleepFragment extends Fragment {
    @BindView(R.id.ll_left)
    LinearLayout llLeft;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minute)
    TextView tvMinute;
    @BindView(R.id.tv_last_data)
    TextView tvLastData;
    @BindView(R.id.tv_sleep_soundly)
    TextView tvSleepSoundly;
    @BindView(R.id.tv_shallow_sleep)
    TextView tvShallowSleep;
    @BindView(R.id.barchart)
    BarChart mChart;

    private Context mContext;
    private YAxis leftAxis;             //左侧Y轴
    private YAxis rightAxis;            //右侧Y轴
    private XAxis xAxis;                //X轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sleep, container, false);
        ButterKnife.bind(this, view);
        mContext = getActivity();
        initView();
        initData();
        return view;
    }

    private void initView() {

    }

    private void initData() {

    }

    /**
     * 初始化BarChart图表
     */
    private void initBarChart(BarChart barChart) {
        /***图表设置***/
        //背景颜色
        barChart.setBackgroundColor(Color.WHITE);
        //不显示图表网格
        barChart.setDrawGridBackground(false);
        //背景阴影
        barChart.setDrawBarShadow(false);
        barChart.setHighlightFullBarEnabled(false);
        //显示边框
        barChart.setDrawBorders(true);
        //设置动画效果
        barChart.animateY(1000, Easing.EasingOption.Linear);
        barChart.animateX(1000, Easing.EasingOption.Linear);

        /***XY轴的设置***/
        //X轴设置显示位置在底部
        xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);

        leftAxis = barChart.getAxisLeft();
        rightAxis = barChart.getAxisRight();
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        rightAxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
    }

    // 柱状图展示
//    public void showBarChart(List<VtDateValueBean> dateValueList, String name, int color) {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        for (int i = 0; i < dateValueList.size(); i++) {
//            /**
//             * 此处还可传入Drawable对象 BarEntry(float x, float y, Drawable icon)
//             * 即可设置柱状图顶部的 icon展示
//             */
//            BarEntry barEntry = new BarEntry(i, (float) dateValueList.get(i).getFValue());
//            entries.add(barEntry);
//        }
//        // 每一个BarDataSet代表一类柱状图
//        BarDataSet barDataSet = new BarDataSet(entries, name);
//        initBarDataSet(barDataSet, color);
//
////        // 添加多个BarDataSet时
////        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
////        dataSets.add(barDataSet);
////        BarData data = new BarData(dataSets);
//
//        BarData data = new BarData(barDataSet);
//        mChart.setData(data);
//    }

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
//        barDataSet.setValueTextSize(10f);
//        barDataSet.setValueTextColor(color);
    }


}
