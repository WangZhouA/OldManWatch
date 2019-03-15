package com.saiyi.oldmanwatch.module.health.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.adapter.TrendAdapter;
import com.saiyi.oldmanwatch.base.BaseMvpFragment;
import com.saiyi.oldmanwatch.entity.TrendListUserBean;
import com.saiyi.oldmanwatch.entity.WeightTrends;
import com.saiyi.oldmanwatch.helper.RetrofitService;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpRequestCallback;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpUtils;
import com.saiyi.oldmanwatch.module.home.mvp.TrendFragmentPresenter;
import com.saiyi.oldmanwatch.utils.Logger;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.TimeUtil;
import com.saiyi.oldmanwatch.utils.WZ_lineChartCopy;
import com.saiyi.oldmanwatch.view.recyclerview.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 体脂称趋势页面
 */
public class TrendFragment extends BaseMvpFragment<TrendFragmentPresenter> implements HttpRequestCallback{
    @BindView(R.id.tv_BarTitle)
    TextView tvTitle;
    @BindView(R.id.tab_layout)
    SegmentTabLayout mTabLayout;
    @BindView(R.id.linechart)
    LineChart mLineChart;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    private Context mContext;
    String[] str = new String[]{"日", "周", "年"};
    private String TypeString = "1";          // 1 日   2 周  3年



    List<Integer> dataList = new ArrayList<>();

    int  uid;

    List<WeightTrends.DataBean> dataListWeight = new ArrayList<>();
    List<TrendListUserBean.DataBean.ParametersBean> dataTrendBeanList = new ArrayList<>();

    private List<String> XDate= new ArrayList<>()  ;

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                // 列表数据刷新
                case 0:

                    dataList.clear();
                    XDate.clear();

                    int size = dataListWeight.size();

                    if (TypeString.equals("1")) {
                        for (int i = 0; i < size; i++) {
                            dataList.add(((int) dataListWeight.get(i).getWeight())*2);
                            XDate.add(dataListWeight.get(i).getCreateDate());
                        }
                    }else if (TypeString.equals("2")) {
                        for (int i = 0; i < size; i++) {
                            dataList.add(((int) dataListWeight.get(i).getWeight())*2);
                            XDate.add(TimeUtil.getLongTime("MM/dd",dataListWeight.get(i).getStart())+"-"+TimeUtil.getLongTime("MM/dd",dataListWeight.get(i).getEnd()));
                        }
                    }else {
                        for (int i = 0; i < size; i++) {
                            dataList.add(((int) dataListWeight.get(i).getWeight())*2);
                            XDate.add(dataListWeight.get(i).getMonth());
                        }


                    }

//                    MpAndroidChartUtils.getInstance(getActivity()).initChart(getActivity(),mLineChart,XDate,dataList);
//                    Log.e("------>","1");
//                    MpAndroidChartUtils.getInstance(getActivity()).showLineChart(mLineChart, dataList, null, getResources().getColor(R.color.appColor));
//                    Log.e("------>","2");

                    setChartDate(dataList);


                    break;

                case 1:

                    trendAdapter.setListData(dataTrendBeanList);

                    break;


            }

        }
    };
    ArrayList<Entry>valuesY = new ArrayList<>();
    private void  setChartDate( List<Integer>  YValue ){
        WZ_lineChartCopy.getInstance().initChartView(getActivity(),mLineChart,XDate);
        valuesY.clear();
        for (int i = 0; i < YValue.size(); i++) {
            valuesY.add(new Entry(i, YValue.get(i)));
        }
        WZ_lineChartCopy.getInstance().initDataDan(valuesY,  mLineChart, Color.parseColor("#34A9FF"));

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trend;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        mContext = getActivity();
        uid =  (int) SharedPreferencesHelper.get("uid", -1);
        mTabLayout.setTabData(str);
        tvTitle.setText(getResources().getString(R.string.trend));
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                // type  1 是日 ，2是周  3 个年

                switch (position){
                    case 0:
                        TypeString="1";
                        okHttp(TypeString,uid);
                        break;

                    case 1:
                        TypeString="2";
                        okHttp(TypeString,uid);
                        break;

                    case 2:
                        TypeString="3";
                        okHttp(TypeString,uid);
                        break;


                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        initData();
        okHttp(TypeString,uid);
//


        new  Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                okHttpQueryList();
            }
        },1000);


    }

    TrendAdapter trendAdapter;
    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0, 1, Color.parseColor("#CBCBCB")));
        trendAdapter=new TrendAdapter(getActivity());
        mRecyclerView.setAdapter(trendAdapter);

    }





    private void  okHttp( String type, int uid){
        Map<String ,String> maps =new HashMap<>();
        maps.put("type",type);
        maps.put("uid",uid+"");
        HttpUtils.getInstance(getActivity()).postAsynHttp(RetrofitService.WEIGHT_USER_PARAMETER,maps,0x01,this);
    }

    private void  okHttpQueryList(){
        Map<String ,String> maps =new HashMap<>();
        maps.put("currPage","1");
        maps.put("size","100");
        maps.put("uid",uid+"");
        HttpUtils.getInstance(getActivity()).postAsynHttp(RetrofitService.QUERYPARAMETERLIST,maps,0x02,this);
    }





    WeightTrends weightTrends;
    TrendListUserBean trendListUserBean;

    @Override
    public void onResponse(String sequest, int type) {
        Gson gson = new Gson();
        switch (type){
            case 0X01:

                Logger.e("-----<111","11");

                weightTrends = gson.fromJson(sequest,WeightTrends.class);

                dataListWeight.clear();
                dataListWeight.addAll(weightTrends.getData());
                if (weightTrends.getData().size()>0) {
                    mHandler.sendEmptyMessageDelayed(0, 100);
                }
                break;

            case 0x02:
                trendListUserBean=gson.fromJson(sequest,TrendListUserBean.class);

                dataTrendBeanList.clear();
                if (trendListUserBean.getData()!=null) {
                    for (int i = 0; i < trendListUserBean.getData().size(); i++) {

                        dataTrendBeanList.addAll(trendListUserBean.getData().get(i).getParameters());
                    }
                    mHandler.sendEmptyMessageDelayed(1, 100);
                }

                break;
        }

    }



    @Override
    public void onFailure(String exp) {

    }

    private String  timeConversion(String timeDate){

        // 2019-01-01 转成  01-01
        String [] timeArray;
        String jointStr="";
        if (!TextUtils.isEmpty(timeDate)){
            timeArray = timeDate.split("-");
            jointStr =timeArray[1]+"-"+timeArray[2];
        }
        return jointStr;
    }


}
