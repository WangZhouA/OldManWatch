package com.saiyi.oldmanwatch.module.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.adapter.AbsBaseAdapter;
import com.saiyi.oldmanwatch.adapter.BobyUserListAdapter;
import com.saiyi.oldmanwatch.adapter.BodyFatScaleAdapter;
import com.saiyi.oldmanwatch.base.BaseMvpFragment;
import com.saiyi.oldmanwatch.entity.AllDeviceBean;
import com.saiyi.oldmanwatch.entity.BodyFatScaleBean;
import com.saiyi.oldmanwatch.entity.BodyTrend;
import com.saiyi.oldmanwatch.entity.bodyUserListBean;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpRequestCallback;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpUtils;
import com.saiyi.oldmanwatch.module.home.mvp.bodyFatScalePresenter;
import com.saiyi.oldmanwatch.module.home.mvp.view.BobyFatView;
import com.saiyi.oldmanwatch.module.scale.AddUserActivity;
import com.saiyi.oldmanwatch.module.user.activity.PerfectPersonalAvtivity;
import com.saiyi.oldmanwatch.utils.MyDialog;
import com.saiyi.oldmanwatch.utils.PopupUtilsList;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.utils.Util;
import com.saiyi.oldmanwatch.view.BMIView;
import com.saiyi.oldmanwatch.view.CircleImageView;
import com.saiyi.oldmanwatch.view.MyRecyclerView;
import com.saiyi.oldmanwatch.view.recyclerview.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.saiyi.oldmanwatch.helper.RetrofitService.QUERYUSERPARAMETER;
import static com.saiyi.oldmanwatch.helper.RetrofitService.QUERY_ALL_DEVICE_LIST;
import static com.saiyi.oldmanwatch.helper.RetrofitService.QUERY_USER_LIST;
import static com.saiyi.oldmanwatch.helper.RetrofitService.USER_INFO;

/**
 * 体脂称首页
 */
public class BodyFatScaleFragment extends BaseMvpFragment<bodyFatScalePresenter> implements BobyFatView<BodyTrend>,HttpRequestCallback {
    /**
     * 头像
     */
    @BindView(R.id.civ_pic)
    CircleImageView civPic;
    /**
     * 使用者名字
     */
    @BindView(R.id.tv_use_name)
    TextView tvName;
    /**
     * 体重
     */
    @BindView(R.id.tv_body_weight)
    TextView tvBodyWeight;
    /**
     * 身体得分
     */
    @BindView(R.id.tv_body_score)
    TextView tvBodyScore;
    /**
     * 体型
     */
    @BindView(R.id.tv_body_type)
    TextView tvBodyType;
    /**
     * 指数列表
     */
    @BindView(R.id.recyclerView)
    MyRecyclerView mRecyclerView;
    /**
     * 横向进度条
     */
    @BindView(R.id.BMIView1)
    BMIView mBmiView;
    /**
     * 上下文
     */
    private Context mContext;
    MyDialog myDialog;
    private BodyFatScaleAdapter mAdapter;
    private List<BodyFatScaleBean> mDatas = new ArrayList<>();
    private List<String> titles;



    private BobyUserListAdapter bobyUserListAdapter;


