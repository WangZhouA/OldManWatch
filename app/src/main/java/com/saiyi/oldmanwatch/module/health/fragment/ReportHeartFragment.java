package com.saiyi.oldmanwatch.module.health.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.base.BaseMvpFragment;
import com.saiyi.oldmanwatch.entity.HeartsList;
import com.saiyi.oldmanwatch.entity.QueryHeartsList;
import com.saiyi.oldmanwatch.mvp.presenter.HistoryPresenter;
import com.saiyi.oldmanwatch.mvp.view.HistoryView;
import com.saiyi.oldmanwatch.utils.CheckUtils;
import com.saiyi.oldmanwatch.utils.ToastUtils;
import com.saiyi.oldmanwatch.view.CircleImageView;
import com.saiyi.oldmanwatch.view.recyclerview.CommonAdapter;
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
 * @Date 18/11/6
 * @Describe
 */
public class ReportHeartFragment extends BaseMvpFragment<HistoryPresenter> implements HistoryView<List<HeartsList>> {

    @BindView(R.id.civ_pic)
    CircleImageView civPic;
    @BindView(R.id.tv_relation)
    TextView tvRelation;
    @BindView(R.id.tv_time_left)
    TextView tvTimeLeft;
    @BindView(R.id.tv_time_right)
    TextView tvTimeRight;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.lineChart1)
    LineChart lineChart2;


    private Context mContext;
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线
    private String mac;
    private ArrayList<String> years = new ArrayList<>();
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> days = new ArrayList<>();
    private OptionsPickerView mPickerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_heart;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        mContext = getActivity();
        initChart(lineChart);
        initChart(lineChart2);
        initData();
    }

    @Override
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter(this);
    }

    private void initData() {
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
//        QueryHeartsList data = new QueryHeartsList();
//        data.setMac(mac);
//        data.setStartDate(tvTimeLeft.getText().toString());
//        data.setEndDate(tvTimeRight.getText().toString());
//        mPresenter.queryHeartsList(data);
        /**
         * 测试数据
         */
        List<Integer> dataList = new ArrayList<>();
        List<Integer> dataList1 = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Integer data = (20 + (i % 2 == 0 ? 0 : i));
            Integer data1 = (20 + (i % 2 == 0 ? 0 : i));
            dataList.add(data);
            dataList1.add(data1);
        }
        // -------结束------------
        showLineChart(lineChart, dataList, null, getResources().getColor(R.color.appColor));
        showLineChart(lineChart2, dataList1, null, getResources().getColor(R.color.heart_violet_start));
        addLine(lineChart2, dataList1, null, getResources().getColor(R.color.heart_sky_blue_start));
        Drawable drawable = getResources().getDrawable(R.drawable.linechart_color);
        Drawable drawable2 = getResources().getDrawable(R.drawable.hypotension_color);
        setChartFillDrawable(lineChart, drawable);
        setChartFillDrawable(lineChart2, drawable2);

    }

    @OnClick({R.id.tv_time_left, R.id.tv_time_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_time_left:
                mPickerView = new OptionsPickerView(mContext);
                mPickerView.setPicker(years, months, days, false);
                mPickerView.setCyclic(false, false, false);
                mPickerView.setItemsVisible(7);
                mPickerView.show();
                mPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(String t1, String t2, String t3) {
                        int month = Integer.parseInt(t2.substring(0, t2.length() - 1));
                        int day = Integer.parseInt(t3.substring(0, t3.length() - 1));
                        tvTimeLeft.setText(t1.substring(0, t1.length() - 1) + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
                        if (tvTimeLeft.getText().toString().compareTo(tvTimeRight.getText().toString()) == -1) {
                            mPresenter.getHeartsList(ReportHeartFragment.this);
                        } else {
                            ToastUtils.show(mContext, "开始日期不能大于结束日期！", ToastUtils.LENGTH_LONG);
                        }

                    }
                });
                break;
            case R.id.tv_time_right:
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
                        tvTimeRight.setText(t1.substring(0, t1.length() - 1) + "-" + (month > 9 ? month : "0" + month) + "-" + (day > 9 ? day : "0" + day));
                        mPresenter.getHeartsList(ReportHeartFragment.this);
                        if (tvTimeRight.getText().toString().compareTo(tvTimeLeft.getText().toString()) == 1) {
                        } else {
                            ToastUtils.show(mContext, "开始日期不能大于结束日期！", ToastUtils.LENGTH_LONG);
                        }
                    }
                });
                break;
        }
    }

    /**
     * 初始化图表
     */
    private void initChart(LineChart lineChart) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(false);
        //设置图表的描述文字 默认会显示在图表的右下角
        lineChart.getDescription().setEnabled(false);
        //设置XY轴动画效果
        lineChart.setPinchZoom(false);
        lineChart.animateY(2500);
        lineChart.animateX(1500);
        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();

        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //      不绘制网格线
        xAxis.setDrawGridLines(false);

        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYaxis.setAxisMinimum(0f);
        leftYAxis.setDrawAxisLine(false);
        rightYaxis.setEnabled(false);
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.NONE);//不显示图例
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
    }

    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setDrawValues(false);
        //不显示点
        lineDataSet.setDrawCircles(false);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    /**
     * 展示曲线
     *
     * @param lineChart 曲线图
     * @param dataList  数据集合
     * @param name      曲线名称
     * @param color     曲线颜色
     */
    public void showLineChart(LineChart lineChart, List<Integer> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            Integer data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data);
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }


    /**
     * 设置线条填充背景颜色
     *
     * @param drawable
     */
    public void setChartFillDrawable(LineChart lineChart, Drawable drawable) {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            lineChart.invalidate();
        }
    }

    /**
     * 添加曲线
     */
    private void addLine(LineChart lineChart, List<Integer> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Integer data = (10 + (i % 2 == 0 ? 0 : i));
            dataList.add(data);
            Entry entry = new Entry(i, (float) data);
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);
        lineChart.getLineData().addDataSet(lineDataSet);
        lineChart.invalidate();
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

    }
}
