package com.saiyi.oldmanwatch.module.scale;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.saiyi.oldmanwatch.R;
import com.saiyi.oldmanwatch.adapter.JiGuangUserListAdapter;
import com.saiyi.oldmanwatch.base.BaseMvpAppCompatActivity;
import com.saiyi.oldmanwatch.entity.UserInfoBean;
import com.saiyi.oldmanwatch.entity.bodyUserListBean;
import com.saiyi.oldmanwatch.helper.RetrofitService;
import com.saiyi.oldmanwatch.http.Constant;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpRequestCallback;
import com.saiyi.oldmanwatch.http.independent_okhttp.HttpUtils;
import com.saiyi.oldmanwatch.listener.OnSectorListener;
import com.saiyi.oldmanwatch.utils.SharedPreferencesHelper;
import com.saiyi.oldmanwatch.view.CircleImageView;
import com.saiyi.oldmanwatch.view.recyclerview.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.saiyi.oldmanwatch.helper.RetrofitService.ADD_TEST_DATE;

/**
 * Created by 最帅的男人 on 2019/3/4.
 */
public class JiGuangActivity extends BaseMvpAppCompatActivity implements HttpRequestCallback ,View.OnClickListener  ,OnSectorListener  {
    @BindView(R.id.tv_weight_num)
    TextView tvWeightNum;
    @BindView(R.id.imPic)
    CircleImageView imPic;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.Myrecy)
    RecyclerView mRecyclerView;
    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.imClose)
    ImageView imClose;


    private String name="";
    private String url="";
    private JiGuangUserListAdapter bobyUserListAdapter;

    @BindView(R.id.tvAddUser)
    TextView tvAddUser;

    @BindView(R.id.iv_Sector)
    ImageView iv_Sector;

    @BindView(R.id.tv_confirm)
    TextView tv_confirm;


    OnSectorListener listener;



    String ID_ = "";

    public void setListener(OnSectorListener listener) {
        this.listener = listener;
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case 1:

                    bobyUserListAdapter.setListData(dateUserBean);

                    break;
                case 2:

                    tvName.setText(name);
                    Glide.with(JiGuangActivity.this).load(Constant.getRoot() +url).error(R.mipmap.head_portrait).into(imPic  );


                    break;
                case 3:

                    Toast.makeText(JiGuangActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                    finish();

                    break;
                case 4:

                    String Msg=(String)msg.obj;
                    Toast.makeText(JiGuangActivity.this, Msg, Toast.LENGTH_SHORT).show();

                    break;

            }

        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_jiguang_test;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {


        Intent intent = getIntent();

        String WEI_ = intent.getStringExtra("WEI_");
         ID_ = intent.getStringExtra("ID_");
//体重
        if (!TextUtils.isEmpty(WEI_)){

            tvWeightNum.setText(WEI_);
        }
        //ID
        if (!TextUtils.isEmpty(ID_)){

        }


        imClose.setOnClickListener(this);
        tvAddUser.setOnClickListener(this);
        iv_Sector.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);


        bobyUserListAdapter =new JiGuangUserListAdapter(this);
        bobyUserListAdapter.setListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(0, 1, Color.parseColor("#CBCBCB")));
        mRecyclerView.setAdapter(bobyUserListAdapter);
        HttpUtils.getInstance(this).getSyncHttp(0X01, RetrofitService.QUERY_USER_LIST+ SharedPreferencesHelper.get("uid", -1),this);
        HttpUtils.getInstance(this).getSyncHttp(0X02, RetrofitService.USER_INFO+ SharedPreferencesHelper.get("uid", -1),this);





    }


    private List<bodyUserListBean> dateUserBean = new ArrayList<>();
    private  bodyUserListBean bean;

    private UserInfoBean userInfoBean;
    @Override
    public void onResponse(String sequest, int type) {
        switch (type) {
            case 0x01:

                try {
                    JSONObject object = new JSONObject(sequest);
                    JSONArray array = object.getJSONArray("data");
                    dateUserBean.clear();
                    for (int i = 0; i < array.length(); i++) {
                        bean = new bodyUserListBean();
                        JSONObject ob = array.getJSONObject(i);
                        bean.setName(ob.getString("name"));
                        Log.e("http name", ob.getString("name"));
                        Log.e("http url", ob.getString("headUrl"));
                        bean.setHeadUrl(ob.getString("headUrl"));
                        bean.setSex(ob.getString("sex"));
                        bean.setBirthday(ob.getString("birthday"));
                        bean.setHeight(ob.getString("height"));
                        dateUserBean.add(bean);
                    }

                    mHandler.sendEmptyMessageDelayed(1, 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            case 0x02:

                try {
                    JSONObject oj = new JSONObject(sequest);
                    JSONObject itemOj = oj.getJSONObject("data");
                    name =  itemOj.getString("name");
                    url  =  itemOj.getString("headUrl");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessageDelayed(2, 0);

                break;
            case 0x03:
                mHandler.sendEmptyMessageDelayed(3, 0);


                break;

        }
    }

    @Override
    public void onFailure(String exp) {

        Log.e("http exp",exp);

//        Toast.makeText(this, "操作失败！", Toast.LENGTH_SHORT).show();

         Message message =mHandler.obtainMessage();
         message.obj=exp;
         message.what=4;
         mHandler.sendMessage(message);


    }



    private boolean isSectior  = false ;
    @Override
    public void onClick(View view) {


        switch (view.getId()){

            case R.id.tv_confirm:

                if(isSectior==true) {

                    String id =  (int)SharedPreferencesHelper.get("uid", 0)+"";

                    okHttpAddDate(id);

                }else {
                    if (JiGuangUserListAdapter.getPostion()>0){
                        okHttpAddDate(dateUserBean.get(JiGuangUserListAdapter.getPostion()-1).getId()+"");
                    }else {
                        Toast.makeText(this, "请选择一个数据", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.iv_Sector:
                isSectior=true;
                iv_Sector.setImageResource(R.mipmap.select_voice);
                listener.setActivitySectorListener(dateUserBean);

                break;
            case R.id.imClose:

                finish();

                break;


            case R.id.tvAddUser:

                Intent intent = new Intent(this,AddUserActivity.class);
                startActivity(intent);


                break;

        }


    }


    private  void  okHttpAddDate(String UID){

        Map<String,String> maps = new HashMap<>();
        maps.put("id",ID_);
        maps.put("uid",UID);
        HttpUtils.getInstance(this).postAsynHttp(ADD_TEST_DATE,maps,0x03,this);


    }


    @Override
    public void setActivitySectorListener(List<bodyUserListBean>mDate) {



    }

    @Override
    public void setAdapterSectorListener() {
        iv_Sector.setImageResource(R.mipmap.unselect);
        isSectior=false;
    }


}