    private List<AllDeviceBean> allDeviceBeans=new ArrayList<>();


    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                //查询最后一次检查的数据
                case 0:
                    for (int i=0; i<mDatas.size();i++){
                        BodyFatScaleBean bodyFatScaleBean =mDatas.get(i) ;

//         <item>体脂率(%)</item>
//        <item>肌肉率(%)</item>
//        <item>水含量(%)</item>
//        <item>骨盐量(kg)</item>
//        <item>内脏脂肪等级</item>
//        <item>基础代谢</item>
//        <item>肌肉重(kg)</item>
//        <item>BMI</item>
//        <item>蛋白质</item>

                        switch (bodyFatScaleBean.getTitle()){
                            case "体脂率(%)":
                                bodyFatScaleBean.setFigure(dataBean.getData().getBodyFatRate()+"");
                                bodyFatScaleBean.setType(dataBean.getData().getBodyFatRateReference()+"");

                                break;
                            case "肌肉率(%)":
                                bodyFatScaleBean.setFigure(dataBean.getData().getMuscleRate()+"");
                                bodyFatScaleBean.setType(dataBean.getData().getMuscleRateReference()+"");
                                break;
                            case "水含量(%)":
                                bodyFatScaleBean.setFigure(dataBean.getData().getWaterContent()+"");
                                bodyFatScaleBean.setType(dataBean.getData().getWaterContentReference()+"");
                                break;
                            case "骨盐量(kg)":
                                bodyFatScaleBean.setFigure(dataBean.getData().getBoneSalt()+"");
                                bodyFatScaleBean.setType(dataBean.getData().getBoneSaltReference()+"");
                                break;
                            case "内脏脂肪等级":
                                bodyFatScaleBean.setFigure(dataBean.getData().getFatLevel()+"");
                                bodyFatScaleBean.setType(dataBean.getData().getFatLevelReference()+"");
                                break;
                            case "基础代谢":
                                bodyFatScaleBean.setFigure(dataBean.getData().getBasalMetabolism()+"");
                                bodyFatScaleBean.setType(dataBean.getData().getBasalMetabolismReference()+"");
                                break;
                            case "肌肉重(kg)":
                                bodyFatScaleBean.setFigure(dataBean.getData().getMuscle()+"");
                                bodyFatScaleBean.setType(dataBean.getData().getMuscleReference()+"");
                                break;
                            case "BMI":
                                bodyFatScaleBean.setFigure(dataBean.getData().getBmi()+""); //BMI
                                bodyFatScaleBean.setType(dataBean.getData().getBmiReference()+""); //BMI
                                break;
                            case "蛋白质":
                                bodyFatScaleBean.setFigure(dataBean.getData().getProtein()+""); //BMI
                                bodyFatScaleBean.setType(dataBean.getData().getProteinReference()+""); //BMI
                                break;


                        }

//                        if (bodyFatScaleBean.getTitle().equals(titles.get(0))){ // 体脂率
//                            Log.e("---->getBodyFatRate",dataBean.getData().getBodyFatRate()+"");
//                            bodyFatScaleBean.setFigure(dataBean.getData().getBodyFatRate()+"");
//                            bodyFatScaleBean.setType(dataBean.getData().getBodyFatRateReference()+"");
//
//                        }else if (bodyFatScaleBean.getTitle().equals(titles.get(1))){   //肌肉率
//                            bodyFatScaleBean.setFigure(dataBean.getData().getMuscleRate()+"");
//                            bodyFatScaleBean.setType(dataBean.getData().getMuscleRateReference()+"");
//
//                        }else if (bodyFatScaleBean.getTitle().equals(titles.get(2))){   //水含量
//                            bodyFatScaleBean.setFigure(dataBean.getData().getWaterContent()+"");
//                            bodyFatScaleBean.setType(dataBean.getData().getWaterContentReference()+"");
//                        }else if (bodyFatScaleBean.getTitle().equals(titles.get(3))){   //骨盐量
//                            bodyFatScaleBean.setFigure(dataBean.getData().getBoneSalt()+"");
//                            bodyFatScaleBean.setType(dataBean.getData().getBoneSaltReference()+"");
//                        }else if (bodyFatScaleBean.getTitle().equals(titles.get(4))){   //内脏脂肪等级
//                            bodyFatScaleBean.setFigure(dataBean.getData().getFatLevel()+"");
//                            bodyFatScaleBean.setType(dataBean.getData().getFatLevelReference()+"");
//                        }else if (bodyFatScaleBean.getTitle().equals(titles.get(5))){  //基础代谢
//                            bodyFatScaleBean.setFigure(dataBean.getData().getBasalMetabolism()+"");
//                            bodyFatScaleBean.setType(dataBean.getData().getBasalMetabolismReference()+"");
//
//                        }else if (bodyFatScaleBean.getTitle().equals(titles.get(6))){   //肌肉重
//                            bodyFatScaleBean.setFigure(dataBean.getData().getMuscle()+"");
//                            bodyFatScaleBean.setType(dataBean.getData().getMuscleReference()+"");
//                        }else if (bodyFatScaleBean.getTitle().equals(titles.get(7))){
//                            bodyFatScaleBean.setFigure(dataBean.getData().getBmi()+""); //BMI
//                            bodyFatScaleBean.setType(dataBean.getData().getBmiReference()+""); //BMI
//
//                        }else {
//                            bodyFatScaleBean.setFigure(dataBean.getData().getProtein()+""); //BMI
//                            bodyFatScaleBean.setType(dataBean.getData().getProteinReference()+""); //BMI
//                        }
                    }


