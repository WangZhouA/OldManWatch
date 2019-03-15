package com.saiyi.oldmanwatch.utils;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.saiyi.oldmanwatch.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 最帅的男人 on 2019/3/11.
 */
public class MpAndroidChartUtils {

    private static volatile MpAndroidChartUtils instance=null;
    public static  MpAndroidChartUtils getInstance(Context context){
        if(instance==null){
            synchronized(MpAndroidChartUtils.class){
                if(instance==null){
                    instance=new MpAndroidChartUtils ();
                }
            }
        }
        return instance;
    }

    private  MpAndroidChartUtils( ){
    }


    /**
     * 初始化图表
     */
    public void initChart(Context context, LineChart lineChart, List<String> xValueDate, List<Integer> dataList) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(context.getResources().getColor(R.color.lineChart_bg));
        //设置是否可以缩放 x和y，默认true
        lineChart.setScaleEnabled(false);
        //设置是否可以通过双击屏幕放大图表。默认是true
        lineChart.setDoubleTapToZoomEnabled(false);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(true);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //设置图表的描述文字 默认会显示在图表的右下角
        lineChart.getDescription().setEnabled(false);

        lineChart.setViewPortOffsets(100, 50, 50, 100);

        //设置XY轴动画效果
        lineChart.setPinchZoom(false);
        lineChart.animateY(2500);
        lineChart.animateX(1500);
        /***XY轴的设置***/
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setAxisMaximum(dataList.size() - 1);

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                if (index < 0 || index >= xValueDate.size()) {
                    return "";
                } else {
                    return xValueDate.get(index);
                }
            }
        });

        //设置显示数据个数
        lineChart.setVisibleXRange(0, 5);
        //设置跳转位置
//        lineChart.moveViewToX(dataList.size() - 5);
        lineChart.moveViewToX(0);
        YAxis leftYAxis;            //左侧Y轴
        YAxis rightYaxis;
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        //      不绘制网格线
        xAxis.setDrawGridLines(false);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYaxis.setAxisMinimum(0f);
        leftYAxis.setDrawAxisLine(false);
        leftYAxis.setEnabled(false);
        rightYaxis.setEnabled(false);
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        /***折线图例 标签 设置***/
        Legend legend;
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.NONE);//不显示图例
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(true);
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
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setDrawValues(true);

        //不显示点
        lineDataSet.setDrawCircles(true);
        //设置折线图填充
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.LINEAR);
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


        List<Entry> entries =new ArrayList<>();
        entries.clear();
        for (int i = 0; i < dataList.size(); i++) {
            Integer data = dataList.get(i);
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i,  data);
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, null);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }


}