                    tvBodyWeight.setText((dataBean.getData().getWeight()*2)+"");// 体重
                    if (TextUtils.isEmpty(dataBean.getData().getScore())){

                    }else {
                        tvBodyScore.setText(dataBean.getData().getScore() + "");  //身体得分
                    }
                    if (dataBean.getData().getSize()==1) {
                        tvBodyType.setText("偏瘦");
                    }else if (dataBean.getData().getSize()==2) {
                        tvBodyType.setText("正常");
                    }else {
                        tvBodyType.setText("偏胖");
                    }


//                    这个自定义view
                    if (dataBean.getData().getBmi().contains("23") || dataBean.getData().getBmi().contains("24") ){
                        mBmiView.setCurrVal(23);
                        mBmiView.setCurrStr("24");
                    }else {
                        mBmiView.setCurrVal(Float.valueOf(dataBean.getData().getBmi()));
                        mBmiView.setCurrStr(dataBean.getData().getBmi() + "");
                    }
                    mAdapter.notifyDataSetChanged();
                    break;

                case 1:

                    showDialog();

                    break;
                case 2:


                    if (Util.ifInclude(queryInformation,"0")==true){


                        Intent intent =new Intent();
                        intent.setClass(mContext, PerfectPersonalAvtivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("age", age);
                        intent.putExtra("sex", sex);
                        intent.putExtra("phone",phone);
                        intent.putExtra("headUrl",headUrl);
                        intent.putExtra("birthday", birthday);
                        intent.putExtra("height", height);
                        mContext.startActivity(intent);

                    }else {



                    }


                    break;

                case 3:





                    break;




            }
        }
    };




    @Override
    protected bodyFatScalePresenter createPresenter() {
        return new bodyFatScalePresenter(this);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_body_fat_scale;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        mContext = getActivity();
        mRecyclerView.setFocusable(false);
        initData();

        tvName.setText("");
        mPresenter.querybodyFatScalePresenter(this,uid());


        HttpUtils.getInstance(getActivity()).getSyncHttp(0x03,USER_INFO +  (int) SharedPreferencesHelper.get("uid", 0),this);

        HttpUtils.getInstance(getActivity()).getSyncHttp(0x04,QUERY_ALL_DEVICE_LIST +  (int) SharedPreferencesHelper.get("uid", 0),this);


    }



    private void initData() {

        bobyUserListAdapter =  new BobyUserListAdapter(getActivity());
        titles = new ArrayList<>();
        titles.addAll(Arrays.asList(getResources().getStringArray(R.array.body_fat_index)));
        BodyFatScaleBean data = null;
        for (int i = 0; i < titles.size(); i++) {
            data = new BodyFatScaleBean();
            data.setType((1) + "");
            data.setTitle(titles.get(i));
            data.setFigure("0");
            mDatas.add(data);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(1, 1, Color.parseColor("#CBCBCB")));
        mAdapter = new BodyFatScaleAdapter(mDatas, mContext);

        bobyUserListAdapter.setOnItemClickListener(new AbsBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object bean, int position) {


                myDialog.dismiss();
                QuerybodyFatScalePresenter(dateUserBean.get(position).getId());
                tvName.setText(dateUserBean.get(position).getName());


            }
        });


        mRecyclerView.setAdapter(mAdapter);







    }


    @OnClick({R.id.tv_use_name,R.id.civ_pic})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_use_name:
                okHttpQueryUserList();
                break;

            case R.id.civ_pic:
                if (allDeviceBeans.size()>0){

                    PopupUtilsList.getInstance(getActivity()).initDate(getActivity(),allDeviceBeans);
                    PopupUtilsList.getInstance(getActivity()).showAsDrop(civPic);
                }

                // 默认的值
//                PopupUtils.getInstance(getActivity()).showAsDrop(civPic);

                break;

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferencesHelper.put(SharedPreferencesHelper.DEVICETYPE,3);

        okHttpQueryUserInfo();

    }



    private void showDialog() {
        //模拟数据
//
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_select_use_name, null);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels-50;         // 屏幕宽度（像素）
        myDialog = new MyDialog(mContext,  view, R.style.MyDialog, 0,width);
        MyRecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        TextView tv_add = view.findViewById(R.id.tv_add_member);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(mContext, AddUserActivity.class);
                mContext.startActivity(intent);
                myDialog.dismiss();
            }
        });


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(1, 1, Color.parseColor("#CBCBCB")));

        bobyUserListAdapter.setListData(dateUserBean);
        mRecyclerView.setAdapter(bobyUserListAdapter);

        myDialog.show();



    }
    //查询用称的成员列表
    private void okHttpQueryUserList() {
        HttpUtils.getInstance(getActivity()).getSyncHttp(0X01,QUERY_USER_LIST+uid(),this);

    }

    //查询用称的成员列表
    private void okHttpQueryUserInfo() {
        HttpUtils.getInstance(getActivity()).getSyncHttp(0X02,QUERYUSERPARAMETER+uid(),this);

    }

    @Override
    public int uid() {
        return  (int) SharedPreferencesHelper.get("uid", -1);

    }

    @Override
    public void getDevices(BodyTrend bodyTrend) {
        dataBean= bodyTrend;
        if (dataBean.getData()!=null) {
            mHandler.sendEmptyMessageDelayed(0, 100);
        }

    }

    @Override
    public String token() {
        return  (String) SharedPreferencesHelper.get("token", "");
    }


    @Override
    public void onRequestSuccessData(BodyTrend data) {

    }

    BodyTrend dataBean;
    List<bodyUserListBean>dateUserBean=new ArrayList<>();

    bodyUserListBean bean;

    List<String> queryInformation = new ArrayList<>(); // 查询用户信息是否完整。1，代表 完整  0 代表不完整。

    String name;
    String age;
    String sex;
    String phone;
    String headUrl;
    String birthday;
    String height;


    @Override
    public void onResponse(String sequest, int type) {

        switch (type){
            case 0x01:

                Log.e("----->成功？","true");

                try {
                    JSONObject object =new JSONObject(sequest);
                    JSONArray array= object.getJSONArray("data");
                    dateUserBean.clear();
                    for (int i=0;i<array.length();i++){
                        bean =new bodyUserListBean();
                        JSONObject ob = array.getJSONObject(i);
                        bean.setName(ob.getString("name"));
                        Log.e("http name",ob.getString("name"));
                        Log.e("http url",ob.getString("headUrl"));
                        bean.setHeadUrl(ob.getString("headUrl"));
                        bean.setId(ob.getInt("id"));
                        dateUserBean.add(bean);
                    }

                    mHandler.sendEmptyMessageDelayed(1,0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;

            case 0x02:


                Gson gson =new Gson();
                dataBean = gson.fromJson(sequest,BodyTrend.class);
                if (dataBean.getData()!=null) {

                    mHandler.sendEmptyMessageDelayed(0, 100);
                }
                break;


            case  0x03:
                queryInformation.clear();


                try {
                    JSONObject object = new JSONObject(sequest);
                    JSONObject itemOj = object.getJSONObject("data");

                    Log.e("http", itemOj.getString("name"));

                    name=itemOj.getString("name");
                    age=itemOj.getString("age");
                    sex=itemOj.getString("sex");
                    phone=itemOj.getString("phone");
                    headUrl=itemOj.getString("headUrl");
                    birthday=itemOj.getString("birthday");
                    height=itemOj.getString("height");


                    if (TextUtils.isEmpty(sex)){
                        queryInformation.add("0");
                    }else {
                        queryInformation.add("1");
                    }
                    if (TextUtils.isEmpty(birthday)){
                        queryInformation.add("0");
                    }else {
                        queryInformation.add("1");
                    }
                    if (TextUtils.isEmpty(height)){
                        queryInformation.add("0");
                    }else {
                        queryInformation.add("1");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mHandler.sendEmptyMessageDelayed(2,100);


                break;

            case 0x04:

                allDeviceBeans.clear();
                try {
                    JSONObject object = new JSONObject(sequest);
                    JSONArray  array = object.getJSONArray("data");
                    for (int i=0;i<array.length();i++){

                        AllDeviceBean allDeviceBean =new AllDeviceBean();
                        JSONObject itemOj = array.getJSONObject(i);
                        allDeviceBean.setFiliation(itemOj.getString("filiation"));
                        allDeviceBean.setHeadUrl(itemOj.getString("headUrl"));
                        allDeviceBean.setType(itemOj.getString("type"));
                        allDeviceBean.setMac(itemOj.getString("mac"));
                        allDeviceBeans.add(allDeviceBean);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("---->allDeviceBeans",allDeviceBeans.size()+"");
                mHandler.sendEmptyMessageDelayed(3,100);


                break;



        }

    }

    @Override
    public void onFailure(String exp) {

    }




    private  void   QuerybodyFatScalePresenter(int uid){
        mPresenter.querybodyFatScalePresenter(this,uid);
    }

}
